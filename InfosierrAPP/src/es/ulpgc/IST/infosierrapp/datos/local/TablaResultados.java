package es.ulpgc.IST.infosierrapp.datos.local;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class TablaResultados implements BaseColumns{
	
	// Etiqueta para logs
	public static final String LOG_TAG="TablaResultados";
	
	// Nombre de la tabla
	public static final String	TABLE_NAME = "resultados";	
	// Columnas de la tabla
	public static final String 	COL_ID =		BaseColumns._ID;	
	public static final String	COL_NOMBRE =	"nombre";
	public static final String	COL_DIRECCION =	"direccion";
	public static final String	COL_TELEFONOS =	"telefonos";
	public static final String	COL_EMAIL = 	"email";
	public static final String	COL_WEB = 		"web";
	public static final String	COL_DESC =		"descripcion";
	public static final String	COL_TAGS =		"tags";
	public static final String	COL_FOTO =		"foto";
	public static final String	COL_MAPX =		"mapX";
	public static final String	COL_MAPY =		"mapY";
	
	public static final String[] ALL_COLUMNS = {COL_ID, COL_NOMBRE, COL_DIRECCION,
												COL_TELEFONOS, COL_EMAIL, COL_WEB,
												COL_DESC, COL_TAGS, COL_FOTO,
												COL_MAPX, COL_MAPY}; 
	
	// TODO ¿¿usar tipo BLOB para las fotos y 
	// guardar la img directamente en lugar de la url??
    
	
	// Forma la cadena SQL para creación de la tabla en la db
	private static final String SQL_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "( " 
			+ COL_ID 		+ " INTEGER PRIMARY KEY, "
			+ COL_NOMBRE	+ " TEXT NOT NULL, "
			+ COL_DIRECCION	+ " TEXT, "
			+ COL_TELEFONOS	+ " TEXT, "
			+ COL_EMAIL		+ " TEXT, "
			+ COL_WEB		+ " TEXT, "
			+ COL_DESC		+ " TEXT, "
			+ COL_TAGS		+ " TEXT NOT NULL, "			
			+ COL_FOTO		+ " TEXT, "
			+ COL_MAPX		+ " REAL, "
			+ COL_MAPY		+ " REAL"
			+ " );";
	// Forma la cadena SQL que se ejecutará en caso de actualización de la db	
	private static final String SQL_UPGRADE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	
	/**
	 *  Ese método será llamado desde el onCreate del helper para la creación
	 *  de la db.
	 * @param database
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE);
	}
	
	/**
	 *  Ese método será llamado desde el onUpgrade del helper para
	 *  la actualización de la db. Borra la antigua 
	 * (OJO: se pierden los datos) y se crea la versión nueva vacía.
	 * 
	 * @param database
	 * @param oldVersion
	 * @param newVersion
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(LOG_TAG, "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL(SQL_UPGRADE);
		onCreate(database);
	}

}
