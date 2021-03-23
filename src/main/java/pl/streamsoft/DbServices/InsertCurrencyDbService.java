package pl.streamsoft.DbServices;

import java.time.LocalDate;

import pl.streamsoft.Get.GetCurrencyDB;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.Context;

public class InsertCurrencyDbService {

	String code;
	LocalDate date;

	public InsertCurrencyDbService(String code, LocalDate date) {
		this.code = code;
		this.date = date;
	}

	public Boolean itExistCode() {
		Boolean itexist;

		String currencydb = new GetCurrencyJsonNBP().getCurrency(code.toUpperCase(), date);

		if (currencydb == null) {
			itexist = true;
		} else {
			itexist = false;
		}

		return itexist;

	}

	public Boolean itExistRate(String code, LocalDate date) {
		Boolean itexist;

		Context context = new Context(new GetCurrencyDB());

		String currencydb = context.execute(code, date);

		if (currencydb == null) {
			itexist = true;

		} else {
			itexist = false;
		}

		return itexist;

	}

}
