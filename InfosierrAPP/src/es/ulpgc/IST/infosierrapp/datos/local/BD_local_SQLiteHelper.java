package es.ulpgc.IST.infosierrapp.datos.local;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


/**
 * Clase responsable de la creación de la BD local en SQLite.
 * Define su estructura: columnas, constantes... y la crea. 
 * Proporciona los métodos getReadableDatabase() y getWriteableDatabase()
 * para acceder a la BD.
 * Se implementa mediante un Singleton para evitar múltiples helpers y 
 * los problemas que esto puede provocar.
 * 
 * @author krlo
 *
 */
public class BD_local_SQLiteHelper extends SQLiteOpenHelper implements BaseColumns {

	// Nombre y versión de la BD
	private static final String 	DATABASE_NAME = "infosierraResults.db";
	private static final int		DATABASE_VERSION = 1;
	
	// Nombre de la tabla (sólo tendremos una, si hubiese más
	// sería recomendable definirlas en clases separadas)
	public static final String	TABLE_RESULTADOS = "resultados";	
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
        		
	// Forma la cadena SQL para creación de la tabla en la db
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_RESULTADOS + "( " 
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

	// TODO ¿¿usar tipo BLOB para las fotos y 
	// guardar la img directamente en lugar de la url??
		
	/* Objeto singleton */
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
	 * @param context
	 * @return 
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
		database.execSQL(DATABASE_CREATE);
	}

	/**
	 * Se ejecuta si se intenta acceder a la BD con una versión más
	 * nueva que la que existe. Si esto sucede se borra la antigua 
	 * (OJO: se pierden los datos) y se crea la versión nueva vacía.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(BD_local_SQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTADOS);
		onCreate(db);
	}

}
