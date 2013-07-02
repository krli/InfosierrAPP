package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {
	
	
	private String _infosierraWebURL="http://www.infosierra.es";
	
	/*
	 * Referencias a componentes del layout;
	 */
	protected ActionBar action_bar;
	
	
	/* Getters/Setters */
	public String get_infosierraWebURL() {
		return _infosierraWebURL;
	}
	protected void set_infosierraWebURL(String url) {
		_infosierraWebURL=url;
	}
	
	
	/**
	 * Inicio de la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Inicializa las refs al layout */
		action_bar = getActionBar();
	}	
	
	/**
	 *  Prepara la ActionBar en el arranque de la actividad.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    //SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    inflater.inflate(R.menu.main_menu, menu);
	    
		// activa el menu home para volver al main
		action_bar.setDisplayHomeAsUpEnabled(true);
	    
	    return super.onCreateOptionsMenu(menu);
	    // return true;
	}
	
	
	/**
	 * Reacciona las acciones en la ActionBar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go main activity
			return goMain();
		case R.id.menu_web:
			// se ha pulsado el boton para abrir infosierra.es
			goWeb();
			return true;
		case R.id.menu_exit:
			// se ha pulsado el boton de salir
			goExit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected boolean goMain() {
		Intent intent = new Intent(this, PresentadorV_main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
	}
	
	protected boolean goExit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		return true;
	}
	
	protected boolean goWeb() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(get_infosierraWebURL()));
		startActivity(intent);
		return true;
	}

}
