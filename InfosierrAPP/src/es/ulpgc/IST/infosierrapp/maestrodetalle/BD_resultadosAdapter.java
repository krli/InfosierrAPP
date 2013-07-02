package es.ulpgc.IST.infosierrapp.maestrodetalle;

import es.ulpgc.IST.infosierrapp.datos.BD_resultados;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BD_resultadosAdapter {

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
	private BD_resultados dbHelper;
	private SQLiteDatabase db;

	/**
    * Definimos lista de columnas de la tabla para utilizarla en las consultas a la base de datos
    */
	private String[] columnas = new String[]{ C_COLUMNA_ID, C_COLUMNA_NOMBRE, C_COLUMNA_ETIQUETAS, C_COLUMNA_DIRECCION, C_COLUMNA_EMAIL, C_COLUMNA_TELEFONO, C_COLUMNA_WEB, C_COLUMNA_DESCRIPCION} ;

	public BD_resultadosAdapter(Context context){
		this.contexto=context;
		}


	public BD_resultadosAdapter abrir() throws SQLException{
		dbHelper = new BD_resultados(contexto);
		db = dbHelper.getWritableDatabase();
		return this;
		}

	public void cerrar(){
		dbHelper.close();
		}


	/**
    * Devuelve cursor con todos las columnas de la tabla
    */
	public Cursor getCursor() throws SQLException{
		Cursor c = db.query( true, C_TABLA, columnas, null, null, null, null, null, null);
		return c;
		}

}
