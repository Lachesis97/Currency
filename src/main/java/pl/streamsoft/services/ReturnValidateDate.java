package pl.streamsoft.services;

import java.time.LocalDate;

import pl.streamsoft.exceptions.DateValidationException;

public class ReturnValidateDate {

	public static LocalDate dataValidation(String code, LocalDate date, RateService strategy) {

		int i = 0;
		String result;

		result = strategy.getCurrency(code.toUpperCase(), date);

		while (result == null) {

			date = date.minusDays(1);

			result = strategy.getCurrency(code.toUpperCase(), date);
			i++;
			if (i == 10) {
				throw new DateValidationException("B³êdne argumenty wyszukiwania lub nie ma takiego kursu.");
			}
		}

		System.out.println("Pobrano kurs z dnia: \"" + date + "\"");

		return date;

	}

}
