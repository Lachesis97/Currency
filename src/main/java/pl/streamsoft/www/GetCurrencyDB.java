package pl.streamsoft.www;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.text.SimpleDateFormat;
import java.util.List;

public class GetCurrencyDB implements Strategy {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");
	
	
	@Override
	public Currency getCurrency(String code) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("GetCurrencyDB");
		List result = null;
		
		
		try {
			
			result = query.getResultList();
			
			Currency currency = new Currency();
			
			currency.setName(result.get(1).toString());
			currency.setCode(result.get(2).toString());
			currency.setDate(null);
			currency.setRate(null);
			
			return currency;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			entityManager.close();
		}
		
	}

}
