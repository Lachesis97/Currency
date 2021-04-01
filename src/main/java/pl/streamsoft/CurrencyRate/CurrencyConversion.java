/*
 * CurrencyConversion
 *
 * Za pomoc� klasy FutureDateCheckService sprawdza czy kto� nie poda� daty z przyszlosci
 *
 * Przekazuje wybrane parametry wyszukiwania do wybranej strategi i finalnie otrzymuje obiekt waluty
 *
 * Na koniec zwraca wynik konwersji wybranej waluty na zlotowki
 *
 */

package pl.streamsoft.CurrencyRate;

import java.time.LocalDate;

import pl.streamsoft.cacheService.CacheService;
import pl.streamsoft.cacheService.LruCacheService;
import pl.streamsoft.getOrSaveData.CurrencyRepository;
import pl.streamsoft.getOrSaveData.GetCurrencyJsonNBP;
import pl.streamsoft.getOrSaveData.LruCache;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.DataProviderService;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {

	private int MAX_ENTRIES = 20;
	LruCacheService<String, Currency> cache;
	private ConvertService convertService = new NbpJsonConverter();
	private DataProviderService dataProviderService = new CurrencyRepository(new GetCurrencyJsonNBP(convertService));
	private CacheService cacheService;
	Currency currency = new Currency();

	public CurrencyConversion() {
		cache = new LruCacheService<String, Currency>(MAX_ENTRIES);
		cacheService = new LruCache(cache);
	}

	public CurrencyConversion(int MAX_ENTRIES) {
		this.MAX_ENTRIES = MAX_ENTRIES;
		cache = new LruCacheService<String, Currency>(MAX_ENTRIES);
		cacheService = new LruCache(cache);
	}

	public CurrencyConversion(DataProviderService dataProviderService) {
		this.dataProviderService = dataProviderService;
		cache = new LruCacheService<String, Currency>(MAX_ENTRIES);
		cacheService = new LruCache(cache);
	}

	public CurrencyConversion(DataProviderService dataProviderService, int MAX_ENTRIES) {
		this.MAX_ENTRIES = MAX_ENTRIES;
		this.dataProviderService = dataProviderService;
		cache = new LruCacheService<String, Currency>(MAX_ENTRIES);
		cacheService = new LruCache(cache);
	}

	public CurrencyConversion(int MAX_ENTRIES, CacheService cacheService) {
		this.cacheService = cacheService;
		this.MAX_ENTRIES = MAX_ENTRIES;
		cache = new LruCacheService<String, Currency>(MAX_ENTRIES);
		cacheService = new LruCache(cache);
	}

	public CurrencyConversion(DataProviderService dataProviderService, ConvertService convertService) {
		this.convertService = convertService;
		this.dataProviderService = dataProviderService;

	}

	public CurrencyConversion(DataProviderService dataProviderService, ConvertService convertService,
			CacheService cacheService, int MAX_ENTRIES) {
		this.dataProviderService = dataProviderService;
		this.convertService = convertService;
		this.cacheService = cacheService;
		this.MAX_ENTRIES = MAX_ENTRIES;
		cache = new LruCacheService<String, Currency>(MAX_ENTRIES);
		cacheService = new LruCache(cache);

	}

	public Currency conversion(String code, LocalDate date) {

		date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, dataProviderService);

		String key = code + date.toString();

		currency = cacheService.checkAndGetIfExist(key);

		if (currency == null) {
			currency = dataProviderService.getCurrency(code.toUpperCase(), date);

			cacheService.putToCache(currency, key);
			CurrencyRepository currencyRepository = new CurrencyRepository();
			currencyRepository.addCurrency(currency);

		}

		return currency;

	}

	public LruCacheService<String, Currency> getCache() {
		return cache;
	}

	public void clearCache() {
		cacheService.clearCache();
	}

}
