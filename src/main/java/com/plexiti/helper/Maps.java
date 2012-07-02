package com.plexiti.helper;

import java.util.HashMap;
import java.util.Map;

public class Maps<V> {
	
	public static <K, V> MapBuilder<K, V> hashMap(Class<K> keyClass, Class<V> valueClass) {
		return new HashMapBuilder<K, V>();
	}
	
	public interface MapBuilder<K, V> {		
		MapBuilder<K, V> put (K key, V value);
		Map<K, V> build();			
	}

	private static class HashMapBuilder<K, V> implements MapBuilder<K, V> {

		private Map<K, V> map = new HashMap<K, V>();

		public MapBuilder<K, V> put (K key, V value) {
			map.put(key, value);
			return this;
		}
		
		public Map<K, V> build() {
			return map;
		}
		
	}

}
