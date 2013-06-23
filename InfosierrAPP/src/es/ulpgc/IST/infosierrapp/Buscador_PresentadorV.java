package es.ulpgc.IST.infosierrapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class Buscador_PresentadorV extends Activity implements OnClickListener {
	
	/**
	 * Modelo de datos.
	 * Usar get/set para acceder.
	 */
	private Modelo _modelo;
	
	

	/**
	 * @return the _modelo
	 */
	protected Modelo get_modelo() {
		return _modelo;
	}

	/**
	 * @param _modelo the _modelo to set
	 */
	protected void set_modelo(Modelo _modelo) {
		this._modelo = _modelo;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_buscador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_buscador, menu);
		return true;
	}
	
	public void funcionTonta() {
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
