package pl.streamsoft.www;

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
	
	private String code;
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();




	public String getCurrency(String code) throws Exception{
		BigDecimal Curr = new BigDecimal("1");
		
		
		HttpGet request = new HttpGet("http://api.nbp.pl/api/exchangerates/rates/a/" + code +"/?format=json");
		
		
		try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
		}
		
		
		
		return Curr.toString();
	}

	
    public void close() throws IOException {
        httpClient.close();
    }



	
	

}
