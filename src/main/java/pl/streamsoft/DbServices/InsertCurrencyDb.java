package pl.streamsoft.DbServices;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import pl.streamsoft.services.Currency;

public class InsertCurrencyDb {
	

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");
	
	public void insert(Currency currency, String code, String date) {
		
		InsertCurrencyDbService insertCurrencyDbService = new InsertCurrencyDbService();
		
		if(insertCurrencyDbService.itExist(currency, code, date)) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;
		
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			CurrencyTable currencyTable = new CurrencyTable();
			
			
			currencyTable.setName(currency.getName());
			currencyTable.setCode(currency.getCode());
			currencyTable.setDate(currency.getDate());
			currencyTable.setRate(currency.getRate());
			
			entityManager.persist(currencyTable);
			entityTransaction.commit();
			
			System.out.println("Dodano kurs \"" + currency.getName() + "\" z dnia \"" + currency.getDate() + "\" do bay danych.");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			entityManager.close();
		}
		
		}
	}

}
