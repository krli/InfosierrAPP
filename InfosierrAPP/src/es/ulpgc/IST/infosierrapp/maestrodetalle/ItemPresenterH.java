package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.content.Intent;
import android.content.res.Configuration;
import es.ulpgc.IST.infosierrapp.R;

/**
 * Presentador asignado a cada anuncio
 * Muestra la disposicion de la informacion
 *
 */
public class ItemPresenterH extends ItemPresenterV {
	

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
