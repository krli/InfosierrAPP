package es.ulpgc.IST.infosierrapp.maestrodetalle;
//
//import es.ulpgc.IST.infosierrapp.R;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
///**
// * Presenta los items obtenidos en la busqueda
// * @author jesus
// *
// */
////TODO no crear un ListModel
//public class ListPresenter extends Activity{
//	private static final int REQUEST_CODE = 0;
//	private ListView list;
//	private ListModel model;
//	
//	@Override
//	public void onCreate (Bundle savedInstanceState) {
//		super.onCreate (savedInstanceState);
//		setContentView(R.layout.vista_v_maestro);
//		
//		list = (ListView) findViewById (R.id.listView);
//		model = new ListModel();
//		
//		ListAdapter adapter = new ListAdapter (this, model.getData());
//		list.setAdapter(adapter);
//		
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> a, View v, int pos, long id){
//                startActivity((ItemModel) a.getAdapter().getItem(pos));
//            }
//        });
//	}
//	
//    private void startActivity(ItemModel itemModel) {
//        Intent intent = new Intent(ListPresenter.this, ItemPresenter.class);
//        intent.putExtra(Intent.ACTION_EDIT, itemModel);
//        startActivityForResult(intent, REQUEST_CODE);
//    }
//    
//    @Override
//    protected void onActivityResult(int reqCode, int resCode, Intent intent) {
//        if (resCode == RESULT_OK && reqCode == REQUEST_CODE) {
//
//            if (intent.hasExtra(Intent.ACTION_DEFAULT)) {
//
//            }
//            if (intent.hasExtra(Intent.ACTION_EDIT)) {
//                ItemModel itemModel = (ItemModel)intent.getSerializableExtra(
//                        Intent.ACTION_EDIT);
//                model.setData(Integer.parseInt(itemModel.getPos()), itemModel);
//            }
//            if (intent.hasExtra(Intent.ACTION_DELETE)) {
//                ItemModel itemModel = (ItemModel)intent.getSerializableExtra(
//                        Intent.ACTION_DELETE);
//                model.delData(Integer.parseInt(itemModel.getPos()));
//
//                ListAdapter adapter = new ListAdapter(this, model.getData());
//                list.setAdapter(adapter);
//            }
//        }
//    }
//    

//
//}

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.BD_infosierra;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;

public class ListPresenter extends MenuListActivity {
	private Cursor cursor;
	private SimpleCursorAdapter cursorAdapter;

	private TextView mTextView;
	private ListView mListView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_v_maestro);

		mTextView = (TextView) findViewById(R.id.text);
		mListView = (ListView) findViewById(R.id.listView);


		mostrar();

		//dbAdapter = new BD_resultadosAdapter(this);
		//dbAdapter.abrir();
		//consultar();
	}

	private void mostrar(){
		cursor=BuscadorDatos.getCursor();
		if (cursor == null) {
			// There are no results
			mTextView.setText(getString(R.string.no_results, new Object[] {BuscadorDatos.getCadena()}));
		} else {
			// Display the number of results
			int count = cursor.getCount();
			String countString = getResources().getQuantityString(R.plurals.search_results,
					count, new Object[] {count, BuscadorDatos.getCadena()});
			mTextView.setText(countString);

			// Specify the columns we want to display in the result

			String[] from = new String[] { BD_infosierra.KEY_NOMBRE,
					BD_infosierra.KEY_ETIQUETAS };


			// Specify the corresponding layout elements where we want the columns to go
			int[] to = new int[] { R.id.txtNombre,
					//R.id.txtEtiqueta 
			};

			//  


			BuscadorDatos.initAdapter(this, from, to);


			mListView.setAdapter(cursorAdapter);

			// Define the on-click listener for the list items
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// Build the Intent used to open WordActivity with a specific word Uri


					Intent wordIntent = new Intent(getApplicationContext(), ItemPresenter.class);

					//
					Uri data = Uri.withAppendedPath(BD_infosierra.CONTENT_URI,
							String.valueOf(id));


					wordIntent.setData(data);


					startActivity(wordIntent);
				}
			});
		}
	}


	//	private void consultar(){
	//		cursor = dbAdapter.getCursor();
	//		startManagingCursor(cursor);
	//		resultadosAdapter = new BD_resultadosCursorAdapter(this, cursor);
	//		lista.setAdapter(resultadosAdapter);
	//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.master_menu, menu);
		return true;
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
}
