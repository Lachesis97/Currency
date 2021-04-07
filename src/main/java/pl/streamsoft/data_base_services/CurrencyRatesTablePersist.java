package pl.streamsoft.data_base_services;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CurrencyRatesTablePersist {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	public void persist(LocalDate date, BigDecimal rate, long codeid) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CurrencyRatesTable currencyRatesTable = new CurrencyRatesTable();
		currencyRatesTable.setDate(date);
		currencyRatesTable.setRate(rate);
		currencyRatesTable.setCodeID(codeid);

		entityManager.persist(currencyRatesTable);
		entityTransaction.commit();
	}

}
