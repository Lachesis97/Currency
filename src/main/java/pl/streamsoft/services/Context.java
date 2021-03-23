package pl.streamsoft.services;

import java.time.LocalDate;

public class Context {

	private RateService strategy;

	public Context(RateService strategy) {
		this.strategy = strategy;
	}

	public String execute(String code, LocalDate date) {
		return strategy.getCurrency(code, date);
	}

}
