package pl.streamsoft.services;

import java.time.LocalDate;

public interface RateService {

	public String getCurrency(String code, LocalDate date);

}