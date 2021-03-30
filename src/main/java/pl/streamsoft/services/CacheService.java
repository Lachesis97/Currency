package pl.streamsoft.services;

public interface CacheService {

	public Currency checkAndGetIfExist(String key);

	public void putToCache(Currency currency, String key, int MAX_ENTRIES);

}