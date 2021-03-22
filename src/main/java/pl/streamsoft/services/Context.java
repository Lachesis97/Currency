package pl.streamsoft.services;

import java.time.LocalDate;

public class Context {

	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public Currency execute(String code, LocalDate date) {
		return strategy.getCurrency(code, date);
	}

}
