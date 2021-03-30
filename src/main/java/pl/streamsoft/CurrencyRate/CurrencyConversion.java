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
import java.util.Map;

import pl.streamsoft.DbServices.CurrencyRepository;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.CacheService;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.DataProviderService;
import pl.streamsoft.services.ExtendedCacheMap;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.GetCurrencyCacheMap;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {

	public CurrencyConversion() {
	}

	public CurrencyConversion(int MAX_ENTRIES) {
		this.MAX_ENTRIES = MAX_ENTRIES;
	}

	public CurrencyConversion(DataProviderService rateService) {
		this.rateService = rateService;
	}

	public CurrencyConversion(int MAX_ENTRIES, DataProviderService rateService) {
		this.MAX_ENTRIES = MAX_ENTRIES;
		this.rateService = rateService;
	}

	public CurrencyConversion(int MAX_ENTRIES, CacheService cacheService) {
		this.cacheService = cacheService;
		this.MAX_ENTRIES = MAX_ENTRIES;
	}

	public CurrencyConversion(DataProviderService rateService, ConvertService convertService) {
		this.rateService = rateService;
		this.convertService = convertService;
	}

	public CurrencyConversion(DataProviderService rateService, ConvertService convertService, CacheService cacheService,
			int MAX_ENTRIES) {
		this.rateService = rateService;
		this.convertService = convertService;
		this.cacheService = cacheService;
		this.MAX_ENTRIES = MAX_ENTRIES;

	}

	private int MAX_ENTRIES = 20;

	Map<String, Currency> cache = new ExtendedCacheMap<String, Currency>();
	private ConvertService convertService = new NbpJsonConverter();
	private DataProviderService rateService = new GetCurrencyJsonNBP(convertService);
	private CacheService cacheService = new GetCurrencyCacheMap(cache);
	Currency currency = new Currency();

	public Currency conversion(String code, LocalDate date) {

		date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, rateService);

		String key = code + date.toString();
		currency = cacheService.checkAndGetIfExist(key);

		if (currency == null) {
			currency = rateService.getCurrency(code.toUpperCase(), date);

			cacheService.putToCache(currency, key, MAX_ENTRIES);
			CurrencyRepository currencyRepository = new CurrencyRepository();
			currencyRepository.addCurrency(currency);

		}

		return currency;

	}

	public Map<String, Currency> getCache() {
		return cache;
	}

}
