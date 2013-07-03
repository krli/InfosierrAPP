package es.ulpgc.IST.infosierrapp.datos;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import es.ulpgc.IST.infosierrapp.R;


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
public class BuscadorDatos extends Activity{

	///////////////////
	///////ADAPTER/////
	///////////////////

	/**
    * Definimos constante con el nombre de la tabla
    */

	public static final String C_TABLA = "ANUNCIOS" ;

	/**
    * Definimos constantes con el nombre de las columnas de la tabla
    */
	public static final String C_COLUMNA_ID = "_id";
	public static final String C_COLUMNA_NOMBRE = "anuncio_nombre";
	public static final String C_COLUMNA_ETIQUETAS = "anuncio_etiquetas";
	public static final String C_COLUMNA_DIRECCION = "anuncio_direccion";
	public static final String C_COLUMNA_EMAIL = "anuncio_email";
	public static final String C_COLUMNA_TELEFONO = "anuncio_telefono";
	public static final String C_COLUMNA_WEB = "anuncio_web";
	public static final String C_COLUMNA_DESCRIPCION = "anuncio_descripcion";
	private Context contexto;
	private BD_infosierra dbHelper;
	private SQLiteDatabase db;
	private static Cursor cursor;
	private static String cadena;

	/**
    * Definimos lista de columnas de la tabla para utilizarla en las consultas a la base de datos
    */
	private String[] columnas = new String[]{ C_COLUMNA_ID, C_COLUMNA_NOMBRE, C_COLUMNA_ETIQUETAS, C_COLUMNA_DIRECCION, C_COLUMNA_EMAIL, C_COLUMNA_TELEFONO, C_COLUMNA_WEB, C_COLUMNA_DESCRIPCION} ;

	/**
	 * Busca en la base de datos completa (BD_infosierra)
	 * y almacena los resultados en local (BD_resultados)
	 * 
	 * @param cadena patrón para la búsqueda
	 */
	public  void buscar(String cadena) {

		// 0. Comprobar que no es la misma b��squeda anterior,
		// y si lo es, omitir los pasos siguientes
		// 1. Busca en BD_infosierra
		// 2. Guarda en BD_resultados

		
		 cursor = managedQuery( BD_infosierra.CONTENT_URI, null, null,
				new String[] {cadena}, null);

//		 cursor = CursorLoader(this, BD_infosierra.CONTENT_URI, null, null,
//						new String[] {cadena}, null);
		 
		

		BuscadorDatos.cadena=cadena;
		
		
	}
	public  void buscar(String cadena, int cp) {
		// otros m��todos para b��squedas avanzadas


	}

	/**
	 * Devuelve los resultados que se mantienen de la ��ltima
	 * b��squeda en local (BD_resultados). Digamos que traduce
	 * del tipo en que se guarden en la BD al tipo de objetos
	 * que usamos en nuestra APP
	 * 
	 * @return los datos almacenados
	 */
	//TODO: definir el tipo que usaremos para esta lista
	// (ArrayList, Vector...) que sea m��s ��ptimo
	public static  Anuncio[] getResultados() {
		Anuncio[] resultados = new Anuncio[3];

		return resultados;
	}

	/**
	 * ���� Devuelve los resultados que se mantienen de la ��ltima
	 * b��squeda en local (BD_resultados) UNO a UNO. ??
	 *  
	 * @return los datos almacenados
	 */

	public  Anuncio getResultadosUnoAuno() {
		Anuncio resultados = new Anuncio();

		return resultados;
	}

	public static SimpleCursorAdapter initAdapter(Context context, String [] from, int [] to){
		// Create a simple cursor adapter for the definitions and apply them to the ListView
		SimpleCursorAdapter words = new SimpleCursorAdapter(context,
				R.layout.vista_v_maestro, cursor, from, to);
		return words;

	}



	public static Cursor getCursor(){
		return cursor;
	}

	public static String getCadena(){
		return cadena;
	}


}
