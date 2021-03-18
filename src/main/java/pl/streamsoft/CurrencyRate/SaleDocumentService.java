/*
 * SaleDocumentService
 *
 * 
 *
 */




package pl.streamsoft.CurrencyRate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.Get.GetCurrencyDB;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.Context;
import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.InsertCurrencyDb;
import pl.streamsoft.services.Strategy;
import pl.streamsoft.services.StringToDate;

public class SaleDocumentService {

	
	
	public static void insert() {
		
		
		String code = "eur";
		String dateS = "2021-03-13";
		BigDecimal zlotowki = new BigDecimal("123.00");
		
		String urlNBP = "http://api.nbp.pl/api/exchangerates/rates/a/";
		//String urlFile = "C:\\Users\\krzysztof.kmiecik/json.json";
		
		
		
		
		
		
		Strategy strategy = new GetCurrencyJsonNBP(urlNBP);
		//Strategy strategy = new GetCurrencyDB();
		//Strategy strategy = new GetCurrencyJsonFile(urlFile);
		

		
		
		
		
		BigDecimal result = CurrencyConversion.conversion(code, dateS, zlotowki, strategy).setScale(4);
		System.out.println("Wynik konwersji: " + result);
	
			
		
		
		
		
		
		

//		Context context = new Context(strategy);
//		Currency currency = context.execute(code, dateS);
//		System.out.println(currency.getCode() + ", " + currency.getName() + ", " + new SimpleDateFormat("yyyy-MM-dd").format(currency.getDate()) + ", " + currency.getRate());

		
		
		
		
//		Context context = new Context(strategy);
//		InsertCurrencyDb insertCurrencyDb = new InsertCurrencyDb();
//		insertCurrencyDb.insert(context.execute(code, dateS), code, dateS);
		
		
	}


}
