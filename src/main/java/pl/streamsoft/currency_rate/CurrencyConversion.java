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

package pl.streamsoft.currency_rate;

import java.time.LocalDate;

import pl.streamsoft.cache_service.CacheService;
import pl.streamsoft.cache_service.LruCacheService;
import pl.streamsoft.data_converters.ConvertService;
import pl.streamsoft.data_converters.NbpJsonConverter;
import pl.streamsoft.get_or_save_data.CurrencyRepository;
import pl.streamsoft.get_or_save_data.DataProviderService;
import pl.streamsoft.get_or_save_data.GetCurrencyJsonNBP;
import pl.streamsoft.get_or_save_data.LruCache;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.FutureDateToTodaysDate;
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

		currency = cacheService.checkAndGetIfExist(code, date);

		if (currency == null) {
			date = ReturnValidateDate.dataValidation(code, date, dataProviderService);

			currency = dataProviderService.getCurrency(code.toUpperCase(), date);

			cacheService.putToCache(currency, code, date);
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
