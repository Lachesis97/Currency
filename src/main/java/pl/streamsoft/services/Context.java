package pl.streamsoft.services;

import java.util.Date;

public class Context {
	
	private Strategy strategy;
	 
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    
    
    public Currency execute(String code, String date){
        return strategy.getCurrency(code, date);
    }
    

}
