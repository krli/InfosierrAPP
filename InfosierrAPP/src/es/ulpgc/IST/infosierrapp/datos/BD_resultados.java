package es.ulpgc.IST.infosierrapp.datos;
/**
 * Clase para conectar con la base de datos de 
 * almacenamiento local de resultados.
 * @author krlo
 *
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BD_resultados extends SQLiteOpenHelper {

	private static int version = 1;
	private static String name = "ResultadosDb" ;
	private static CursorFactory factory = null;
	private static Anuncio[] resultados;
	private int contador=0;

	public BD_resultados(Context context)
	{
		super(context, name, factory, version);
	}

	/**
	 * Se crea la base de datos la primera vez que se accede
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{

		Log.i(this.getClass().toString(), "Creando base de datos");

		db.execSQL( "CREATE TABLE ANUNCIOS(" +
				" _id INTEGER PRIMARY KEY," +
				" anuncio_nombre TEXT NOT NULL, " +
				" anuncio_etiquetas TEXT, " +
				" anuncio_direccion TEXT," +
				" anuncio_telefono TEXT," +
				" anuncio_email TEXT," +
				" anuncio_web TEXT," +
				" anuncio_descripcion TEXT)" );

		db.execSQL( "CREATE UNIQUE INDEX hip_nombre ON HIPOTECA(anuncio_nombre ASC)" );

		Log.i(this.getClass().toString(), "Tabla ANUNCIOS creada");

		/*
		 * Insertamos datos iniciales
		 */
		//Sustituir por BuscadorDatos.getResultados()
		//resultados = BuscadorDatos.getResultados();
		
		do{
		//COMO PODRIA LLAMAR A METODO?
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_nombre) VALUES(1,resultados[contador].getNombre())");
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_etiquetas) VALUES(2,'Restaurantes, cafeterias')");
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_direccion) VALUES(3,'Calle 123')");
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_telefono) VALUES(4,'928000000')");
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_email) VALUES(5,'barpepe@hotmail.com')");
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_web) VALUES(6,'www.barpepe.com')");
		db.execSQL("INSERT INTO ANUNCIO(_id, anuncio_descripcion) VALUES(7,'blablabla')");

		contador++;
		} while (resultados[contador]!=null);
		Log.i(this.getClass().toString(), "Datos iniciales ANUNCIOS insertados");

		Log.i(this.getClass().toString(), "Base de datos creada");

	}

	/**
	 * Se actualiza cuando se detecta un cambio en la version de la base de datos
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}
}

