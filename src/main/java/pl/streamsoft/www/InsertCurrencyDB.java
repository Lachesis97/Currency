package pl.streamsoft.www;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class InsertCurrencyDB {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");
	
	public static void insert(Currency currency) {
		
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
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			entityManager.close();
		}
		
		
	}

}
