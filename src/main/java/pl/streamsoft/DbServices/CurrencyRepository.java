package pl.streamsoft.DbServices;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.streamsoft.exceptions.DeleteFromDataBaseException;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.DataProviderService;

public class CurrencyRepository implements CurrencyRepo, DataProviderService {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");
	private DataProviderService nextStrategy;

	public CurrencyRepository() {

	}

	public CurrencyRepository(DataProviderService dataProviderService) {
		this.nextStrategy = dataProviderService;
	}

	@Override
	public void addCurrency(Currency currency) {

		if (codeExists(currency.getCode())) {
			CurrencyCodeTablePersist codeTablePersist = new CurrencyCodeTablePersist();
			codeTablePersist.persist(currency.getName(), currency.getCode());
		}

		if (rateExists(currency.getCode(), currency.getDate())) {
			CurrencyRatesTablePersist ratesTablePersist = new CurrencyRatesTablePersist();
			ratesTablePersist.persist(currency.getDate(), currency.getRate(), getCurrencyId(currency.getCode()));
		}
	}

	@Override
	public void deleteSingleRate(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query = entityManager.createQuery("DELETE CurrencyRatesTable r WHERE r.date = :date AND r.cid = :cid");
		query.setParameter("date", date);
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));
		try {
			query.executeUpdate();
			entityTransaction.commit();

		} catch (IllegalStateException e) {
			throw new DeleteFromDataBaseException("èle sformu≥owane zapytanie", e);
		}

	}

	@Override
	public void updateSingleRate(String code, LocalDate date) {

	}

	@Override
	public long getCurrencyId(String code) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCode");
		query.setParameter("code", code.toUpperCase());
		CurrencyCodeTable currencyCodeTable = null;

		try {
			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();
			return currencyCodeTable.getId();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			entityManager.close();
		}
		return 0;

	}

	@Override
	public boolean rateExists(String code, LocalDate date) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetRate");
		query.setParameter("date", date);
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));

		try {
			CurrencyRatesTable currencyRatesTable = (CurrencyRatesTable) query.getSingleResult();

			if (currencyRatesTable != null) {
				return false;
			} else {
				return true;
			}
		} catch (NoResultException e) {
			return true;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean codeExists(String code) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCode");
		query.setParameter("code", code.toUpperCase());
		CurrencyCodeTable currencyCodeTable = null;

		try {
			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {
				return false;
			} else {
				return true;
			}
		} catch (NoResultException e) {
			return true;
		} finally {
			entityManager.close();
		}

	}

	@Override
	public Currency getCurrency(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCurrency");
		query.setParameter("code", code.toUpperCase());
		query.setParameter("date", date);
		CurrencyCodeTable currencyCodeTable = null;
		Currency currency = null;

		try {
			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {
				for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
					currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
							currencyRatesTable.getDate(), currencyRatesTable.getRate());
				}
				return currency;
			} else {
				if (nextStrategy != null) {
					return nextStrategy.getCurrency(code, date);
				}
				return null;

			}

		} catch (NoResultException e) {
			if (nextStrategy != null) {
				return nextStrategy.getCurrency(code, date);
			}
			return null;
		} finally {
			entityManager.close();
		}

	}

	@Override
	public Currency validateDate(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCurrency");
		query.setParameter("code", code.toUpperCase());
		query.setParameter("date", date);
		CurrencyCodeTable currencyCodeTable = null;
		Currency currency = null;

		try {
			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {
				for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
					currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
							currencyRatesTable.getDate(), currencyRatesTable.getRate());
				}
				return currency;
			} else {
				return null;

			}

		} catch (NoResultException e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

}
