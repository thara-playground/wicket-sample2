package org.tomochika1985.sample;

import jp.javelindev.wicket.markup.MarkupPath;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@MarkupPath("HomePage.html")
public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
        // TODO Add your page's components here
    }
}
