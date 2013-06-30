/**
 * 
 */
package es.ulpgc.IST.infosierrapp.main;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.datos.BD_resultados;

/**
 * 
 * Clase que se cargar�� siempre la primera cuando se inicie la
 * App.
 * 
 * Realiza todas las comprobaciones de inicio que sean necesarias
 * ej: si hay conexi��n a internet, si es datos o wifi ...
 * 
 * @author krlo
 *
 */
public class Inicio {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * Declaramos el controlador de la BBDD y accedemos en modo escritura
		 */
		BD_resultados dbResultados = new BD_resultados(getBaseContext());

		SQLiteDatabase db = dbResultados.getWritableDatabase();

		Toast.makeText(getBaseContext(), "Base de datos local preparada", Toast.LENGTH_LONG).show()
	}
}
