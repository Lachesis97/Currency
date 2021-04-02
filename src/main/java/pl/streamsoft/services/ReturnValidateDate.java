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

			newDate = newDate.minusDays(1);

			result = strategy.validateDate(code.toUpperCase(), newDate);

			i++;
			if (i == 10) {
				if (strategy.getNextStrategy() != null) {
					return ReturnValidateDate.dataValidation(code, date, strategy.getNextStrategy());
				} else {
					throw new DateValidationException("B��dne argumenty wyszukiwania lub nie ma takiego kursu.");
				}

			}
		}

		return newDate;

	}

}
