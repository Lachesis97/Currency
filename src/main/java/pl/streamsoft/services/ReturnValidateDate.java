package pl.streamsoft.services;

import java.time.LocalDate;

import pl.streamsoft.exceptions.DateValidationException;

public class ReturnValidateDate {

	public static LocalDate dataValidation(String code, LocalDate date, DataProviderService strategy) {

		int i = 0;
		Currency result;

		result = strategy.validateDate(code.toUpperCase(), date);

		while (result == null) {

			date = date.minusDays(1);

			result = strategy.validateDate(code.toUpperCase(), date);

			i++;
			if (i == 10) {
				throw new DateValidationException("B³êdne argumenty wyszukiwania lub nie ma takiego kursu.");
			}
		}

		return date;

	}

}
