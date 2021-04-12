package pl.streamsoft.currency_rate;

import java.time.LocalDate;

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

	private int maxEntries = 20;
	private LruCacheService<String, Currency> cache = new LruCacheService<String, Currency>(maxEntries);

	private ConvertService convertService = new NbpJsonConverter();
	private DataProviderService dataProviderService = new LruCache(cache,
			new CurrencyRepository(new GetCurrencyJsonNBP(convertService)));
	Currency currency = new Currency();

	public CurrencyConversion() {

	}

	public CurrencyConversion(int maxEntries) {
		this.cache = new LruCacheService<String, Currency>(maxEntries);
		this.dataProviderService = new LruCache(cache, new CurrencyRepository(new GetCurrencyJsonNBP(convertService)));
	}

	public CurrencyConversion(DataProviderService dataProviderService) {
		this.dataProviderService = dataProviderService;

	}

	public CurrencyConversion(DataProviderService dataProviderService, ConvertService convertService) {
		this.convertService = convertService;
		this.dataProviderService = dataProviderService;

	}

	public CurrencyConversion(DataProviderService dataProviderService, ConvertService convertService, int maxEntries) {
		this.dataProviderService = dataProviderService;
		this.convertService = convertService;
		this.cache = new LruCacheService<String, Currency>(maxEntries);
		this.dataProviderService = new LruCache(cache, new CurrencyRepository(new GetCurrencyJsonNBP(convertService)));

	}

	public Currency conversion(String code, LocalDate date) {

		date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, dataProviderService);

		currency = dataProviderService.getCurrency(code.toUpperCase(), date);

		return currency;

	}

	public LruCacheService<String, Currency> getCache() {
		return cache;
	}

	public void clearCache() {
		cache.clear();

	}

}
