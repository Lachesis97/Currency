package pl.streamsoft.www;

public class Context {
	
	private Strategy strategy;
	 
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    
    
    public Currency execute(String code){
        return strategy.getCurrency(code);
    }
    

}
