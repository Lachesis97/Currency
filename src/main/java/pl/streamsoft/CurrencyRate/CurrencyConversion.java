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

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.NbpJsonConverter;
import pl.streamsoft.services.RateService;
import pl.streamsoft.services.ReturnValidateDate;

public class CurrencyConversion {

	RateService rateService;
	ConvertService convertService;

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

		String result = rateService.getCurrency(code.toUpperCase(), date);

		Currency currency = convertService.convertDataToObj(result);

		return amount.multiply(currency.getRate());

	}

}
