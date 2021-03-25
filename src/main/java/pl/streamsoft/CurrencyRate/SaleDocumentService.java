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
		BigDecimal foreignCurrency = new BigDecimal("123.45");

		CurrencyConversion conversion = new CurrencyConversion();
		BigDecimal result = conversion.conversion(code, date, foreignCurrency);
		System.out.println("Wynik konwersji: " + result);

	}

}
