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
package org.tomochika1985.core.util;

import java.util.HashMap;

/**
 * @author t_hara
 *
 */
public class Literals {

	public static <K,V> MapBuilder<K,V> map(K key,V value) {
		return new MapBuilder<K, V>().map(key, value);
	}
	
	static class MapBuilder<K,V> extends HashMap<K, V> {
		private static final long serialVersionUID = 1L;

		public MapBuilder() {
			super();
		}
		
		public MapBuilder<K,V> map(K key,V value) {
			put(key, value);
			return this;
		}
	}
}
