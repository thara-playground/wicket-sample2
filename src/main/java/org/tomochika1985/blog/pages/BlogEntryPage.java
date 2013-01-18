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
package org.tomochika1985.blog.pages;

import jp.javelindev.wicket.markup.MarkupPath;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author t_hara
 *
 */
@MarkupPath("blog/entry.html")
public class BlogEntryPage extends WebPage {

	private static final long serialVersionUID = 5232251122586680373L;

	public BlogEntryPage() {
		super();
		add(new FeedbackPanel("feedback"));
	}
}
