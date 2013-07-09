package es.ulpgc.IST.infosierrapp.datos.local;

import java.net.MalformedURLException;
import java.net.URL;
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
	 * @throws SQLException
	 */
	private void open_db() throws SQLException {
		if ( dbHelper != null) {
			database = dbHelper.getWritableDatabase();
		} else {
			throw new SQLException();
		}
	}
	/**
	 * Cierra la conexión con la BD
	 */
	private void close_db() {
		if(database != null){
			Log.i(LOG_TAG, "Cerrando la base de datos [ " 
					+ dbHelper.getDatabaseName() + " ].");
			database.close();
		}
	}
	

	/**
	 * Devuelve todo el contenido de la BD en una lista
	 * de Anuncios.
	 * 
	 * @return la lista de Anuncios o null en caso de que la db estuviese vacía
	 * o haya algún error  
	 */
	public List<Anuncio> get_all_list() {
		
		// Lista a devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();
		
		//Abre la conexón con la BD
		open_db();
		
		// Obtiene el cursor con todos los resultados de la BD
		Cursor cursor = database.query(TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, null, null, null, null, null);
		
		if (cursor != null) {
			// Recorre la BD mediante el cursor, convirtiendo
			// cada entrada en un Anuncio y añadiéndolo a la lista
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Anuncio anuncio = cursorToAnuncio(cursor);
				anuncios.add(anuncio);
				cursor.moveToNext();
			}
			// Cierra el cursor
			cursor.close();
			// Cierra la BD
			close_db();
			
		} else {
			anuncios = null;			
		}
		
		// Devuelve la lista o null en caso de que no haya
		// encontrado nada
		return anuncios;
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
		
		//Abre la conexón con la BD
		open_db();

		// Obtiene el cursor con todos los resultados de la BD
		Cursor cursor = database.query( TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, 
				null, null, null, null, null);
		
		// Devuelve el cursor
		return cursor;
	}
	
	
	/**
	 * Realiza una búsqueda en la DB devolviendo una lista de Anuncios
	 * 
	 * @param query_string la cadena que se quiere buscar
	 * @return lista de resultados o null si no se encuentra nada o hay error
	 */
	public List<Anuncio> buscarPorTags(String query_string ){

		// Lista a devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();

		//Abre la conexón con la BD
		open_db();

		// Hace la búsqueda
		Cursor cursor = database.query(TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, 
				TablaResultados.COL_TAGS + " MATCH ?",
				new String[] {"*"+query_string+"*"},
				null, null, null);

		// Si hay resultados...
		if (cursor != null) {
			// Recorre los resultados mediante el cursor, convirtiendo
			// cada entrada en un Anuncio y añadiéndolo a la lista
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Anuncio anuncio = cursorToAnuncio(cursor);
				anuncios.add(anuncio);
				cursor.moveToNext();
			}
			// Cierra el cursor y la BD
			cursor.close();
			close_db();
			
		} else {
			anuncios = null;
		}

		return anuncios;		
	}
	
	
	/**
	 * Realiza una búsqueda en la DB devolviendo un Cursor
	 * con los resultados. Importante cerrar el cursor y la
	 * base de datos una vez se terminen de usar.
	 * 
	 * @param query_string la cadena que se quiere buscar
	 * @return cursor a resultados o null si no se encuentra nada o hay error
	 */
	public Cursor buscarEnTags(String query_string) {

		//Abre la conexón con la BD
		open_db();
		
		// Hace la búsqueda
		Cursor cursor = database.query(TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, 
				TablaResultados.COL_TAGS + " MATCH ?",
				new String[] {"*"+query_string+"*"},
				null, null, null);
		
		// Devuelve el cursor
		return cursor;
		
	}
	
	
	
	/**
	 * 
	 * Vacía la tabla Resultados.
	 * 
	 */
	public void borrarResultados() {
		open_db();
		database.execSQL("DROP TABLE IF EXISTS " + TablaResultados.TABLE_NAME);
		dbHelper.onCreate(database);
		close_db();
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
		anuncioCV.put(TablaResultados.COL_FOTO, 		anuncio.get_foto_string() );
		anuncioCV.put(TablaResultados.COL_WEB,			anuncio.get_web_string() );
		anuncioCV.put(TablaResultados.COL_MAPX,			anuncio.get_X() );
		anuncioCV.put(TablaResultados.COL_MAPY, 		anuncio.get_Y() );		
		anuncioCV.put(TablaResultados.COL_TAGS,			anuncio.get_AllTags() );
		anuncioCV.put(TablaResultados.COL_TELEFONOS,	anuncio.get_AllTelefonos() );		
		
		
		// Abre la conexión con la BD
		open_db();
		
		// Realiza la inserción
		// Excepción SQLException si hay error.
		long inserted_ID = database.insert(TablaResultados.TABLE_NAME, null, anuncioCV);
		
		// Cierra la BD
		close_db();

		return inserted_ID;
	}
	
	/**
	 * Procesa una fila de la tabla (objeto Cursor) y
	 * lo convierte en un objeto java Anuncio
	 */
	private Anuncio cursorToAnuncio(Cursor cursor) {
		Anuncio anuncio = new Anuncio();

		// Para cada atributo:
		// 1. Obtiene el índice de la columna
		// 2. Extrae y establece el valor del atributo
		//------		
		int i_id = cursor.getColumnIndex(TablaResultados.COL_ID);
		anuncio.set_id(cursor.getLong(i_id));
		//------
		int i_nombre = cursor.getColumnIndex(TablaResultados.COL_NOMBRE);
		anuncio.set_nombre(cursor.getString(i_nombre));
		//------
		int i_dir = cursor.getColumnIndex(TablaResultados.COL_DIRECCION);
		anuncio.set_direccion(cursor.getString(i_dir));
		//------
		int i_desc = cursor.getColumnIndex(TablaResultados.COL_DESC);
		anuncio.set_descripcion(cursor.getString(i_desc));
		//------
		int i_email = cursor.getColumnIndex(TablaResultados.COL_EMAIL);
		anuncio.set_email(cursor.getString(i_email));
		//------
		int i_web = cursor.getColumnIndex(TablaResultados.COL_WEB);		
		try {
			anuncio.set_web(new URL(cursor.getString(i_web)));
		} catch (MalformedURLException e) {
			anuncio.set_web(null);
		}
		//------
		int i_foto = cursor.getColumnIndex(TablaResultados.COL_FOTO);
		try {
			anuncio.set_foto(new URL(cursor.getString(i_foto)));
		} catch (MalformedURLException e) {
			anuncio.set_foto(null);
		}
		//------
		int i_mapx = cursor.getColumnIndex(TablaResultados.COL_MAPX);		
		anuncio.set_X(cursor.getDouble(i_mapx));
		//------
		int i_mapy = cursor.getColumnIndex(TablaResultados.COL_MAPY);
		anuncio.set_Y(cursor.getDouble(i_mapy));
		//------
		int i_tlfs = cursor.getColumnIndex(TablaResultados.COL_TELEFONOS);
		anuncio.set_AllTelefonos(cursor.getString(i_tlfs));
		//------
		int i_tags = cursor.getColumnIndex(TablaResultados.COL_TAGS);
		anuncio.set_AllTags(cursor.getString(i_tags));

		return anuncio;
	}
	
	
	
	/**
	 * TODO: borrar método y referencias cuando se 
	 * terminen las pruebas iniciales
	 * 
	 * Crea unos cuantos anuncios para pruebas.
	 * 
	 */
	public void rellenaUnPoco() {
		open_db();
		
		Anuncio anuncio1 = new Anuncio();
		anuncio1.set_nombre("Anuncio1 Nombre");
		anuncio1.set_descripcion("Anun1 descripciooon");
		anuncio1.set_direccion("Calle imaginaria, N tal");
		anuncio1.set_email("email@este.com");
		try {
			anuncio1.set_web(new URL("http://www.miweb.com"));
		} catch (MalformedURLException e) {
		}
		anuncio1.set_X(37.893056);
		anuncio1.set_Y(6.474167);
		anuncio1.set_tags(new String[]{"eti1", "eti2", "eti3","eti4"});
		anuncio1.set_telefonos(new String[]{"987654321", "123456789"});		
		insertAnuncio(anuncio1);

		Anuncio anuncio2 = new Anuncio();
		anuncio2.set_nombre("Nombre Anuncio2");
		anuncio2.set_descripcion("Anunu2 desc");
		insertAnuncio(anuncio2);		

		Anuncio anuncio3 = new Anuncio();
		anuncio3.set_nombre("Nombre Anuncio3");
		anuncio3.set_descripcion("Anunu3 desc");
		insertAnuncio(anuncio3);
		
		Anuncio anuncio4 = new Anuncio();
		anuncio4.set_nombre("Nombre_Anuncio4");
		anuncio4.set_descripcion("Cuarto anuncio descripción");
		insertAnuncio(anuncio4);	
		
		close_db();
		
	}
	
	
	
}
