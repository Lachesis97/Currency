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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.streamsoft.DbServices.CurrencyRepository;
import pl.streamsoft.Get.GetCurrencyCacheMap;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.CacheService;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.DataProviderService;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {
	private int MAX_ENTRIES = 20;
	Map<String, Currency> cache = new LinkedHashMap<String, Currency>() {

		protected boolean removeEldestEntry(Map.Entry<String, Currency> eldest) {

			return cache.size() > MAX_ENTRIES;
		}
	};

	private ConvertService convertService = new NbpJsonConverter();
	private DataProviderService rateService = new GetCurrencyJsonNBP(convertService);
	private CacheService cacheService = new GetCurrencyCacheMap(cache, MAX_ENTRIES);
	Currency currency = new Currency();

	public CurrencyConversion() {
	}

	public CurrencyConversion(int MAX_ENTRIES) {
		this.MAX_ENTRIES = MAX_ENTRIES;
	}

	public CurrencyConversion(CacheService cacheService, int MAX_ENTRIES) {
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

	public BigDecimal conversion(String code, LocalDate date, BigDecimal amount) {

		date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, rateService);

		String key = code + date.toString();
		currency = cacheService.checkAndGetIfExist(key);

		if (currency == null) {
			CurrencyRepository currencyRepository = new CurrencyRepository();
			currency = currencyRepository.getCurrency(code, date);
			cacheService.putToCache(currency, key);

		}

		if (currency == null) {
			currency = rateService.getCurrency(code.toUpperCase(), date);

			CurrencyRepository currencyRepository = new CurrencyRepository();
			currencyRepository.addCurrency(currency);

			cacheService.putToCache(currency, key);

			return currency.currencyToPln(amount);
		}

		return currency.currencyToPln(amount);

	}

	public Map<String, Currency> getCache() {
		return cache;
	}

}
