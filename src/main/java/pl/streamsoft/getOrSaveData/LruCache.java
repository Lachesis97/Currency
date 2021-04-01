package pl.streamsoft.getOrSaveData;

import pl.streamsoft.cacheService.CacheService;
import pl.streamsoft.cacheService.LruCacheService;
import pl.streamsoft.services.Currency;

public class LruCache implements CacheService {

	private LruCacheService<String, Currency> cache = new LruCacheService<String, Currency>(20);;

	public LruCache() {
	}

	public LruCache(LruCacheService cache) {
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

	public void clearCache() {

		cache.clear();

	}

	public LruCacheService getCache() {
		return cache;
	}

}
