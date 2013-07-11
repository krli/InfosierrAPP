package es.ulpgc.IST.infosierrapp.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import es.ulpgc.IST.infosierrapp.R;
import android.widget.Toast;

/**
 * Clase padre para realizar una configuración básica de la 
 * ActionBar. Todas las actividades que quieran mostrar el 
 * mismo menú sólo tienen que heredar de esta. Para ampliarlo
 * o modificarlo es suficiente con que sobreescriban los 
 * métodos onCreateOptionsMenu y onOptionsItemSelected, 
 * llamando al super para mantener la configuración que 
 * se realiza aquí.
 *
 */
public class MenuActivity extends Activity {
	
	// URL de la web para el item del menu ir a...
	private static final String _infosierraWebURL="http://www.infosierra.es";
	
	
	/*
	 * Referencias a componentes del layout;
	 */
	protected ActionBar action_bar;
	
	
	/* Getters/Setters */
	public String get_infosierraWebURL() {
		return _infosierraWebURL;
	}
	
	
	/**
	 * Creación de la actividad.
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
				
		// Levanta el menú desde main_menu.xml
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    
		// Activa el home UP para volver al main		
		action_bar.setDisplayHomeAsUpEnabled(true);
	    	
	    return super.onCreateOptionsMenu(menu);
	}
	
	
	/**
	 * Reacciona a las acciones en la ActionBar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		
		switch (item.getItemId()) {
		
		case android.R.id.home:
			// app icon in action bar clicked; go main activity
			return goMain();
			
		case R.id.menu_web:
			// se ha pulsado el boton para abrir infosierra.es			
			return goWeb();
			
		case R.id.menu_exit:
			// se ha pulsado el boton de salir
			return goExit();
			
		case R.id.menu_opciones:
			return goOpciones();
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	protected boolean goMain() {
		Intent intent = new Intent(this, PresentadorV_main.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
	}
	
	protected boolean goExit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
		return true;
	}
	
	protected boolean goWeb() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(get_infosierraWebURL()));
		startActivity(intent);
		return true;
	}
	
	protected boolean goOpciones() {
		Toast.makeText(getApplicationContext(), 
				"+ Menú de Opciones + (en obras)", 
				Toast.LENGTH_SHORT).show();
		return true;
	}

}
