package pl.streamsoft.Get;

import java.util.LinkedHashMap;
import java.util.Map;

import pl.streamsoft.services.CacheService;
import pl.streamsoft.services.Currency;

public class GetCurrencyCacheMap implements CacheService {

	int MAX_ENTRIES;
	Map<String, Currency> cache = new LinkedHashMap<String, Currency>(MAX_ENTRIES) {

		protected boolean removeEldestEntry(Map.Entry<String, Currency> eldest) {

			return cache.size() > MAX_ENTRIES;
		}
	};

	public GetCurrencyCacheMap() {
	}

	public GetCurrencyCacheMap(Map<String, Currency> cache) {
		this.cache = cache;
	}

	public GetCurrencyCacheMap(Map<String, Currency> cache, int mAX_ENTRIES) {
		this.cache = cache;
		this.MAX_ENTRIES = mAX_ENTRIES;
	}

	public Currency checkAndGetIfExist(String key) {

		Currency currency;
		if (cache.containsKey(key)) {
			currency = cache.get(key);
			return currency;

		}
		return null;
	}

	public void putToCache(Currency currency, String key) {
		cache.put(key, currency);

	}

	public Map<String, Currency> getCache() {
		return cache;
	}

}
