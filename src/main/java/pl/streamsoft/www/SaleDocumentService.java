package pl.streamsoft.www;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.Db.GetCurrencyDB;
import pl.streamsoft.Db.InsertCurrencyDb;
import pl.streamsoft.services.Context;
import pl.streamsoft.services.DateCheckService;
import pl.streamsoft.services.Strategy;
import pl.streamsoft.services.StringToDate;

public class SaleDocumentService {

	
	
	public static void insert() {
		
		DateCheckService getCurrancyDateService = new DateCheckService();
		
		String code = "eur".toUpperCase();
		String dateS = "2021-03-25";
		BigDecimal zlotowki = new BigDecimal("10.00");
		//dateS = getCurrancyDateService.datacheck(dateS);
		StringToDate stringToDate = new StringToDate();
		
		
		Strategy strategy = new GetCurrencyJson();
		//Strategy strategy = new GetCurrencyDB();
		

		
		
		
		
		CurrencyConversion.conversion(code, dateS, zlotowki, strategy);
			
		
		
		

//		Context context = new Context(strategy);
//		Currency currency = context.execute(code, dateS);
//		System.out.println(currency.getCode() + ", " + currency.getName() + ", " + new SimpleDateFormat("yyyy-MM-dd").format(currency.getDate()) + ", " + currency.getRate());

		
		
		
		
//		Context context = new Context(strategy);
//		InsertCurrencyDb insertCurrencyDb = new InsertCurrencyDb();
//		insertCurrencyDb.insert(context.execute(code, dateS), code, dateS);
		
		
	}
	
	public static void main(String[] args) {
		insert();
	}

}
