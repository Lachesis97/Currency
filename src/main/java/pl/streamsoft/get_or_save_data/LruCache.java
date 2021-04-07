package pl.streamsoft.get_or_save_data;

import java.time.LocalDate;

import pl.streamsoft.cache_service.CacheService;
import pl.streamsoft.cache_service.LruCacheService;
import pl.streamsoft.services.Currency;

public class LruCache implements CacheService {

	private LruCacheService<String, Currency> cache = new LruCacheService<String, Currency>(20);;

	public LruCache() {
	}

	public LruCache(LruCacheService cache) {
		this.cache = cache;
	}

	public Currency checkAndGetIfExist(String code, LocalDate date) {

		String key = code + date.toString();

		Currency currency;
		if (cache.get(key) != null) {
			currency = cache.get(key);

			return currency;
		}
		return null;
	}

	public void putToCache(Currency currency, String code, LocalDate date) {

		String key = code + date.toString();

		cache.put(key, currency);

	}

	public void clearCache() {

		cache.clear();

	}

	public LruCacheService getCache() {
		return cache;
	}

}
