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

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author t_hara
 *
 */
public class Range implements Iterable<Integer>, Serializable, Cloneable {

	public static Range range(final int start, final int end, final int interval) {
		return new Range(start, end, interval);
	}
	
    public static Range range(final int end) {
        return new Range(0, end, 1);
    }
    
    public static Range range(final int start, final int end) {
        return new Range(start, end, 1);
    }
	
    int start;
    int end;
    int interval;
    int current = 0;
	
    Range(final int start, final int end, final int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.current = start;
    }
    
    public Range withInterval(final int interval) {
    	try {
    		Range clone = (Range) this.clone();
    		clone.interval = interval;
    		return clone;
    	} catch (CloneNotSupportedException ex) {
    		throw new RuntimeException(ex);
    	}
    }
    
	@Override
	public Iterator<Integer> iterator() {
		return new RangeItr();
	}

	class RangeItr implements Iterator<Integer> {

		@Override
		public boolean hasNext() {
			return current <= end;
		}

		@Override
		public Integer next() {
			final int ret = current;
			current += interval;
			return Integer.valueOf(ret);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
}
