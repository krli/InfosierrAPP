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
		setContentView(R.layout.vista_h_buscador);
	}

	@Override
	protected Intent getPresenter() {
		Intent intent = new Intent(PresentadorH_main.this,
				PresentadorV_main.class);
		return intent;
	}

}
