package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;

public class MenuFragmentActivity extends FragmentActivity{
	
	/*
	 * Referencias a componentes del layout;
	 */
	protected ActionBar action_bar;
	
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
		getMenuInflater().inflate(R.menu.detail_menu, menu);
		
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

		case R.id.menu_llamar:
			return goLlamar();
			
		case R.id.menu_compartir:
			return goCompartir();

		case R.id.menu_email:	
			return goEmail();
		
		case android.R.id.home:
			return goMain();
			
		case R.id.menu_exit:
			return goExit();
			
		case R.id.menu_opciones:
			return goOpciones();
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	/*** MÉTODOS PARA EL MENÚ ***/
	protected boolean goLlamar() {
		return true;
	}
	protected boolean goCompartir() {
		return true;
	}
	protected boolean goEmail(){
		return true;
	}
	protected boolean goMain() {
		// Intent intent = new Intent(this, PresentadorV_main.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		
        // startActivity(intent);
        // finish();
        // return true;
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
	protected boolean goOpciones() {
		Toast.makeText(getApplicationContext(), 
				"+ Menú de Opciones + (en obras)", 
				Toast.LENGTH_SHORT).show();
		return true;
	}

}
