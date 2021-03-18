package pl.streamsoft.Db;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.streamsoft.exceptions.CloseConnectionException;
import pl.streamsoft.exceptions.NoDbResultException;
import pl.streamsoft.services.InsertCurrencyDbService;
import pl.streamsoft.services.Strategy;
import pl.streamsoft.services.StringToDate;
import pl.streamsoft.www.Currency;
import pl.streamsoft.www.CurrencyTable;

public class GetCurrencyDB implements Strategy {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");
	
 
	@Override
	public Currency getCurrency(String code, String date) {

		
		StringToDate stringToDate = new StringToDate();
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("GetCurrencyDB");
		query.setParameter("code", code);
		query.setParameter("date", stringToDate.conversion(date));
		
		CurrencyTable currencyTable = null;
		
		
		try {
			
			currencyTable = (CurrencyTable) query.getSingleResult();
			
			if(currencyTable != null) {
			Currency currency = new Currency();
			
			currency.setName(currencyTable.getName());
			currency.setCode(currencyTable.getName());
			currency.setDate(currencyTable.getDate());
			currency.setRate(currencyTable.getRate());
			return currency;
			} else {
				return null;
			}
			
		} catch (NoResultException e) {
			if(currencyTable == null) {
				return null;
			} else {
				throw new NoDbResultException("Brak wyniku w bazie danych.");
			}
		} finally {
			entityManager.close();
		}
	
		
	}

}
