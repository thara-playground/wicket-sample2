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
package org.tomochika1985.core.wicket.markup;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import jp.javelindev.wicket.markup.MarkupPath;

import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.UrlResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author t_hara
 *
 */
public class MarkupPathResourceFinder implements IResourceFinder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MarkupPathResourceFinder.class);
	private final ServletContext servletContext;
	private IResourceFinder parent;

	public MarkupPathResourceFinder(ServletContext servletContext) {
		this(servletContext, null);
	}

	public MarkupPathResourceFinder(ServletContext servletContext,
			IResourceFinder parent) {
		super();
		this.parent = parent;
		this.servletContext = servletContext;
	}

    public IResourceFinder getParent() {
        return parent;
    }

    public void setParent(IResourceFinder parent) {
        this.parent = parent;
    }
    
    @Override
    public IResourceStream find(Class<?> clazz, String pathname) {
    	
        IResourceStream result = null;
//    	if (parent != null) {
//    		result = parent.find(clazz, pathname);
//    		if (result != null) return result;
//    	}
    	

        getExtension(pathname);
        if (clazz.isAnnotationPresent(MarkupPath.class)) {
            MarkupPath annotation = clazz.getAnnotation(MarkupPath.class);
            String path = annotation.value();
            if (getExtension(pathname).equals(getExtension(path))) {
            	try {
            		//if 'path' starts with '/', the path will be an absolute path of local machine.
            		//if not, the path will be a relative path from web application's context root.
            		URL url = null;
            		if (path.startsWith("/")) {
            			url = new File(path).toURI().toURL();
            		} else {
            			url = servletContext.getResource(appendPathSeparator(path));
            		}
            		if (url != null) {
            			result = new UrlResourceStream(url);
            		}
            	} catch (MalformedURLException ex) {
            		LOGGER.warn("Invalid path:" + path, ex);
            		//do nothing. Wicket will find resource with default behavior.
            	}
            }
            
        }
        
        if (result != null) {
            return result;
        } else {
            return (parent == null ? null : parent.find(clazz, pathname));
        }
    }

	/**
	 * @param pathname
	 * @return
	 */
	private String getExtension(String pathname) {
		return pathname.substring(pathname.lastIndexOf(".") + 1);
	}

    private String appendPathSeparator(String pathname) {
        assert pathname != null;
        return pathname.startsWith("/") ? pathname : "/" + pathname;
    }
}
