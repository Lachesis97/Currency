package pl.streamsoft.services;

import java.time.LocalDate;

public class FutureDateCheckService {

	public LocalDate datacheck(LocalDate date) {

		LocalDate now = LocalDate.now();

		if (date.isAfter(now)) {
			System.out.println("Próbujesz sprawdziæ kurs z przysz³oœci");
			date = now;

		}

		return date;

	}

}
