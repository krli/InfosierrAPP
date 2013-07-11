package es.ulpgc.IST.infosierrapp.datos;

import android.os.AsyncTask;

/**
 * Ejecuta una tarea de búsqueda en segundo plano informando
 * a la actividad (con interfaz ListenerTareaBusqueda) del
 * progreso
 * 
 * @author krlo
 */
public class TareaBusqueda extends AsyncTask<String, Integer, Boolean> {

	/**
	 * Interfaz para comunicación con la actividad
	 * que ejecuta la tarea
	 */
	private ListenerTareaBusqueda listener;
	
	/**
	 * Clase que contiene toda la lógica para la 
	 * búsqueda.
	 */
	private BuscadorDatos buscador;
	
	/**
	 * Constructor
	 * @param context contexto de la aplicación
	 * @param actividad actividad que implementa ListenerTareaBusqueda que 
	 * recibirá los avisos de progreso
	 */
	public TareaBusqueda(BuscadorDatos buscador, ListenerTareaBusqueda actividad) {
		this.listener=actividad;
		this.buscador=buscador;
	}	
	
	/**
	 * Se ejecuta justo antes de doInBackground		
	 */
	@Override
	protected void onPreExecute() {
		// notifica al buscador
		listener.busquedaIniciada();			
	}		

	/**
	 * Proceso que se ejecuta en segundo plano
	 */
	@Override
	protected Boolean doInBackground(String... param) {
		
		/** Comprobación de argumentos **/
		// Cantidad de argumentos
		int n_params=param.length;
		// Sólo esperamos una cadena para la búsqueda
		if (n_params != 1) {
			return false;
		}
		String query_string = param[0];
		
		/** Búsqueda **/
		
		publishProgress(ListenerTareaBusqueda.MIN_PROGRESS);
		
		
		// BuscadorDatos.loqueseaYtal...
		buscador.buscar(query_string);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		publishProgress(ListenerTareaBusqueda.MAX_PROGRESS);
		
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
		listener.busquedaFinalizada(result);		
	}
	
	/**
	 * Se ejecuta si la tarea es cancelada a mitad del proceso
	 */
	@Override
	protected void onCancelled(Boolean result) {
		// notifica al buscador
		listener.busquedaCancelada(); 
	}		
	
	
}