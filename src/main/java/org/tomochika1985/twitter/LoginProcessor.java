/*
 *  Copyright 2009 Tsutomu YANO.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.tomochika1985.twitter;

import org.apache.wicket.RedirectToUrlException;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;

/**
 *
 * @author Tsutomu YANO
 */
public class LoginProcessor implements IRequestHandler {

    public void respond(RequestCycle requestCycle) {
    	
        String[] values = (String[]) requestCycle.getPageParameters().get("oauth_verifier");
        if(values == null || values.length == 0) throw new IllegalStateException("'oauth_verifier' parameter is missing.");
        if(values.length > 1) throw new IllegalStateException("'oauth_verifier' parameter has plural values. the parameter should has only one value.");
        
        String pin = values[0];
        AppSession session = (AppSession) requestCycle.getSession();
        if(session.login(pin, requestCycle.getResponse())){
            String targetUrl = session.getLastAccessUrl();
            throw new RedirectToUrlException(targetUrl);
        } else {
            throw new IllegalStateException("Can not login");
        }
    }

	@Override
	public void respond(IRequestCycle requestCycle) {
		respond((RequestCycle) requestCycle);
	}

	@Override
	public void detach(IRequestCycle requestCycle) {
		//NOTHING TO DO
	}
}
