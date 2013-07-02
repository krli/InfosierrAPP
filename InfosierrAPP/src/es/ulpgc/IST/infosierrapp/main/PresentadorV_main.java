package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.BD_resultados;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PresentadorV_main extends Activity implements OnClickListener {
	
	/**
	 * Modelo de datos.
	 */
	protected Modelo_main modelo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Recupera el modelo desde el singleton
		modelo = Modelo_main.getModel();
		
		setContentView(R.layout.main_vista_v);
		
		/*
		 * Declaramos el controlador de la BBDD y accedemos en modo escritura
		 */
		BD_resultados dbResultados = new BD_resultados(getBaseContext());

		SQLiteDatabase db = dbResultados.getWritableDatabase();

		Toast.makeText(getBaseContext(), "Base de datos local preparada", Toast.LENGTH_LONG).show();
	}

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Si ha habido un giro...
        checkOrientation();    
    }
    
	/**
	 * Verifica que la orientaci��n del dispositivo concuerda con
	 * la del presentador en uso. Si no es as�� fuerza el cambio
	 * al otro presentador.
	 */
    protected void checkOrientation() {      
      Configuration config = getResources().getConfiguration();
      if (config.orientation != Configuration.ORIENTATION_PORTRAIT) {
      	changePresenter();
      } 
    }
    
    /**
     * Cambia de presentador
     */
    protected void changePresenter() {
    	// Get the next Controller
    	Intent intent = getPresenter();    	
    	// Start the next and finish the current Controller
    	startActivity(intent);
    	finish();    	
    }
    

    protected Intent getPresenter() {

    	Intent intent = new Intent(PresentadorV_main.this,
    			PresentadorH_main.class);

    	return intent;
    }

    
    protected void loadView() {
        /*Toast.makeText(getApplicationContext(),
                "loadView1()", Toast.LENGTH_SHORT).show();*/
        setContentView(R.layout.main_vista_v);
    }
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
