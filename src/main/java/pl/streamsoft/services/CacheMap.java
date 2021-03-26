package pl.streamsoft.services;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheMap {

	String key;
	Map<String, Currency> cache = new LinkedHashMap<String, Currency>(5);

	public CacheMap(String key, Map<String, Currency> cache) {
		super();
		this.key = key;
		this.cache = cache;
	}

	public Currency cacheCurrency() {

		Currency currency;
		if (cache.containsKey(key)) {
			currency = cache.get(key);
			return currency;

		}

		return null;
	}

}
