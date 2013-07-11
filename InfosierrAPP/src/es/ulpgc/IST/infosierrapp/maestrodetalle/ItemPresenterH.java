package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.ulpgc.IST.infosierrapp.R;

/**
 * Presentador asignado a cada anuncio
 * Muestra la disposicion de la informacion
 *
 */
public class ItemPresenterH extends ItemPresenterV  {
	private GoogleMap 		mMap;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LatLng latlng = new LatLng(anuncio.get_X(), anuncio.get_Y());
		
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		
		mMap.addMarker(new MarkerOptions().position(latlng).title(anuncio.get_nombre()));
		// Mueve la camara a la posicion
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
		// Anima la camara.
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);	
		
	}

	
    protected void checkOrientation() {      
      Configuration config = getResources().getConfiguration();
      if (config.orientation != Configuration.ORIENTATION_LANDSCAPE) {
      	changePresenter();
      } 
    }

    protected Intent getIntentForChangePresenter() {
    	// Destino: Presentador V
    	Intent intent = new Intent(ItemPresenterH.this,
    			ItemPresenterV.class);
		// Guarda en el intent el anuncio 
		intent.putExtra("anuncio", anuncio);
    	return intent;
    }

    protected void loadView() {
        setContentView(R.layout.detalle_vista_h);		
    }
    
    /* ************************************************* */
	

}
