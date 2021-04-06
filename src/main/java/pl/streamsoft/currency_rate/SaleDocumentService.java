/*
 * SaleDocumentService
 *
 * 
 *
 */
package pl.streamsoft.currency_rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import pl.streamsoft.data_base_services.CurrencySpecificRequests;
import pl.streamsoft.get_or_save_data.CurrencyRepository;
import pl.streamsoft.get_or_save_data.GetCurrencyJsonNBP;
import pl.streamsoft.services.Currency;

public class SaleDocumentService {

	public void insert() {

		String code = "eur";
		LocalDate date = LocalDate.of(2021, 4, 15);
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

		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();

		Currency maxCurrency = specificRequests.getMaxRate("eur", LocalDate.of(2021, 3, 17), LocalDate.of(2021, 3, 31));
		System.out.println("Max: " + maxCurrency.getCode() + ", " + maxCurrency.getName() + ", " + maxCurrency.getDate()
				+ ", " + maxCurrency.getRate());
		Currency minCurrency = specificRequests.getMinRate("eur", LocalDate.of(2021, 3, 17), LocalDate.of(2021, 3, 31));
		System.out.println("Min: " + minCurrency.getCode() + ", " + minCurrency.getName() + ", " + minCurrency.getDate()
				+ ", " + minCurrency.getRate());

//
//		List<Currency> bestFive = specificRequests.getFiveBestRates("eur");
//		List<Currency> worstFive = specificRequests.getFiveWorstRates("eur");
//
//		int i = 1;
//		for (Currency currency2 : bestFive) {
//			System.out.println("Best " + i + ": " + currency2.getCode() + ", " + currency2.getName() + ", "
//					+ currency2.getDate() + ", " + currency2.getRate());
//			i++;
//		}
//		i = 1;
//		for (Currency currency2 : worstFive) {
//			System.out.println("Worst " + i + ": " + currency2.getCode() + ", " + currency2.getName() + ", "
//					+ currency2.getDate() + ", " + currency2.getRate());
//			i++;
//		}

	}

}
