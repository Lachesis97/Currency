package pl.streamsoft.services;

import java.time.LocalDate;

public class FutureDateToTodaysDate {

	public static LocalDate dataValidation(LocalDate date) {

		LocalDate now = LocalDate.now();

		if (date.isAfter(now)) {
			date = now;

		}

		return date;

	}

}
