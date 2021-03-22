/*
 * SaleDocumentService
 *
 * 
 *
 */
package pl.streamsoft.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.Get.GetCurrencyJsonNBP;

public class SaleDocumentService {

	public static void insert() {

		String code = "eur";
		LocalDate date = LocalDate.of(2021, 3, 25);
		BigDecimal waluta = new BigDecimal("123.00");

		CurrencyConversion conversion = new CurrencyConversion(new GetCurrencyJsonNBP());
		BigDecimal result = conversion.conversion(code, date, waluta);
		System.out.println("Wynik konwersji: " + result);

//		Context context = new Context(strategy);
//		InsertCurrencyDb insertCurrencyDb = new InsertCurrencyDb();
//		insertCurrencyDb.insert(context.execute(code, dateS), code, dateS);

//		Context context = new Context(strategy);
//		Currency currency = context.execute(code, dateS);
//		System.out.println(currency.getCode() + ", " + currency.getName() + ", " + new SimpleDateFormat("yyyy-MM-dd").format(currency.getDate()) + ", " + currency.getRate());

	}

}
