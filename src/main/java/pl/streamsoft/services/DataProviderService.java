package pl.streamsoft.services;

import java.time.LocalDate;

public interface DataProviderService {

	public Currency validateDate(String code, LocalDate date);

	public Currency getCurrency(String code, LocalDate date);

}