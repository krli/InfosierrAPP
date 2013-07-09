package es.ulpgc.IST.infosierrapp.datos;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.local.BD_local_Acceso;
import es.ulpgc.IST.infosierrapp.datos.local.TablaResultados;


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
			
		// Por ahora vaciamos la dbLocal y la rellenamos para pruebas
		dbLocal.borrarResultados();		
		dbLocal.rellenaUnPoco();

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
	 * TODO: Esto parece más propio de la configuración del 
	 * layout del maestro-detalle ¿¿ Sacar de aquí y meter 
	 * directamente allí ??
	 * 
	 * @param context
	 * @return
	 */
	public SimpleCursorAdapter getCursorAdapter(Context context){

		// Especifica las columnas que se mostraran en el resultado
		String[] from = new String[] { TablaResultados.COL_NOMBRE, TablaResultados.COL_DESC, TablaResultados.COL_DIRECCION,
				TablaResultados.COL_EMAIL, TablaResultados.COL_TELEFONOS, TablaResultados.COL_WEB, TablaResultados.COL_MAPX, TablaResultados.COL_MAPY, TablaResultados.COL_FOTO};

		// Especifica los correspondintes elementos del layout 
		int[] to = new int[] { R.id.txtNombre, R.id.txtDescripcion, R.id.txtDireccion, R.id.txtEmail, R.id.txtTelefono, R.id.txtWeb, R.id.txtX, R.id.txtY, R.id.image};

		//crea el cursor adapter para las definiciones dadas y lo aplica a la ListView
		//Inicializa el adaptador.
		//El 3 argumento es null porque el cursor aun no ha sido cargado por primera vez
		//El ultimo argumento es 0 para prevenir el registro del Observer (CursorLoader lo hace directamente)
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(context, R.layout.vista_v_item,
				get_resultados_cursor(), from, to, 0);

		// Devuelve el CursorAdapter a ListPresenter
		return cursorAdapter;

	}
	
	
	/******************* Clase HistorialBusquedas *****************/
	/**
	 * TODO
	 * Clase que implementa un historial para
	 * almacenar un número fijo de cadenas. Cuando está
	 * lleno y se guardan más cadenas va borrando
	 * las más viejas.
	 *  
	 * @author krlo
	 */
	private class HistorialBusquedas {
		
		/**
		 * Vector almacén
		 */
		private String[] history;
		
		/**
		 * Índice de la posición para guardar la
		 * próxima cadena
		 */
		private int _pointer;
		
		/**
		 * Constructor
		 * @param size tamaño del historial
		 */
		public HistorialBusquedas(int size) {
			history=new String[size];
			_pointer=0;
		}	
		
		/**
		 * Incrementa el índice que indica la posición
		 * para la próxima cadena almacenada. Vuelve a 0
		 * cuando llega al final del vector.
		 */
		private void increPointer() {
			_pointer++;
			if (getPointer() >= history.length) {
				_pointer=0;
			}
		}
		/**
		 * Devuelve la posición para guardar la próxima búsqueda 
		 */
		private int getPointer(){
			return _pointer;
		}
		/**
		 * Devuelve la última String añadida 
		 */
		public String getLast() {		
			return history[getLastIndex()];
		}
		/**
		 * Devuelve la posición de la última búsqueda guardada 
		 */
		private int getLastIndex() {
			int last = getPointer()-1;
			if (last < 0) {
				last = history.length - 1;
			}
			return last;
		}
		
		/**
		 * Añade una cadena al historial
		 */
		public void add(String cadena) {			
			history[ getPointer() ] = cadena;		
			increPointer();
		}		
		/**
		 * Borra todo el historial
		 */
		@SuppressWarnings("unused")
		public void clearHistory() {
			history = new String[history.length];
		}
		/**
		 * Devuelve todo el historial
		 */
		@SuppressWarnings("unused")
		public String[] getHistory() {
			return history;
		}		
		/**
		 * Devuelve las últimas N cadenas añadidas. Si N
		 * es mayor que el tamaño del historial se devuelve
		 * se ignora y se devuelve un vector del tamaño 
		 * del historial
		 *  
		 * @param N número de Strings que se quieren recuperar
		 * @return vector de búsquedas ordenadas desde la más reciente
		 */
		public String[] getLastN(int N) {			
			// Vector respuesta
			String[] queries;			
			// Calcula tamaño de queries
			if (N > history.length) {
				queries = new String[history.length];
			} else {
				queries = new String[N];	
			}			
			
			// Rellena queries con las últimas búsquedas:
			// desde LastIndex hacia atrás
			int index = getLastIndex();
			for(int k=0; k < queries.length; k++) {
				
				queries[k]=history[index];				
				// Actualiza index (vuelve al final cuando llega a 0)
				index--;
				if (index < 0 ) {
					index=history.length -1;
				}
			}			
			return queries;
		}
		
	} /***************** Fin Clase HistorialBusquedas ***************/
	


}
