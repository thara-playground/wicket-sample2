package org.tomochika1985.sample;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.mount.MountMapper;
import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.file.WebApplicationPath;
import org.tomochika1985.blog.pages.BlogEntryPage;
import org.tomochika1985.core.wicket.dispatcher.AnnotationEventDispatcher;
import org.tomochika1985.core.wicket.markup.MarkupPathResourceFinder;
import org.tomochika1985.rssreader.ListPage;
import org.tomochika1985.twitter.AppSession;
import org.tomochika1985.twitter.LoginProcessor;

import com.google.inject.Module;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see org.tomochika1985.sample.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	
	private String consumerKey;
	private String consumerSecret;

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<ListPage> getHomePage() {
		return ListPage.class;
//		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		
		// add your configuration here
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		getFrameworkSettings().add(new AnnotationEventDispatcher());
		
		Module[] modules = getModules();
		GuiceComponentInjector injector = new GuiceComponentInjector(this, modules);
		getComponentInstantiationListeners().add(injector);
		getBehaviorInstantiationListeners().add(injector);
		
		mountPage("/rssreader", ListPage.class);
		mountPage("/blog", BlogEntryPage.class);
//		mount(new LoginProcessorUrlCodingStrategy("/login"));
		mount(new MountMapper("/login", new LoginProcessor()));
		
		consumerKey = getInitParameter("twitter-consumer-key");
		consumerSecret = getInitParameter("twitter-consumer-secret");
		if(consumerKey == null) throw new IllegalStateException("'twitter-consumer-key' is missing in init-param of wicketFilter.");
		if(consumerSecret == null) throw new IllegalStateException("'twitter-consumer-secret' is missing in init-param of wicketFilter.");
	}

	@Override
	protected IResourceFinder getResourceFinder() {
		ServletContext sc = getServletContext();
		return new MarkupPathResourceFinder(sc, new WebApplicationPath(sc));
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new AppSession(request);
	}
	
	/**
	 * @return
	 */
	private Module[] getModules() {
		List<Module> modules = new ArrayList<Module>();
		// add module to inject resources.
		
		
		return modules.toArray(new Module[modules.size()]);
	}
	
	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}
	
	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}
}
