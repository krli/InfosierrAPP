package es.ulpgc.IST.infosierrapp.maestrodetalle;


import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;
import es.ulpgc.IST.infosierrapp.main.MenuActivity;



public class ListPresenter extends MenuActivity {

	private BuscadorDatos buscadorDatos;

	private TextView mTextView;
	private ListView mListView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.vista_v_maestro);

		Toast.makeText(getApplicationContext(), 
				"Creando Maestro...<", Toast.LENGTH_SHORT).show();		

		buscadorDatos = BuscadorDatos.getBuscador(getApplicationContext());

		mTextView = (TextView) findViewById(R.id.text);
		mListView = (ListView)findViewById(R.id.list);

		List<Anuncio> lista = buscadorDatos.get_resultados_lista();		



		ArrayAdapter adapter = new ArrayAdapter<Anuncio>(this,android.R.layout.simple_list_item_1,lista);

		// Asocia el adaptador con la vista
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

}