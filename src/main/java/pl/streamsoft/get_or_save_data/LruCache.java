package pl.streamsoft.get_or_save_data;

import java.time.LocalDate;

import pl.streamsoft.cache_service.CacheService;
import pl.streamsoft.cache_service.LruCacheService;
import pl.streamsoft.services.Currency;

public class LruCache implements CacheService, DataProviderService {

	private LruCacheService<String, Currency> cache = new LruCacheService<String, Currency>(20);

	private DataProviderService nextStrategy;

	public LruCache(DataProviderService dataProviderService) {
		this.nextStrategy = dataProviderService;
	}

	public LruCache(LruCacheService<String, Currency> cache) {
		this.cache = cache;
	}

	public LruCache(LruCacheService<String, Currency> cache, DataProviderService dataProviderService) {
		this.cache = cache;
		this.nextStrategy = dataProviderService;
	}

	public LruCache() {

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

	public Currency getCurrency(String code, LocalDate date) {
		String key = code + date.toString();

		Currency currency = null;

		if (cache.get(key) != null) {
			currency = cache.get(key);

			return currency;
		} else {
			if (nextStrategy != null) {
				currency = nextStrategy.getCurrency(code, date);
				putToCache(currency, code, date);
				return currency;
			}
			return currency;
		}

	}

	public Currency validateDate(String code, LocalDate date) {
		String key = code + date.toString();

		Currency currency = null;

		if (cache.get(key) != null) {
			currency = cache.get(key);

			return currency;
		}
		return currency;
	}

	public DataProviderService getNextStrategy() {
		return nextStrategy;
	}

}
