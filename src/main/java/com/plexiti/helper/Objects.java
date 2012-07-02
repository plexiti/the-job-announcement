package com.plexiti.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Objects {

	public static <E> E newInstance (Class<? extends E> clazz) {
		try {
			return clazz.newInstance();		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Type getActualTypeArgument(Class<?> clazz, int arg) {
		ParameterizedType superclass = (ParameterizedType) clazz.getGenericSuperclass();
		return ((ParameterizedType) superclass).getActualTypeArguments()[arg];
	}

}
