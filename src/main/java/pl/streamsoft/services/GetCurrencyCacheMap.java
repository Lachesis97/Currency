package pl.streamsoft.services;

public class GetCurrencyCacheMap implements CacheService {

	private LruCache<String, Currency> cache;

	public GetCurrencyCacheMap() {
	}

	public GetCurrencyCacheMap(LruCache cache) {
		this.cache = cache;
	}

	public Currency checkAndGetIfExist(String key) {

		Currency currency;
		if (cache.get(key) != null) {
			currency = cache.get(key);

			return currency;
		}
		return null;
	}

	public void putToCache(Currency currency, String key) {

		cache.put(key, currency);

	}

	public LruCache getCache() {
		return cache;
	}

}
