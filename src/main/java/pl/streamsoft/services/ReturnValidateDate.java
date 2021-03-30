package pl.streamsoft.services;

import java.time.LocalDate;

import pl.streamsoft.exceptions.DateValidationException;

public class ReturnValidateDate {

	public static LocalDate dataValidation(String code, LocalDate date, DataProviderService strategy) {

		int i = 0;
		Currency result;

		result = strategy.getCurrency(code.toUpperCase(), date);

		while (result == null) {

			date = date.minusDays(1);

			result = strategy.getCurrency(code.toUpperCase(), date);

			i++;
			if (i == 10) {
				throw new DateValidationException("B��dne argumenty wyszukiwania lub nie ma takiego kursu.");
			}
		}

		return date;

	}

}
