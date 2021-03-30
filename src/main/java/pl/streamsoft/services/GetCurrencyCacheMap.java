package pl.streamsoft.services;

import java.util.Map;

public class GetCurrencyCacheMap implements CacheService {

	Map<String, Currency> cache = new ExtendedCacheMap<String, Currency>();

	public GetCurrencyCacheMap() {
	}

	public GetCurrencyCacheMap(Map<String, Currency> cache) {
		this.cache = cache;
	}

	public Currency checkAndGetIfExist(String key) {

		Currency currency;
		if (cache.containsKey(key)) {
			currency = cache.get(key);
			return currency;

		}
		return null;
	}

	public void putToCache(Currency currency, String key, int MAX_ENTRIES) {

		cache.put(key, currency);

	}

	public Map<String, Currency> getCache() {
		return cache;
	}

}
