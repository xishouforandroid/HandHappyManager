package com.liangxunwang.unimanager.baiduyun.filter;

import java.lang.reflect.Field;
import java.util.Map;

public interface IFieldFilter {

	public void validate(Field field, Object obj) throws Exception;
	
	public void mapping(Field field, Object obj, Map<String, String> map) throws Exception;
	
}
