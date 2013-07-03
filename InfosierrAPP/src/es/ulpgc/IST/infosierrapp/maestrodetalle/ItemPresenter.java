package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.datos.BD_infosierra;

/**
 * Presentador asignado a cada anuncio
 * Muestra la disposicion de la informacion
 * @author jesus
 *
 */
public class ItemPresenter extends FragmentActivity  {

	//	private EditText nombre;
	//	private EditText direccion;
	//	private EditText telefono;
	//	private EditText email;
	//	private EditText descripcion;
	//	private EditText web;
	//	private TextView pos;

	private Anuncio anuncio;
	private String action;

	private GoogleMap mMap;
	//coordenadas
	private int X;
	private int Y;



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_v_detalle);
		setUpMapIfNeeded();

		//        pos = (TextView)findViewById(R.id.lblPos);
		//        nombre = (EditText)findViewById(R.id.txtNombre);
		//        direccion = (EditText)findViewById(R.id.txtDireccion);
		//        telefono = (EditText)findViewById(R.id.txtTelefono);
		//        email = (EditText)findViewById(R.id.txtEmail);
		//        web = (EditText)findViewById(R.id.txtWeb);
		//        descripcion = (EditText)findViewById(R.id.txtDescripcion);
		//       
		//
		//
		//
		//        Intent intent = getIntent();
		//        anuncio = (Anuncio)intent.getSerializableExtra(Intent.ACTION_EDIT);
		//
		//        pos.setText(anuncio.getPos());
		//
		//        nombre.setText(anuncio.getNombre());
		//        direccion.setText(anuncio.getDireccion());
		//        telefono.setText(anuncio.getTelefono());
		//        email.setText(anuncio.getEmail());
		//        web.setText(anuncio.getWeb());
		//        descripcion.setText(anuncio.getDescripcion());
		//        X=(anuncio.getX());
		//        Y=(anuncio.getY());

		Uri uri = getIntent().getData();
		Cursor cursor = managedQuery(uri, null, null, null, null);
		//Cursor cursor = CursorLoader(this, uri, null, null, null, null);

		if (cursor == null) {
			finish();
		} else {
			cursor.moveToFirst();

			//TODO: meter imagenes
			TextView nombre = (TextView) findViewById(R.id.txtNombre);
			// TextView etiquetas = (TextView) findViewById(R.id.txtEtiquetas);
			TextView direccion = (TextView) findViewById(R.id.txtDireccion);
			TextView telefono = (TextView) findViewById(R.id.txtTelefono);
			TextView email = (TextView) findViewById(R.id.txtEmail);
			TextView web = (TextView) findViewById(R.id.txtWeb);
			TextView descripcion = (TextView) findViewById(R.id.txtDescripcion);
			//            TextView posX = (TextView) findViewById(R.id.posX);
			//            TextView posY = (TextView) findViewById(R.id.posY);


			int aIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_NOMBRE);
			// int bIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_ETIQUETAS);
			int cIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_DIRECCION);
			int dIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_TELEFONO);
			int eIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_CORREO);
			int fIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_WEB);
			int gIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_DESCRIPCION);
			X = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_POSX);
			Y = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_POSY);


			nombre.setText(cursor.getString(aIndex));
			// etiquetas.setText(cursor.getString(bIndex));
			direccion.setText(cursor.getString(cIndex));
			telefono.setText(cursor.getString(dIndex));
			email.setText(cursor.getString(eIndex));
			web.setText(cursor.getString(fIndex));
			descripcion.setText(cursor.getString(gIndex));
			//            posX.setText(cursor.getString(hIndex));
			//            posY.setText(cursor.getString(iIndex));


		}


	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	/**
	 * Intenta abrir el mapa
	 */
	private void setUpMapIfNeeded() {
		// Si el nMap esta null entonces es porque no se instancio el mapa.
		if (mMap == null) {
			// Intenta obtener el mapa del SupportMapFragment. 
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			// Comprueba si hemos tenido exito en la obtencion del mapa.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * Coloca marcador en la posicion del anuncio
	 */

	private void setUpMap() {
		mMap.addMarker(new MarkerOptions().position(new LatLng(X, Y)).title("Marker"));
	}

	@Override
	public void finish() {
		Intent intent = new Intent();
		//intent.putExtra(action, anuncio);
		setResult(RESULT_OK, intent);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail_menu, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//            case R.id.menu_save:
		//                model.setNombre(nombre.getText().toString());
		//                model.setDireccion(direccion.getText().toString());
		//                model.setTelefono(telefono.getText().toString());
		//                model.setEmail(email.getText().toString());
		//                model.setDescripcion(descripcion.getText().toString());
		//                action = Intent.ACTION_EDIT;
		//                finish();
		//                return true;
		case R.id.menu_llamar:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(anuncio.getTelefono()));
			startActivity(intent);
			finish();
			return true;
			//            case R.id.menu_delete:
			//                action = Intent.ACTION_DELETE;
			//                finish();
			//                return true;
		case R.id.menu_compartir:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			return true;

		case R.id.menu_email:
			Intent emailintent = new Intent(Intent.ACTION_SEND);
			emailintent.setType("plain/text");
			emailintent.putExtra(Intent.EXTRA_EMAIL,new String[] { "address@example.com" });
			emailintent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the mail");
			emailintent.putExtra(Intent.EXTRA_TEXT, "body of the mail");
			startActivity(Intent.createChooser(emailintent, "Title of the chooser dialog"));
			return true;

		case R.id.menu_back:
			action = Intent.ACTION_DEFAULT;
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}