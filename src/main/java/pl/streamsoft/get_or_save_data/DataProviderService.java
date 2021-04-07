package pl.streamsoft.get_or_save_data;

import java.time.LocalDate;

import pl.streamsoft.services.Currency;

public interface DataProviderService {

	public Currency validateDate(String code, LocalDate date);

	public Currency getCurrency(String code, LocalDate date);

	public DataProviderService getNextStrategy();

}