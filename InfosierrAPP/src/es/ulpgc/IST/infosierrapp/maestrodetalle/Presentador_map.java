package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

import es.ulpgc.IST.infosierrapp.R;

/**
 * Clase responsable de presentar el mapa
 *
 */
public class Presentador_map extends android.support.v4.app.FragmentActivity  {


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
		
		/*Intent intent = getIntent();
		nombre = intent.getExtras().getString("nombre");
		X = intent.getExtras().getDouble("X",0);
		Y = intent.getExtras().getDouble("Y",0);*/

		//mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			//	.getMap();
		/*
		LatLng posicion = new LatLng(X, Y);
		mMap.addMarker(new MarkerOptions().position(posicion).title(this.nombre));
		// Mueve la camara a la posicion
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 15));
		// Anima la camara.
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	
*/
	}




}
