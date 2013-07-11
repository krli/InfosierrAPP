package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.main.MenuActivity;

/**
 * Clase responsable de presentar el mapa
 *
 */
public class Presentador_map extends MenuActivity  {


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
		setContentView(R.layout.map);
		
		Intent intent = getIntent();
		this.nombre = intent.getStringExtra("nombre");
		this.X = intent.getDoubleExtra("X",0);
		this.Y = intent.getDoubleExtra("Y",0);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		
		
		
		mMap.addMarker(new MarkerOptions().position(new LatLng(X,Y)).title(this.nombre));
		// Mueve la camara a la posicion
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(X,Y), 15));
		// Anima la camara.
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	

	}




}
