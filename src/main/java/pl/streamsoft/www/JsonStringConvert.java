package pl.streamsoft.www;

public class JsonStringConvert {
	String result;

	public JsonStringConvert(String result) {
		this.result = result;
	}
	
	public String convert() {
    String[] cutresult = result.split("\\[");    
    
    String finalresult = cutresult[0].substring(0, cutresult[0].length() - 8) + cutresult[1].substring(1, cutresult[1].length() - 2);
	
    return finalresult;
	}
	

}
