/*
 * SaleDocumentService
 *
 * 
 *
 */
package pl.streamsoft.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import pl.streamsoft.DbServices.CurrencyRepository;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.Currency;

public class SaleDocumentService {

	public void insert() {

		String code = "eur";
		LocalDate date = LocalDate.of(2021, 4, 5);
		BigDecimal foreignCurrency = new BigDecimal("123.00");

		CurrencyConversion conversion = new CurrencyConversion(new CurrencyRepository(new GetCurrencyJsonNBP()));
		Currency currency = conversion.conversion(code, date);
		BigDecimal result = currency.currencyToPln(foreignCurrency);
		System.out.println(result.setScale(2, RoundingMode.HALF_UP));

		conversion.conversion("inr", LocalDate.of(2021, 3, 22));
		conversion.conversion("inr", LocalDate.of(2021, 3, 23));
		conversion.conversion("isk", LocalDate.of(2021, 3, 24));
		conversion.conversion("isk", LocalDate.of(2021, 3, 24));
		conversion.conversion("czk", LocalDate.of(2021, 3, 25));
		conversion.conversion("czk", LocalDate.of(2021, 3, 25));
		conversion.conversion("gbp", LocalDate.of(2021, 3, 18));
		conversion.conversion("gbp", LocalDate.of(2021, 3, 18));
		conversion.conversion("usd", LocalDate.of(2021, 3, 17));

	}

}
