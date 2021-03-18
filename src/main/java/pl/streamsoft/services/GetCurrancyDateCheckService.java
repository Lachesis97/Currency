package pl.streamsoft.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import pl.streamsoft.exceptions.CloseConnectionException;

public class GetCurrancyDateCheckService {
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	
	
	public String checkDate(String code, String date) {
		
		try {
			
			HttpGet request = new HttpGet("http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date + "/?format=json");
			StringToDate stringToDate = new StringToDate();
			
			int i = 0;
			Date newdate = newdate = stringToDate.conversion(date);
			CloseableHttpResponse response = httpClient.execute(request);
			
			while(response.getStatusLine().getStatusCode() != 200){
				
					request.releaseConnection();
					response.close();
					
					SubtractOneDay subtractOneDay = new SubtractOneDay();
					
					newdate = subtractOneDay.substract(newdate);

					HttpGet requestt = new HttpGet("http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + new SimpleDateFormat("yyyy-MM-dd").format(newdate) + "/?format=json");
					response = httpClient.execute(requestt);
					i++;
				
			}
	        
			if(i != 0) {
				System.out.println("Dzisiejszy kurs jest niedostêpny.");
			}
	        
	         return new SimpleDateFormat("yyyy-MM-dd").format(newdate).toString();
             
		} catch (Exception e) {
			
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException("Nie uda³o siê zamkn¹æ po³¹czenia");
			}
		}
		

		
		
		return null;
	}
	

}
