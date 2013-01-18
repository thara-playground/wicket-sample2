/*
* Copyright 2011 Tomochika Hara.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.tomochika1985.twitter;

import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomochika1985.sample.WicketApplication;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * @author t_hara
 *
 */
public class AppSession extends WebSession {

	private static final long serialVersionUID = -9062179328177828083L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppSession.class);
	private static final int COOKIE_MAX_AGE = 60 * 60 * 24; //60 (seconds) * 5 (minutes) = 5 minutes

	private String consumerKey;
	private String consumerSecret;
	
	private Twitter twitterSession;

	private RequestToken requestToken;
	private String lastAccessUrl;
	
	/**
	 * @param request
	 */
	public AppSession(Request request) {
		super(request);
		WicketApplication app = (WicketApplication) getApplication();
		consumerKey = app.getConsumerKey();
		consumerSecret = app.getConsumerSecret();
	}
	
    public boolean login(String pin, Response response) {
        try {
            if(requestToken == null) {
                throw new IllegalStateException("requestToken is missing.");
            }

            Twitter client = new TwitterClient();
            client.setOAuthConsumer(consumerKey, consumerSecret);
            
            AccessToken accessToken = client.getOAuthAccessToken(requestToken, pin);
            client.setOAuthAccessToken(accessToken);
            this.twitterSession = client;
            dirty();
            return true;
        } catch(TwitterException ex) {
            LOGGER.error("Can not setup OAuth Access Token to Twitter object.", ex);
            return false;
        }
    }
	
    public Twitter getTwitterSession(Request request) throws NeedAuthenticationException {
    	if(request == null) throw new IllegalArgumentException("'request' is missing");
    	
    	//既にTwitterオブジェクトを作成済みなら，認証の必要はない。
    	if(twitterSession != null) return twitterSession;
    	
    	// AccessTokenをDBなどに永続化している場合は，ここで永続化したAccessTokenを使って
    	// Twitterオブジェクトを再作成して返却すればよい。
    	// このサンプルではAccessTokenを永続化していないので，すぐにOAuth認証に入る。
    	
		// OAuth認証開始。RequestTokenの取得を試みる。
		Twitter client = new TwitterClient();
		client.setOAuthConsumer(consumerKey, consumerSecret);

		RequestToken token = null;
		try {
			token = client.getOAuthRequestToken(RequestUtils.toAbsolutePath("login", "/"));
		} catch (TwitterException ex) {
			throw new RuntimeException(ex);
		}
		
		  this.requestToken = token;
		  this.lastAccessUrl = request.getClientUrl().toString();
		  dirty();
		  
		  throw new RedirectToUrlException(token.getAuthorizationURL());
    }

    public Twitter getTwitterSession() {
        return twitterSession;
    }

    public String getLastAccessUrl() {
        return lastAccessUrl;
    }

    public static AppSession get() {
        return (AppSession) Session.get();
    }
}
