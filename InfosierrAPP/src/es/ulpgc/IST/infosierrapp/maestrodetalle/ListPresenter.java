
package es.ulpgc.IST.infosierrapp.maestrodetalle;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;
import es.ulpgc.IST.infosierrapp.datos.local.TablaResultados;
import es.ulpgc.IST.infosierrapp.main.MenuActivity;


/**
 * Clase encargada de implementar el Maestro
 *
 */
public class ListPresenter extends MenuActivity {

	private Cursor cursor=null;
	private SimpleCursorAdapter cursorAdapter;
	private BuscadorDatos buscadorDatos;
	private TextView mTextView;
	private ListView mListView;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.vista_maestro);



		buscadorDatos = BuscadorDatos.getBuscador(getApplicationContext());

		mTextView = (TextView) findViewById(R.id.text);
		mListView = (ListView)findViewById(R.id.list);



		// Obtiene el cursor que BuscadorDatos ha obtenido con los resultados
		// Cursor cursor = buscadorDatos.get_resultados_cursor();
		
		String query = buscadorDatos.get_cadena_busqueda();
		Toast.makeText(getApplicationContext(),
				"Buscando: "+query, Toast.LENGTH_SHORT).show();
		
		Cursor cursor = buscadorDatos.buscarEnLocal(query);
		
		startManagingCursor(cursor);




		if (cursor == null) {
			// No hay resultados. "No se encontraron resultados para: <cadena>"
			 mTextView.setText(getString(R.string.no_results, new Object[] {buscadorDatos.get_cadena_busqueda()}));
			
		} else {

			// Muestra el numero de resultados
			int count = cursor.getCount();
			String countString = getResources().getQuantityString(R.plurals.search_results,
					count, new Object[] {count, buscadorDatos.get_cadena_busqueda()});
			mTextView.setText(countString);


			// Obtiene el CursorAdapter definido y creado por BuscadorDatos
			cursorAdapter=getCursorAdapter(cursor);

			// Asocia el adaptador con la vista
			mListView.setAdapter(cursorAdapter);


			////////////////////////////
			//SI USAMOS LOADER MANAGER//
			////////////////////////////
			
//			// A traves de Callbacks interactuamos con el LoaderManager.
//			// El LoaderManager usa este objeto para instanciar el Loader y notificar al cliente
//			mCallbacks = this;
//
//
//			// Inicializa el Loader con id 1 y callbacks 'mCallbacks'
//			// Si el Loader no existe, es creado
//			LoaderManager lm = getLoaderManager();
//			lm.initLoader(LOADER_ID, null, mCallbacks);


			// Define el clic en la lista de items
			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int pos, long id){

					// Aparece el nombre almacenado.
					//					String nombre = 
					//							cursor.getString(cursor.getColumnIndexOrThrow(TablaResultados.COL_NOMBRE));
					//					Toast.makeText(getApplicationContext(),
					//							nombre, Toast.LENGTH_SHORT).show();


					Intent myIntent = new Intent(ListPresenter.this, ItemPresenterV.class);

					// Obtiene el CursorAdapter definido y creado por BuscadorDatos
					Cursor cursor = (Cursor) cursorAdapter.getItem(pos);
					startActivity(myIntent, cursor);
				}
			});
		}
	}

	/**
	 * Pasa los valores de los campos obtenidos por el cursor al ItemPresenter
	 * @param myIntent
	 * @param cursor
	 */
	public void startActivity(Intent myIntent, Cursor cursor) {
		
		Anuncio anuncio = Anuncio.cursorToAnuncio(cursor);		
		myIntent.putExtra("anuncio", anuncio);
		startActivity(myIntent);

	}

	@Override
	public void finish(){
		if (cursor != null) {
			cursor.close();
		}
		super.finish();
	}
	
	/**
	 * TODO: Esto parece más propio de la configuración del 
	 * layout del maestro-detalle ¿¿ Sacar de aquí y meter 
	 * directamente allí ??
	 * 
	 * @param context
	 * @return
	 */
	private SimpleCursorAdapter getCursorAdapter(Cursor cursor){

		// Especifica las columnas que se mostraran en el resultado
		String[] from = new String[] { TablaResultados.COL_NOMBRE, TablaResultados.COL_TAGS, 
				TablaResultados.COL_DESC, TablaResultados.COL_DIRECCION,
				TablaResultados.COL_EMAIL, TablaResultados.COL_TELEFONOS, 
				TablaResultados.COL_WEB, TablaResultados.COL_MAPX, 
				TablaResultados.COL_MAPY, TablaResultados.COL_FOTO};

		// Especifica los correspondintes elementos del layout 
		int[] to = new int[] { R.id.txtNombre, R.id.txtEtiquetas, R.id.txtDescripcion, 
				R.id.txtDireccion, R.id.txtEmail, R.id.txtTelefono, 
				R.id.txtWeb, R.id.txtX, R.id.txtY, R.id.image};

		//crea el cursor adapter para las definiciones dadas y lo aplica a la ListView
		//Inicializa el adaptador.
		//El 3 argumento es null porque el cursor aun no ha sido cargado por primera vez
		//El ultimo argumento es 0 para prevenir el registro del Observer (CursorLoader lo hace directamente)
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.vista_item,
				cursor, from, to, 0);

		// Devuelve el CursorAdapter a ListPresenter
		return cursorAdapter;

	}


	//SI USAMOS LOADER MANAGER//

//	//Implementamos Callbacks
//
//	/**
//	 * Aqui es donde el cursor es creado
//	 */
//	@Override
//	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//		Toast.makeText(this, "onCreateLoader", Toast.LENGTH_SHORT).show();
//
//		//String[] projection = { TablaResultados.COL_NOMBRE };
//		//				CursorLoader cursorLoader = new CursorLoader(this, 
//		//						ListPresenter., projection, null, null, null);
//		//	return cursorLoader;
//
//		return null;
//
//	}
//
//	/**
//	 * Una vez que se ha creado el cursor aqui puede empezar a ser usado
//	 */
//	@Override
//	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
//		Toast.makeText(this, "onLoadFinished", Toast.LENGTH_SHORT).show();
//
//		cursorAdapter.swapCursor(cursor);
//
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> arg0) {
//		Toast.makeText(this, "onLoaderReset", Toast.LENGTH_SHORT).show();
//
//		cursorAdapter.swapCursor(null);
//
//
//	}

}