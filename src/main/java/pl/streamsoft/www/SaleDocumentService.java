package pl.streamsoft.www;

import java.math.BigDecimal;

public class SaleDocumentService {

	
	
	public static void insert() {
		
		//System.out.println(CurrencyConversion.conversion("USD", new BigDecimal("10")));
		
		//Context context = new Context(new GetCurrencyJson());
			
		//InsertCurrencyDB.insert(context.execute("EUR"));
		Context context = new Context(new GetCurrencyDB());
		Currency currency = context.execute("EUR");
		System.out.println(currency.getName() + currency.getCode());
			
	}

}
