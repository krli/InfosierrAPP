package es.ulpgc.IST.infosierrapp.datos.local;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;


/**
 * Clase DAO (Data Access Object) (o Facade) que gestionará
 * el acceso a la base de datos: abstrae a la aplicación del
 * del manejo de la BD. Accede, busca, modifica y convierte los
 * datos en objetos java (Anuncio).
 * 
 * Una alternativa a esta clase es un ContentProvider: más nuevo,
 * más eficiente y mejor. Poco a poco.
 * 
 * @author krlo
 */
public class BD_local_Acceso {

	// Etiqueta para logs
	public static final String LOG_TAG="BD_local_Acceso";	
	// Base de datos
	private SQLiteDatabase database = null;
	// Helper
	private BD_local_SQLiteHelper dbHelper = null;

	/**
	 * Constructor
	 * Inicializa el helper 
	 * @param context
	 */
	public BD_local_Acceso(Context context) {
		dbHelper = BD_local_SQLiteHelper.getHelper(context);
	}

	/**
	 * Abre la conexión con la BD
	 * @return true si la apertura se hizo correctamente
	 * @return false si hubo algún error
	 */
	private boolean open_db() {
		Log.i(LOG_TAG, "Abriendo la base de datos [" 
				+ dbHelper.getDatabaseName() + "]...");
		try {
			if ( dbHelper != null) {
				database = dbHelper.getWritableDatabase();
				return true;
			} else {
				Log.e(LOG_TAG, "El Helper no está disponible [" 
					+ dbHelper.getDatabaseName() + "].");
			}
		}catch (SQLException e) {
			Log.e(LOG_TAG, "getWritableDatabase() error [" 
					+ dbHelper.getDatabaseName() + "].");				
		}
		return false;
	}
	/**
	 * Cierra la conexión con la BD
	 */
	public void close_db() {
		Log.i(LOG_TAG, "Cerrando la base de datos [" 
				+ dbHelper.getDatabaseName() + "]...");
		if(database != null){
			database.close();
		}
	}
	
	/**
	 * Devuelve todo el contenido de la BD mediante un cursor.
	 * Importante cerrar el cursor y la base de datos una vez se
	 * terminen de usar.
	 * 
	 * @return un cursor apuntando a todos los resultados o null 
	 * si la db estaba vacía o hubo algún error 
	 */
	public Cursor get_all_cursor() {		
		// Cursor devuelto
		Cursor cursor = null;
		//Abre la conexón con la BD
		if ( open_db() ) {
			// Obtiene el cursor con todos los resultados de la BD
			cursor = database.query( TablaResultados.TABLE_NAME,
					TablaResultados.ALL_COLUMNS, 
					null, null, null, null, null);
		} // error abriendo la db
		return cursor;
	}
	
	/**
	 * Realiza una búsqueda en la DB devolviendo un Cursor
	 * con los resultados. Importante cerrar el cursor y la
	 * base de datos una vez se terminen de usar.
	 * 
	 * @param query_string la cadena que se quiere buscar
	 * @return cursor a resultados o null si no se encuentra nada o hay error
	 */
	public Cursor buscarEnTags_cursor(String query_string) {
		// Cursor devuelto
		Cursor cursor = null;		
		//Abre la conexón con la BD
		if ( open_db() ) {
			// Busca
			try {
				cursor = database.query(
						TablaResultados.TABLE_NAME,
						TablaResultados.ALL_COLUMNS, 
						TablaResultados.COL_TAGS + " LIKE "+
								"'%"+query_string+"%'",
								null, null, null, null);
			}catch (SQLException e) {			
			}		
		}// error abriendo la db
		return cursor;
	}

	/**
	 * Devuelve todo el contenido de la BD en una lista
	 * de Anuncios.
	 * 
	 * @return la lista de Anuncios o null en caso de que la db estuviese vacía
	 * o haya algún error  
	 */
	public List<Anuncio> get_all_list() {		
		// Obtiene un cursor con toda la tabla, 
		// convierte a Lista y devuelve
		return cursorToList(  get_all_cursor() );		
	}
	
	/**
	 * Realiza una búsqueda en la DB devolviendo una lista de Anuncios
	 * 
	 * @param query_string la cadena que se quiere buscar
	 * @return lista de resultados o null si no se encuentra nada o hay error
	 */
	public List<Anuncio> buscarEnTags_lista(String query_string){
		// Obtiene un cursor con losr resultados, 
		// de la búsqueda, convierte a Lista y devuelve
		return cursorToList( buscarEnTags_cursor(query_string) );
	}

	/**
	 * Guarda un objeto Anuncio en la BD
	 * @param objeto de tipo Anuncio que se quiere insertar
	 * @return el ID de la nueva fila creada. -1 si hubo algún error.
	 */
	public long insertAnuncio(Anuncio anuncio) {

		// Crea el contenedor para meter en la BD
		ContentValues anuncioCV = new ContentValues();
		// Procesa todos los campos excepto el ID (se lo asignará automáticamente la BD)
		anuncioCV.put(TablaResultados.COL_NOMBRE,		anuncio.get_nombre() );		
		anuncioCV.put(TablaResultados.COL_DIRECCION,	anuncio.get_direccion() );
		anuncioCV.put(TablaResultados.COL_DESC,			anuncio.get_descripcion() );
		anuncioCV.put(TablaResultados.COL_EMAIL,		anuncio.get_email() );
		anuncioCV.put(TablaResultados.COL_FOTO, 		anuncio.get_foto() );
		anuncioCV.put(TablaResultados.COL_WEB,			anuncio.get_web() );
		anuncioCV.put(TablaResultados.COL_MAPX,			anuncio.get_X() );
		anuncioCV.put(TablaResultados.COL_MAPY, 		anuncio.get_Y() );		
		anuncioCV.put(TablaResultados.COL_TAGS,			anuncio.get_AllTags() );
		anuncioCV.put(TablaResultados.COL_TELEFONOS,	anuncio.get_AllTelefonos() );		

		// Abre la conexión con la BD
		long inserted_ID = -1;
		if ( open_db() ) {
			// Realiza la inserción
			try {
				inserted_ID = database.insert(TablaResultados.TABLE_NAME, null, anuncioCV);
			} catch (SQLException e) {				
			}	
			// Cierra la BD
			close_db();		
		} // error abriendo la db
		
		return inserted_ID;
	}
	
	/**
	 * Vacía la tabla Resultados.
	 */
	public void borrarTablaResultados() {
		if (open_db()) {
			database.execSQL("DROP TABLE IF EXISTS " + TablaResultados.TABLE_NAME);
			dbHelper.onCreate(database);
			close_db();
		}
	}
	
	/**
	 * Transforma un conjunto de filas marcado por un cursor
	 * en una lista de objetos Anuncio
	 */
	private List<Anuncio> cursorToList(Cursor cursor) {
		// Lista a devolver
		List<Anuncio> anuncios = null;
		
		if (cursor != null) {			
			// Inicializa la lista
			anuncios = new ArrayList<Anuncio>();
			// Recorre la BD mediante el cursor, convirtiendo
			// cada entrada en un Anuncio y añadiéndolo a la lista
			cursor.moveToFirst();
			while ( !cursor.isAfterLast() ) {
				Anuncio anuncio = Anuncio.cursorToAnuncio(cursor);
				anuncios.add(anuncio);
				cursor.moveToNext();
			}
			// Cierra el cursor
			cursor.close();
			// Cierra la BD
			close_db();	
		}
		return anuncios;
	}

}
