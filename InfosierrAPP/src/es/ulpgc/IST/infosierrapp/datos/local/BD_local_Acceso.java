package es.ulpgc.IST.infosierrapp.datos.local;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.IST.infosierrapp.datos.Anuncio;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


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
	public static final String LOGS_TAG="infosierAPP";	
	// Base de datos
	private SQLiteDatabase database;
	// Helper
	private BD_local_SQLiteHelper helper;

	/**
	 * Constructor
	 * Inicializa el helper
	 * @param context
	 */
	public BD_local_Acceso(Context context) {
		helper = BD_local_SQLiteHelper.getHelper(context);
	}
	
	/**
	 * Abre la conexión con la BD
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		if ( helper != null) {
			database = helper.getWritableDatabase();
		} else {
			throw new SQLException();
		}
	}
	/**
	 * Cierra la conexión con la BD
	 */
	public void close() {
		if(database != null){
			Log.i(LOGS_TAG, "Cerrando la base de datos [ " + helper.getDatabaseName() + " ].");
			database.close();
			database = null;
		}
	}

	
// Código sacado de: http://www.vogella.com/articles/AndroidSQLite/article.html
// Ejemplos para crear y eliminar Comments (= Anuncio)	
//	public Comment createComment(String comment) {
//	    ContentValues values = new ContentValues();
//	    values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
//	    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
//	        values);
//	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//	        null, null, null);
//	    cursor.moveToFirst();
//	    Comment newComment = cursorToComment(cursor);
//	    cursor.close();
//	    return newComment;
//	  }
//
//	  public void deleteComment(Comment comment) {
//	    long id = comment.getId();
//	    System.out.println("Comment deleted with id: " + id);
//	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//	        + " = " + id, null);
//	  }


	/**
	 * Devuelve todo el contenido de la BD en una lista
	 * de Anuncios.
	 * 
	 * @return
	 */
	public List<Anuncio> getResultados() throws SQLException {
		
		// Lista a devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();
		
		// Abre la conexión con la BD
		open();
		
		// Obtiene el cursor con todos los resultados de la BD
		Cursor cursor = database.query(BD_local_SQLiteHelper.TABLE_RESULTADOS,
				BD_local_SQLiteHelper.ALL_COLUMNS, null, null, null, null, null);

		// Recorre la BD mediante el cursor, convirtiendo
		// cada entrada en un Anuncio y añadiéndolo a la lista
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Anuncio anuncio = cursorToAnuncio(cursor);
			anuncios.add(anuncio);
			cursor.moveToNext();
		}
		// Cierra el cursor, la BD y devuelve la lista
		cursor.close();		
		close();
		return anuncios;
	}

	/**
	 * TODO
	 * Procesa una fila de la tabla (objeto Cursor) y
	 * lo convierte en un objeto java Anuncio
	 */
	private Anuncio cursorToAnuncio(Cursor cursor) {
		Anuncio anuncio = new Anuncio();

		// Indice de la columna del campo id
		int i_id = cursor.getColumnIndex(BD_local_SQLiteHelper.COL_ID);
		anuncio.set_id(cursor.getInt(i_id));

		int i_nombre = cursor.getColumnIndex(BD_local_SQLiteHelper.COL_NOMBRE);
		anuncio.set_nombre(cursor.getString(i_nombre));

		// TODO ...
		// procesar el resto de campos

		return anuncio;
	}




}
