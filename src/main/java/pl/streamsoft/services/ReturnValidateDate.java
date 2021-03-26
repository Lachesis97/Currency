package pl.streamsoft.services;

import java.time.LocalDate;

import pl.streamsoft.exceptions.DateValidationException;

public class ReturnValidateDate {

	public static LocalDate dataValidation(String code, LocalDate date, RateService strategy) {

		int i = 0;
		String result;

		result = strategy.getCurrency(code.toUpperCase(), date);

		while (result == null || result.equals("SUNDAY") || result.equals("SATURDAY")) {

			date = date.minusDays(1);

			result = strategy.getCurrency(code.toUpperCase(), date);
			System.out.println(result);
			i++;
			if (i == 10) {
				throw new DateValidationException("B³êdne argumenty wyszukiwania lub nie ma takiego kursu.");
			}
		}

		return date;

	}

}
