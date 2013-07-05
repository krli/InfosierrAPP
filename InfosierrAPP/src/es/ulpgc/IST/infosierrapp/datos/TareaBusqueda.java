package es.ulpgc.IST.infosierrapp.datos;

import android.os.AsyncTask;

/**
 * Ejecuta una tarea de búsqueda en segundo plano informando
 * al buscador (interfaz IfazActBuscador) del inicio, progreso y fin
 * 
 * @author krlo
 */
public class TareaBusqueda extends AsyncTask<String, Integer, Boolean> {

	/**
	 * Interfaz para comunicación con el buscador que ejecuta la tarea
	 */
	private IfazActBuscador buscador;
	
	public TareaBusqueda(IfazActBuscador buscador) {
		this.buscador=buscador;
	}	
	
	/**
	 * Se ejecuta justo antes de doInBackground		
	 */
	@Override
	protected void onPreExecute() {
		// notifica al buscador
		buscador.busquedaIniciada();		
	}		

	/**
	 * Ejecuta la tarea
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
		
		// BuscadorDatos.loqueseaYtal...
		 BuscadorDatos.buscar(query_string);
		
		publishProgress(IfazActBuscador.MIN_PROGRESS);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		publishProgress(IfazActBuscador.MAX_PROGRESS);
		
		return true;
	}
	
	/**
	 * Para actualizar indicadores de progreso
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		// notifica al buscador
		for (Integer value : values) {
			buscador.progresoBusqueda(value.intValue());
		}
	}		
	
	/**
	 * Se ejecuta al finalizar doInBackground
	 */
	@Override
	protected void onPostExecute(Boolean result){
		// notifica al buscador
		buscador.busquedaFinalizada(result);		
	}
	
	/**
	 * Se ejecuta si la tarea es cancelada a mitad del proceso
	 */
	@Override
	protected void onCancelled(Boolean result) {
		// notifica al buscador
		buscador.busquedaCancelada(); 
	}		
	
	
}