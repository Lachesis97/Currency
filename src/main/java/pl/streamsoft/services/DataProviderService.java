package pl.streamsoft.services;

import java.time.LocalDate;

public interface DataProviderService {

	public Currency getCurrency(String code, LocalDate date);

}