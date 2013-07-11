package es.ulpgc.IST.infosierrapp.maestrodetalle;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.main.PresentadorH_main;
import es.ulpgc.IST.infosierrapp.main.PresentadorV_main;

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
