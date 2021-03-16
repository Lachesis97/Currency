package pl.streamsoft.www;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaleDocumentService {

	
	
	public static void insert() {
		
		String code = "EUR";
		String dateS = "2021.03.16";
		BigDecimal zlotowki = new BigDecimal("10");
		
		
		Context context = new Context(new GetCurrencyJson());
		//Context context = new Context(new GetCurrencyDB());
		
		Currency currency = context.execute(code, StringToDate.conversion(dateS));
		
		
		
		CurrencyConversion.conversion(code, StringToDate.conversion(dateS), zlotowki);
			
	
		
		//InsertCurrencyDB.insert(context.execute(code, StringToDate.conversion(dateS)));
		
		
		
		
		
		//System.out.println(currency.getName() + currency.getCode());

		

			
	}

}
