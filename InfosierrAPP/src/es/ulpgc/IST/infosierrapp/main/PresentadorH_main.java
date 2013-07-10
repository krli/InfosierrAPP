package es.ulpgc.IST.infosierrapp.main;


import android.content.Intent;
import android.content.res.Configuration;
import es.ulpgc.IST.infosierrapp.R;

/**
 * Presentador para la versión horizontal de la actividad main.
 * El único cambio es el layout.
 */
public class PresentadorH_main extends PresentadorV_main {
	
	@Override
	protected void checkOrientation() {	    
		Configuration config = getResources().getConfiguration();
		if (config.orientation != Configuration.ORIENTATION_LANDSCAPE) {
			changePresenter();
		} 
	}

	@Override
	protected void loadView() {
		setContentView(R.layout.main_vista_h);
	}

	@Override
	protected Intent getIntentForChangePresenter() {
    	// Destino: Presentador V
		Intent intent = new Intent(PresentadorH_main.this,
				PresentadorV_main.class);
		intent.setAction(INTENT_ACTION);
		// Guarda en el intent el contenido de la searchview
		intent.putExtra(INTENT_CONTENT_WISEARCH, wi_search.getQuery());
    	
		return intent;
	}

}
