package es.ulpgc.IST.infosierrapp.datos;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.local.BD_local_Acceso;
import es.ulpgc.IST.infosierrapp.datos.local.TablaResultados;
import es.ulpgc.IST.infosierrapp.recursos.HistorialBusquedas;


/**
 * Clase para gestionar las búsquedas y el
 * almacenamiento local de los resultados usando
 * las clases BD_*. Será la interfaz para todas
 * las actividades de la APP.
 * 
 * 
 * @author krlo
 *
 */
public class BuscadorDatos {


	/**
	 * Historial con las última búsquedas realizadas
	 */
	private static final int HISTORY_SIZE = 10;
	private HistorialBusquedas queries_history;	

	/**
	 * Objeto singleton.		
	 */
	private static BuscadorDatos instance = null;
	
	
	// Conexión con base de datos local
	private BD_local_Acceso dbLocal;	
	// Conexón con base de datos remota
	// private DatosInfosierra_Acceso dbRemota;
		
	
	// Constructor privado (usar getBuscador() )
	private BuscadorDatos(Context context) {
		// Historial de búsquedas
		queries_history = new HistorialBusquedas(HISTORY_SIZE);		
		// Conexiones con BDs
		dbLocal=new BD_local_Acceso(context);
		// dbRemota=new DatosInfosierra_Acceso();		
		
		/* TODO
		 * Para probar sin BD remota: 
		 * Vaciamos la dbLocal y la rellenamos para pruebas
		 */
		dbLocal.borrarResultados();		
		dbLocal.rellenaUnPoco();
		
		
	}
	// "Constructor Singleton"
	public static BuscadorDatos getBuscador(Context context) {
		if(instance == null){
			instance = new BuscadorDatos(context);
		}
		return instance;		
	}
	
	
	/**
	 * Busca en la base de datos completa (infosierra)
	 * y almacena los resultados en local (BD_resultados)
	 * 
	 * 
	 * @param cadena patrón para la búsqueda
	 */
	public void buscar(String query_string) {
		
			
		/* Este método deberá:
		 * 
		 * 0. Comprobar que la búsqueda no es repetida,
		 * y si lo es, omitir los pasos 1, 2 y 3
		 * 
		 * 1. Actualizar el historial 
		 * 
		 * 2. Buscar en dbRemota
		 * 
		 * 3. Guardar resultados en dbLocal 
		 *
		 */
				
		// Paso 0:
		if (query_string == null) {
			return;
		}
		if (query_string.equalsIgnoreCase(queries_history.getLast())) {
			return;
		}
		
		// Paso 1:
		queries_history.add(query_string);
		
		// Paso 2:
		// TODO
		
		// Paso 3:
		// TODO

	}
	
	/**
	 * Devuelve la última cadena buscada
	 * @return
	 */
	public String get_cadena_busqueda() {
		return queries_history.getLast();
	}
	
	public String[] get_historial(int cantidad) {
		return queries_history.getLastN(cantidad);
	}
	public String[] get_historial() {
		return queries_history.getHistory();
	}
	public void limpiar_historial() {
		queries_history.clearHistory();
	}


	/**
	 * Devuelve una lista con todos los Anuncios almacenados en la
	 * base de datos local.
	 * 
	 * @return los datos almacenados
	 */
	public List<Anuncio> get_resultados_lista() {
		return dbLocal.get_all_list();
	}


	/**
	 * Devuelve todos los Anuncios almacenados en la
	 * base de datos local mediante un Cursor
	 * 
	 * @return los datos almacenados
	 */
	public Cursor get_resultados_cursor() {		
		return dbLocal.get_all_cursor();
	}
	
	/**
	 * TODO
	 * Método temporal para hacer las búsquedas en la base de datos local
	 * @param query
	 * @return
	 */
	public Cursor buscarEnLocal(String query_string) {
		return dbLocal.buscarEnTags(query_string);
	}


}
