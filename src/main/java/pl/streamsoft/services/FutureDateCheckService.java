package pl.streamsoft.services;

import java.time.LocalDate;

public class FutureDateCheckService {

	public LocalDate datacheck(LocalDate date) {

		LocalDate now = LocalDate.now();

		if (date.isAfter(now)) {
			System.out.println("Pr�bujesz sprawdzi� kurs z przysz�o�ci");
			date = now;

		}

		return date;

	}

}
