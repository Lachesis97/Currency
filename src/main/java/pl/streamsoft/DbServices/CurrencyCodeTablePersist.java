package pl.streamsoft.DbServices;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CurrencyCodeTablePersist {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	public void persist(String code, String name) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CurrencyCodeTable currencyCodeTable = new CurrencyCodeTable();
		currencyCodeTable.setName(code.toUpperCase());
		currencyCodeTable.setCode(name.toUpperCase());

		entityManager.persist(currencyCodeTable);
		entityTransaction.commit();

	}

}
