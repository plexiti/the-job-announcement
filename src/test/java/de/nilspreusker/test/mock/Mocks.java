package de.nilspreusker.test.mock;

import java.util.HashMap;
import java.util.Map;

/*
 * @author martin@schimak.at
 */
public class Mocks {

	private static ThreadLocal<Map<String, Object>> beans = new ThreadLocal<Map<String, Object>>(); 

	private static Map<String, Object> map() {
		Map<String, Object> map = beans.get();
		if (map == null)
			register(map = new HashMap<String, Object>());
		return map;
	}
	
	public static Map<String, Object> get() {
		return map();
	}
	
	public static void register(Map<String, Object> beans) {
		Mocks.beans.set(beans);
	}

	public static void register(String key, Object value) {
		map().put(key, value);
	}

	public static Object get(Object key) {
		return map().get(key);
	}

}
