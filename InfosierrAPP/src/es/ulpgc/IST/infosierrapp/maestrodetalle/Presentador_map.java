package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.main.MenuActivity;

public class Presentador_map extends MenuActivity implements OnClickListener {


	/*
	 * Referencias a componentes del layout;
	 */
	private Double			X,Y;
	private String			nombre;
	private GoogleMap 		mMap;


	/**
	 * Inicio de la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		loadView();
		
		setUpMapIfNeeded();

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
			mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
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
		
		Intent intent = getIntent();
		nombre = intent.getExtras().getString("nombre");
		X = intent.getExtras().getDouble("X",0);
		Y = intent.getExtras().getDouble("Y",0);
		
		
		LatLng posicion = new LatLng(X, Y);
		mMap.addMarker(new MarkerOptions().position(posicion).title(this.nombre));
		// Mueve la camara a la posicion
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 15));
		// Anima la camara.
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	
	}

	/**
	 * Carga el layout.
	 */
	protected void loadView() {
		setContentView(R.layout.map);
	}



	/**
	 * Reacciona a los eventos de click
	 */
	@Override
	public void onClick(View view) {
		int btn = view.getId();

	}

}
