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
	protected Intent getPresenter() {
		Intent intent = new Intent(PresentadorH_main.this,
				PresentadorV_main.class);
		return intent;
	}

}
