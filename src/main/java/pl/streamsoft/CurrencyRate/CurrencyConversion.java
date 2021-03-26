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

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.CacheMap;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.RateService;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {
	private static int MAX_ENTRIES = 5;
	private RateService rateService;
	private ConvertService convertService;
	Currency existCurrency = new Currency();
	Currency newCurrency = new Currency();
	Map<String, Currency> cache = new LinkedHashMap<String, Currency>(MAX_ENTRIES) {

		protected boolean removeEldestEntry(Map.Entry<String, Currency> eldest) {

			return cache.size() > MAX_ENTRIES;
		}
	};

	public CurrencyConversion() {
		rateService = new GetCurrencyJsonNBP();
		convertService = new NbpJsonConverter();
	}

	public CurrencyConversion(int MAX_ENTRIES) {
		CurrencyConversion.MAX_ENTRIES = MAX_ENTRIES;
		rateService = new GetCurrencyJsonNBP();
		convertService = new NbpJsonConverter();
	}

	public CurrencyConversion(RateService rateService, ConvertService convertService, int MAX_ENTRIES) {
		this.rateService = rateService;
		this.convertService = convertService;
		CurrencyConversion.MAX_ENTRIES = MAX_ENTRIES;

	}

	public BigDecimal conversion(String code, LocalDate date, BigDecimal amount) {

		date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, rateService);

		String key = code + date.toString();
		CacheMap cacheMap = new CacheMap(key, cache);
		existCurrency = cacheMap.cacheCurrency();

		if (existCurrency == null) {
			String result = rateService.getCurrency(code.toUpperCase(), date);
			newCurrency = convertService.convertDataToObj(result);

			cache.put(key, newCurrency);

			return amount.multiply(newCurrency.getRate());
		}

		return amount.multiply(existCurrency.getRate());

	}

	public Currency getExistCurrency() {
		return existCurrency;
	}

	public Currency getNewCurrency() {
		return newCurrency;
	}

	public Map<String, Currency> getCache() {
		return cache;
	}

}
