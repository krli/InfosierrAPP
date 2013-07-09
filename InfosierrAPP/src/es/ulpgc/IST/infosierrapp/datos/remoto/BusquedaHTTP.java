package es.ulpgc.IST.infosierrapp.datos.remoto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.net.http.AndroidHttpClient;

public class BusquedaHTTP {

	public static final String INFOSIERRA_POST_URL="http://www.infosierra.es/buscador";
	public static final String INFOSIERRA_POST_ID="formulario-buscador";
	public static final String INFOSIERRA_POST_NAME="formulario-buscador";
	public static final String USER_AGENT="infosierrAPP";


	public BusquedaHTTP() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Realiza una petición de búsqueda a la web (hace un 
	 * POST al formulario) y devuelve la respuesta HTTP generada.
	 */
	private HttpResponse postQuery(String queryString) {

		HttpResponse response;
		// Create a new HttpClient and Post Header
		// HttpClient httpclient = new DefaultHttpClient();
		AndroidHttpClient httpclient = AndroidHttpClient.newInstance(USER_AGENT);
		HttpPost httppost = new HttpPost(INFOSIERRA_POST_URL);
		
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("busqueda", queryString));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			response=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response=null;
		}
		
		return response;
	} 


	// Fast Implementation
	private StringBuilder inputStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		// Read response until the end
		try {
			while ((line = rd.readLine()) != null) { 
				total.append(line); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			total=null;
		}
		
		
		// Return full string
		return total;
	}




}
