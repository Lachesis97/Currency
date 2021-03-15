package pl.streamsoft.www;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.math.BigDecimal;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetCurrencyJson implements Strategy {
	
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();




	public String getCurrency(String code) throws Exception{
		
		
		
		
		
		HttpGet request = new HttpGet("http://api.nbp.pl/api/exchangerates/rates/a/" + code +"/?format=json");
		
		
		try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
           
            

            if (entity != null) {
                String result = EntityUtils.toString(entity);  
                System.out.println(result);
                
                JsonN
                
                System.out.println(currency);
            }
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		return "";
	}

	
    public void close() throws IOException {
        httpClient.close();
    }



	
	

}
