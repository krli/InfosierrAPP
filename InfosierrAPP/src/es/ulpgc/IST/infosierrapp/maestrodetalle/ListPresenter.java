
package es.ulpgc.IST.infosierrapp.maestrodetalle;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;
import es.ulpgc.IST.infosierrapp.datos.local.BD_local_Acceso;
import es.ulpgc.IST.infosierrapp.datos.local.TablaResultados;
import es.ulpgc.IST.infosierrapp.main.MenuActivity;

//extends MenuListActivity 

public class ListPresenter extends MenuActivity implements LoaderManager.LoaderCallbacks<Cursor>{
	private static Uri CONTENT_URI= Uri.parse("content://es.ulpgc.IST.infosierrapp/infosierraResults.db");

	private static final int REQUEST_CODE = 0;

	private Cursor cursor=null;
	private SimpleCursorAdapter cursorAdapter;


	private BuscadorDatos buscadorDatos;
	private BD_local_Acceso db_conn;

	private static final int LOADER_ID = 1;

	private TextView mTextView;
	private ListView mListView;
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.vista_v_maestro);

		Toast.makeText(getApplicationContext(), 
				"Creando Maestro...<", Toast.LENGTH_SHORT).show();		


		////////////////////////		
		//USANDO ARRAY ADAPTER//
		////////////////////////

		//ListView mlistView = (ListView) findViewById(R.id.list);
		/*ListView mlistView = getListView();

				BDlocal=new BD_local_Acceso(this);
				List <Anuncio> anuncios = BDlocal.getResultados();

				ArrayAdapter<Anuncio> adapter = new ArrayAdapter <Anuncio> (this,es.ulpgc.IST.infosierrapp.R.layout.vista_v_item,anuncios);
				setListAdapter(adapter);


				mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> a, View v, int pos, long id){
						startActivity((Anuncio) a.getAdapter().getItem(pos));
					}
				});*/


		buscadorDatos = BuscadorDatos.getBuscador(getApplicationContext());
		db_conn = new BD_local_Acceso(getApplicationContext());

		mTextView = (TextView) findViewById(R.id.text);
		//ListView listView = (ListView) findViewById(R.id.list);
		// mListView = getListView();
		mListView = (ListView)findViewById(R.id.list);



		// Obtiene el cursor que BuscadorDatos ha obtenido con los resultados
		Toast.makeText(getApplicationContext(), 
				"Obteniendo cursor...<", Toast.LENGTH_SHORT).show();
		Cursor cursor = buscadorDatos.get_resultados_cursor();
		startManagingCursor(cursor);




		if (cursor == null) {
			// No hay resultados. "No se encontraron resultados para: <cadena>"

			// mTextView.setText(getString(R.string.no_results, new Object[] {buscadorDatos.get_cadena_busqueda()}));
			// mTextView.setText("cadena de búsqueda: " + buscadorDatos.get_cadena_busqueda());
			mTextView.setText("cadena inofensiva");

		} else {

			// Muestra el numero de resultados
			int count = cursor.getCount();
			String countString = getResources().getQuantityString(R.plurals.search_results,
					count, new Object[] {count, buscadorDatos.get_cadena_busqueda()});
			mTextView.setText(countString);


			// Obtiene el CursorAdapter definido y creado por BuscadorDatos
			cursorAdapter=buscadorDatos.getCursorAdapter(this);

			// Asocia el adaptador con la vista
			mListView.setAdapter(cursorAdapter);



			// A traves de Callbacks interactuamos con el LoaderManager.
			// El LoaderManager usa este objeto para instanciar el Loader y notificar al cliente
			mCallbacks = this;



			// Inicializa el Loader con id 1 y callbacks 'mCallbacks'
			// Si el Loader no existe, es creado
			LoaderManager lm = getLoaderManager();
			lm.initLoader(LOADER_ID, null, mCallbacks);


			// Define el clic en la lista de items

			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int pos, long id){
					//startActivity( (Anuncio) a.getAdapter().getItem(pos));
					// Obtiene el cursor posicionado en la fila del resultado
					//Cursor cursor = (Cursor) mListView.getItemAtPosition(pos);

					// Aparece el nombre almacenado.
					//					String nombre = 
					//							cursor.getString(cursor.getColumnIndexOrThrow(TablaResultados.COL_NOMBRE));
					//					Toast.makeText(getApplicationContext(),
					//							nombre, Toast.LENGTH_SHORT).show();


					Intent myIntent = new Intent(ListPresenter.this, ItemPresenter.class);

					// Obtiene el CursorAdapter definido y creado por BuscadorDatos
					Cursor cursor = (Cursor) cursorAdapter.getItem(pos);
					startActivity(myIntent, cursor);


				}
			});


		}


	}

	public void startActivity(Intent myIntent, Cursor cursor) {

		myIntent.putExtra("nombre", cursor.getString(cursor.getColumnIndex(TablaResultados.COL_NOMBRE)));
		myIntent.putExtra("descripcion", cursor.getString(cursor.getColumnIndex(TablaResultados.COL_DESC)));
		myIntent.putExtra("direccion", cursor.getString(cursor.getColumnIndex(TablaResultados.COL_DIRECCION)));
		myIntent.putExtra("email", cursor.getString(cursor.getColumnIndex(TablaResultados.COL_EMAIL)));
		myIntent.putExtra("mapx", cursor.getDouble(cursor.getColumnIndex(TablaResultados.COL_MAPX)));
		myIntent.putExtra("mapy", cursor.getDouble(cursor.getColumnIndex(TablaResultados.COL_MAPY)));
		myIntent.putExtra("telefono", cursor.getString(cursor.getColumnIndex(TablaResultados.COL_TELEFONOS)));
		myIntent.putExtra("web", cursor.getString(cursor.getColumnIndex(TablaResultados.COL_WEB)));


		startActivity(myIntent);

	}

	/*@Override
	protected void onActivityResult(int reqCode, int resCode, Intent intent) {
		if (resCode == RESULT_OK && reqCode == REQUEST_CODE) {

			if (intent.hasExtra(Intent.ACTION_DEFAULT)) {

			}
			if (intent.hasExtra(Intent.ACTION_EDIT)) {
				Anuncio anuncio = (Anuncio)intent.getSerializableExtra(
						Intent.ACTION_EDIT);
				//model.setData(Integer.parseInt(anuncio.getPos()), anuncio);
				db_conn.insertAnuncio(anuncio);			
			}
		}
	}*/



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.master_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//		case R.id.menu_new:
		//			startActivity(new ItemModel());
		//			return true;
		case R.id.menu_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void finish(){
		if (cursor != null) {
			cursor.close();
		}
		super.finish();
	}


	//LOADER MANAGER//

	//Implementamos Callbacks

	/**
	 * Aqui es donde el cursor es creado
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Toast.makeText(this, "onCreateLoader", Toast.LENGTH_SHORT).show();

		//String[] projection = { TablaResultados.COL_NOMBRE };
		//				CursorLoader cursorLoader = new CursorLoader(this, 
		//						ListPresenter., projection, null, null, null);
		//	return cursorLoader;

		return null;

	}

	/**
	 * Una vez que se ha creado el cursor aqui puede empezar a ser usado
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		Toast.makeText(this, "onLoadFinished", Toast.LENGTH_SHORT).show();

		cursorAdapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		Toast.makeText(this, "onLoaderReset", Toast.LENGTH_SHORT).show();

		cursorAdapter.swapCursor(null);


	}

}