/**
 * 
 */
package es.ulpgc.IST.infosierrapp.main;


import es.ulpgc.IST.infosierrapp.R;
import android.content.Intent;
import android.content.res.Configuration;

/**
 * @author krlo
 *
 */
public class PresentadorH_buscador extends PresentadorV_buscador {
	
	@Override
	protected void checkOrientation() {	    
		Configuration config = getResources().getConfiguration();
		if (config.orientation != Configuration.ORIENTATION_LANDSCAPE) {
			changePresenter();
		} 
	}

	@Override
	protected void loadView() {
		setContentView(R.layout.vista_h_buscador);
	}

	@Override
	protected Intent getPresenter() {
		Intent intent = new Intent(PresentadorH_buscador.this,
				PresentadorV_buscador.class);
		return intent;
	}

}
