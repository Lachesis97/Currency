package pl.streamsoft.services;

public class JsonStringConvert {
	String result;

	public String convert(String result) {
		String[] cutresult = result.split("\\[");

		String finalresult = cutresult[0].substring(0, cutresult[0].length() - 8)
				+ cutresult[1].substring(1, cutresult[1].length() - 2);

		return finalresult;
	}

}
