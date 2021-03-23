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
		LocalDate date = LocalDate.of(2022, 3, 15);
		BigDecimal foreignCurrency = new BigDecimal("123.45");

		CurrencyConversion conversion = new CurrencyConversion();
		BigDecimal result = conversion.conversion(code, date, foreignCurrency);
		System.out.println("Wynik konwersji: " + result);

//		Context context = new Context(strategy);
//		InsertCurrencyDb insertCurrencyDb = new InsertCurrencyDb();
//		insertCurrencyDb.insert(context.execute(code, dateS), code, dateS);

//		Context context = new Context(strategy);
//		Currency currency = context.execute(code, dateS);
//		System.out.println(currency.getCode() + ", " + currency.getName() + ", " + new SimpleDateFormat("yyyy-MM-dd").format(currency.getDate()) + ", " + currency.getRate());

	}

}
