package pl.streamsoft.services;

import java.time.LocalDate;

public interface Strategy {

	public Currency getCurrency(String code, LocalDate date);

}