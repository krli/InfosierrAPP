package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.R;
import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PresentadorV_main extends MenuActivity implements OnClickListener {
		
	/**
	 * Modelo de datos.
	 */
	protected Modelo_main modelo;
	
	/*
	 * Referencias a componentes del layout;
	 */

	
	/**
	 * Inicio de la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Recupera el modelo desde el singleton
		modelo = Modelo_main.getModel();
		
		/* Configuraciones iniciales */

		// Comprobaciones de arranque
		//Inicio.loquesea();
		// Verifica la orientación
		checkOrientation();
		
		// Y por último carga el layout
		loadView();
	}

	/**
	 * Se llamará si hay algún cambio en las configuraciones del teléfono, como
	 * por ejemplo, un cambio de orientación.
	 */
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Si ha habido un giro...
        checkOrientation();    
    }
		
    
	/**
	 * Verifica que la orientación del dispositivo concuerda con
	 * la del presentador en uso. Si no es así fuerza el cambio
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
    
    /**
     * Devuelve un intent para cambiar al presentador que
     * corresponda:
     * PresenV --> PresenH
     * PresenH --> PresenV 
     * @return
     */
    protected Intent getPresenter() {
    	Intent intent = new Intent(PresentadorV_main.this,
    			PresentadorH_main.class);
    	return intent;
    }

    /**
     * Carga el layout.
     */
    protected void loadView() {
        /*Toast.makeText(getApplicationContext(),
                "loadView1()", Toast.LENGTH_SHORT).show();*/
        setContentView(R.layout.main_vista_v);
    }
	

    /**
     * Reacciona a los eventos de click
     */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
