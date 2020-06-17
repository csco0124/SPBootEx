package com.app.dashboard.util;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

public class LowerKeyMap extends HashMap{
	
	/**
	 * MyBatis에서 Map 형태로 받을 때 모든 컬럼을 소문자로 변환해주는 유틸
	 */
	@Override
	public Object put(Object key, Object value) {
        return super.put(StringUtils.lowerCase((String) key), value);
    }
}