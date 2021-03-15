package pl.streamsoft.www;

public class Context {
	
	private Strategy strategy;
	 
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    
    
    public void execute(String code) throws Exception {
        strategy.getCurrency(code);
    }
    

}
