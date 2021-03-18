package pl.streamsoft.services;

import java.text.SimpleDateFormat;

import pl.streamsoft.Get.GetCurrencyDB;
import pl.streamsoft.Get.GetCurrencyJsonNBP;

public class InsertCurrencyDbService {


	public Boolean itExist(Currency currency, String code, String date){
		Boolean itexist;
		
		Context context = new Context(new GetCurrencyDB());

		Currency currencydb = context.execute(code, date);
		


		if (currencydb == null) {
			itexist = true;
		} else {
			System.out.println("Kurs \""+ currency.getName() +"\" o podanej dacie: \"" + date + "\" jest ju¿ w bazie danych.");
			itexist = false;
		}
				
		
		return itexist;
		
	}
	
}
