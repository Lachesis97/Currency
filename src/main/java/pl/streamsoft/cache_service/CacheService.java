package pl.streamsoft.cache_service;

import java.time.LocalDate;

import pl.streamsoft.services.Currency;

public interface CacheService {

	public Currency checkAndGetIfExist(String code, LocalDate date);

	public void putToCache(Currency currency, String code, LocalDate date);

	public void clearCache();

}