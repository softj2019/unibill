package com.hsck.ubfw.component.com.cmm.util;

import org.apache.commons.collections.map.ListOrderedMap;

public class ContentMap extends ListOrderedMap {

	private static final long serialVersionUID = 6723434363565852261L;

	@Override
	public Object put(Object key, Object value) {
		return super.put(key.toString().toLowerCase(), value);
	}

}