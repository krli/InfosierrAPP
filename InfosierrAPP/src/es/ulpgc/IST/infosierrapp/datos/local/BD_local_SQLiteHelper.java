package es.ulpgc.IST.infosierrapp.datos.local;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Clase responsable de la creación de la BD local en SQLite. 
 * Proporciona los métodos getReadableDatabase() y getWriteableDatabase()
 * para acceder a la BD.
 * Se implementa mediante un Singleton para evitar múltiples helpers y 
 * los problemas que esto puede provocar.
 * 
 * @author krlo
 *
 */
public class BD_local_SQLiteHelper extends SQLiteOpenHelper {

	/** Nombre del fichero database */ 
	private static final String 	DATABASE_NAME = "infosierraResults.db";
	/** Versión de la DB */
	private static final int		DATABASE_VERSION = 1;
		
	/** Objeto singleton */
	private static BD_local_SQLiteHelper instance = null;
	
	/**
	 * Constructores privados (Singleton)
	 * @param context
	 */
	private BD_local_SQLiteHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}
	private BD_local_SQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	private BD_local_SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}		

	/** 
	 * Devuelve el helper ("constructor singleton")
	 * @param context contexto de la APP
	 * @return objeto único BD_local_SQLiteHelper
	 */
	public static BD_local_SQLiteHelper getHelper(Context context) {
		if(instance == null){
			instance = new BD_local_SQLiteHelper(context);
		}
		return instance;		
	}


	/**
	 * Se ejecuta la primera vez que se accede a la DB y crea
	 * la tabla.
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Tabla resultados
		TablaResultados.onCreate(database);
	}

	/**
	 * Se ejecuta si se intenta acceder a la BD con una versión más
	 * nueva que la que existe.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Tabla resultados
		TablaResultados.onUpgrade(db, oldVersion, newVersion);
	}

}
