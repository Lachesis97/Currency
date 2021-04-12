package pl.streamsoft.services;

import java.time.LocalDate;

import pl.streamsoft.exceptions.DateValidationException;
import pl.streamsoft.get_or_save_data.DataProviderService;

public class ReturnValidateDate {

	public static LocalDate dataValidation(String code, LocalDate date, DataProviderService strategy) {

		int i = 0;
		Currency result;
		LocalDate newDate = date;

		result = strategy.validateDate(code.toUpperCase(), newDate);

		while (result == null) {

			if (strategy.getNextStrategy() != null && result == null) {
				return ReturnValidateDate.dataValidation(code, date, strategy.getNextStrategy());
			} else if (i == 10) {
				throw new DateValidationException("B³êdne argumenty wyszukiwania lub nie ma takiego kursu.");

			}

			newDate = newDate.minusDays(1);

			result = strategy.validateDate(code.toUpperCase(), newDate);

			i++;
		}

		return newDate;

	}

}
