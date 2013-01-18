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
package org.tomochika1985.core.wicket.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.wicket.Component;
import org.apache.wicket.IEventDispatcher;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSink;

/**
 * @author t_hara
 *
 */
public class AnnotationEventDispatcher implements IEventDispatcher {

	@Override
	public void dispatchEvent(Object sink, IEvent<?> event, Component component) {
		Class<? extends IEventSink> sinkClass = sink.getClass().asSubclass(IEventSink.class);
		Object payload = event.getPayload();
		Class<? extends Object> payloadClass = payload.getClass();
		
		if (payload != null) {
			for (Method method : sinkClass.getMethods()) {
				Class<?>[] paramTypes = method.getParameterTypes();
				if (paramTypes.length == 1 && paramTypes[0].isAssignableFrom(payloadClass)) {
					try {
						method.invoke(sink, payload);
					} catch (IllegalAccessException e) {
						throw new IllegalStateException("Could not access to the method. EventHandler must be a public method.", e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException("underlying method threw a exception. see the stack trace.", e);
					}
				}
			}
		}
	}
}
