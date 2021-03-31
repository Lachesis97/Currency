/*
 * CurrencyConversion
 *
 * Za pomoc¹ klasy FutureDateCheckService sprawdza czy ktoœ nie poda³ daty z przyszlosci
 *
 * Przekazuje wybrane parametry wyszukiwania do wybranej strategi i finalnie otrzymuje obiekt waluty
 *
 * Na koniec zwraca wynik konwersji wybranej waluty na zlotowki
 *
 */

package pl.streamsoft.CurrencyRate;

import java.time.LocalDate;

import pl.streamsoft.DbServices.CurrencyRepository;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.CacheService;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.DataProviderService;
import pl.streamsoft.services.GetCurrencyCacheMap;
import pl.streamsoft.services.LruCache;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {

	private int MAX_ENTRIES = 20;
	LruCache<String, Currency> cache;
	private ConvertService convertService = new NbpJsonConverter();
	private DataProviderService rateService = new GetCurrencyJsonNBP(convertService);
	private CacheService cacheService;
	Currency currency = new Currency();

	public CurrencyConversion() {
		cache = new LruCache<String, Currency>(MAX_ENTRIES);
		cacheService = new GetCurrencyCacheMap(cache);
	}

	public CurrencyConversion(int MAX_ENTRIES) {
		this.MAX_ENTRIES = MAX_ENTRIES;
		cache = new LruCache<String, Currency>(MAX_ENTRIES);
		cacheService = new GetCurrencyCacheMap(cache);
	}

	public CurrencyConversion(DataProviderService rateService) {
		this.rateService = rateService;
		cache = new LruCache<String, Currency>(MAX_ENTRIES);
		cacheService = new GetCurrencyCacheMap(cache);
	}

	public CurrencyConversion(DataProviderService rateService, int MAX_ENTRIES) {
		this.MAX_ENTRIES = MAX_ENTRIES;
		this.rateService = rateService;
		cache = new LruCache<String, Currency>(MAX_ENTRIES);
		cacheService = new GetCurrencyCacheMap(cache);
	}

	public CurrencyConversion(int MAX_ENTRIES, CacheService cacheService) {
		this.cacheService = cacheService;
		this.MAX_ENTRIES = MAX_ENTRIES;
		cache = new LruCache<String, Currency>(MAX_ENTRIES);
		cacheService = new GetCurrencyCacheMap(cache);
	}

	public CurrencyConversion(DataProviderService rateService, ConvertService convertService) {
		this.convertService = convertService;
		this.rateService = rateService;

	}

	public CurrencyConversion(DataProviderService rateService, ConvertService convertService, CacheService cacheService,
			int MAX_ENTRIES) {
		this.rateService = rateService;
		this.convertService = convertService;
		this.cacheService = cacheService;
		this.MAX_ENTRIES = MAX_ENTRIES;
		cache = new LruCache<String, Currency>(MAX_ENTRIES);
		cacheService = new GetCurrencyCacheMap(cache);

	}

	public Currency conversion(String code, LocalDate date) {

		// date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, rateService);

		String key = code + date.toString();

		currency = cacheService.checkAndGetIfExist(key);

		if (currency == null) {
			currency = rateService.getCurrency(code.toUpperCase(), date);

			cacheService.putToCache(currency, key);
			CurrencyRepository currencyRepository = new CurrencyRepository();
			currencyRepository.addCurrency(currency);

		}

		return currency;

	}

	public LruCache getCache() {
		return cache;
	}

	public void cacheClear() {
		cache = new LruCache<String, Currency>(5);
	}

}
