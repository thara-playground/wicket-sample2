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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import twitter4j.Twitter;
import twitter4j.http.HttpClient;

/**
 * @author t_hara
 *
 */
public class TwitterClient extends Twitter implements Externalizable {

	/**
	 * 
	 */
	public TwitterClient() {
		super();
	}

	/**
	 * @param id
	 * @param password
	 * @param baseURL
	 */
	public TwitterClient(String id, String password, String baseURL) {
		super(id, password, baseURL);
	}

	/**
	 * @param id
	 * @param password
	 */
	public TwitterClient(String id, String password) {
		super(id, password);
	}

	/**
	 * @param baseURL
	 */
	public TwitterClient(String baseURL) {
		super(baseURL);
	}

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(getBaseURL());
        out.writeUTF(getSearchBaseURL());
        out.writeObject(this.http);
        out.writeUTF(this.source);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setBaseURL(in.readUTF());
        this.setSearchBaseURL(in.readUTF());
        this.http = (HttpClient) in.readObject();
        this.source = in.readUTF();
    }
}
