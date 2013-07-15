package es.ulpgc.IST.infosierrapp.eltiempo;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.webkit.WebView;
import es.ulpgc.IST.infosierrapp.datos.ListenerTareaAsync;

/**
 * Ejecuta la carga de una web en segundo plano informando
 * a la actividad (con interfaz ListenerTareaAsync) del
 * progreso
 * 
 * @author krlo
 */
public class TareaWebLoad extends AsyncTask<String, Integer, Boolean> {

	/**
	 * Interfaz para comunicación con la actividad
	 * que ejecuta la tarea
	 */
	private ListenerTareaAsync listener;
	/**
	 * WebView en que se debe cargar la web
	 */
	private WebView webview;

	
	/**
	 * Constructor
	 * @param context contexto de la aplicación
	 * @param actividad actividad que implementa ListenerTareaAsync que 
	 * recibirá los avisos de progreso
	 */
	public TareaWebLoad(WebView webview, ListenerTareaAsync actividad) {
		this.listener=actividad;
		this.webview=webview;
	}	
	
	/**
	 * Se ejecuta justo antes de doInBackground		
	 */
	@Override
	protected void onPreExecute() {
		// notifica al buscador
		listener.tareaIniciada();			
	}		

	/**
	 * Proceso que se ejecuta en segundo plano
	 */
	@Override
	protected Boolean doInBackground(String... param) {
		
		/** Comprobación de argumentos **/
		// Cantidad de argumentos
		int n_params=param.length;
		// Sólo esperamos una cadena con la URL a la web
		if (n_params != 1) {
			return false;
		}
		String string_url = param[0];
		
		// Si la url no está bien formada, fin sin éxito
		try {
			new URL(string_url);
		} catch (MalformedURLException e) {
			return false;
		}
		
		/** Carga la web **/
		webview.loadUrl(string_url);
		
		return true;
	}
	
	/**
	 * Para actualizar indicadores de progreso
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		// notifica al buscador
		// for (Integer value : values) {
		//	listener.progresoBusqueda(value.intValue());
		//}
	}		
	
	/**
	 * Se ejecuta al finalizar doInBackground
	 */
	@Override
	protected void onPostExecute(Boolean result){
		// notifica al buscador
		listener.tareaFinalizada(result);		
	}
	
	/**
	 * Se ejecuta si la tarea es cancelada a mitad del proceso
	 */
	@Override
	protected void onCancelled(Boolean result) {
		// notifica al buscador
		listener.tareaCancelada(); 
	}		
	
	
}