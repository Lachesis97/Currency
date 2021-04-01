package pl.streamsoft.cacheService;

import pl.streamsoft.services.Currency;

public interface CacheService {

	public Currency checkAndGetIfExist(String key);

	public void putToCache(Currency currency, String key);

	public void clearCache();

}