/*
 * SaleDocumentService
 *
 * 
 *
 */
package pl.streamsoft.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleDocumentService {

	public void insert() {

		String code = "eur";
		LocalDate date = LocalDate.of(2021, 3, 21);
		BigDecimal foreignCurrency = new BigDecimal("1");

		CurrencyConversion conversion = new CurrencyConversion();
		BigDecimal result = conversion.conversion(code, date, foreignCurrency);
		System.out.println("Wynik konwersji: " + result);

		conversion.conversion(code, LocalDate.of(2021, 3, 22), foreignCurrency);
		conversion.conversion(code, LocalDate.of(2021, 3, 23), foreignCurrency);
		conversion.conversion(code, LocalDate.of(2021, 3, 24), foreignCurrency);
		conversion.conversion(code, LocalDate.of(2021, 3, 25), foreignCurrency);
		conversion.conversion(code, LocalDate.of(2021, 3, 18), foreignCurrency);
		conversion.conversion(code, LocalDate.of(2021, 3, 17), foreignCurrency);
		conversion.conversion(code, LocalDate.of(2021, 3, 17), foreignCurrency);

	}

}
