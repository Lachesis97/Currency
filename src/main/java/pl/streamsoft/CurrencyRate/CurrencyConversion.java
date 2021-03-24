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
import java.util.ArrayList;
import java.util.List;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.RateService;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {

	private RateService rateService;
	private ConvertService convertService;

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

		List<Currency> cache = new ArrayList<Currency>();
		Currency existCurrency = new Currency();
		Currency newCurrency = new Currency();

		Boolean doesNotExist = true;

		for (Currency currency : cache) {
			if (currency.getCode().equals(code) && currency.getDate().equals(date)) {
				doesNotExist = false;
				existCurrency.setName(currency.getName());
				existCurrency.setCode(currency.getCode());
				existCurrency.setDate(currency.getDate());
				existCurrency.setRate(currency.getRate());
			}

		}

		if (doesNotExist) {
			String result = rateService.getCurrency(code.toUpperCase(), date);

			newCurrency = convertService.convertDataToObj(result);

			cache.add(newCurrency);

			return amount.multiply(newCurrency.getRate());
		}

		return amount.multiply(existCurrency.getRate());

	}

}
