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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
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

	private RateService rateService;
	private ConvertService convertService;
	Map<String, Currency> cache = new HashMap<>();

	public CurrencyConversion() {
		rateService = new GetCurrencyJsonNBP();
		convertService = new NbpJsonConverter();
	}

	public CurrencyConversion(RateService rateService, ConvertService convertService) {
		this.rateService = rateService;
		this.convertService = convertService;

	}

	public BigDecimal conversion(String code, LocalDate date, BigDecimal amount) {

		date = FutureDateToTodaysDate.dataValidation(date);
		date = ReturnValidateDate.dataValidation(code, date, rateService);

		Currency existCurrency = new Currency();
		Currency newCurrency = new Currency();

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

}
