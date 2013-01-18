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
package org.tomochika1985.rssreader;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.javelindev.wicket.markup.MarkupPath;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;

/**
 * @author t_hara
 *
 */
@MarkupPath("rssreader/list.html")
public class ListPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("serial")
	public ListPage() {
		super();
		add(new Label("test", new AbstractReadOnlyModel<String>() {
			public String getObject() {
				return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
			}
		}));
		
		createUrlForm();
	}
	
	@SuppressWarnings("serial")
	void createUrlForm() {
		final Form<Void> urlForm = new Form<Void>("urlForm");
		add(urlForm);
		final TextField<String> txtFld = new TextField<String>("urlTextField", new Model<String>());
		txtFld.setRequired(true);
		txtFld.add(new PatternValidator("^http://.*$"));
		urlForm.add(txtFld);
		
		final CheckBox check1 = new CheckBox("check1", new Model<Boolean>(true));
		final CheckBox check2 = new CheckBox("check2", new Model<Boolean>(true));
		urlForm.add(check1);
		urlForm.add(check2);
		
		add(new FeedbackPanel("feedback"));
		
		urlForm.add(new Button("submitBtn") {
			@Override
			public void onSubmit() {
				Component urlField = this.getParent().get("urlTextField");
				String newUrlStr = urlField.getDefaultModelObjectAsString();
				info("入力値は: " + newUrlStr);
			}
		});
		urlForm.add(new AbstractFormValidator() {
			@Override
			public void validate(Form<?> form) {
				if (!check1.getConvertedInput() && !check2.getConvertedInput()) {
					urlForm.error("チェックボックスがチェックされていません");
				}
			}
			
			@Override
			public FormComponent<?>[] getDependentFormComponents() {
				return new FormComponent<?>[]{check1, check2};
			}
		});
	}
}
