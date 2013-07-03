package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

public class PresentadorV_main extends MenuActivity implements OnClickListener, IfazActBuscador {
		
	/**
	 * Modelo de datos.
	 */
	protected Modelo_main modelo;
	
	/*
	 * Referencias a componentes del layout;
	 */
	private SearchView		wi_search;
	private ProgressBar 	wi_progreso;
	private Button			b_buscar;
	
	/**
	 * Inicio de la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
				
		/* Comprobaciones de arranque */
		// Inicio.loquesea();
		// Verifica la orientación
		checkOrientation();
		
		/* Carga el layout */
		loadView();
		
		/* Recupera el modelo desde el singleton */
		modelo = Modelo_main.getModel();
		
		/* Inicializa las refs al layout */
		wi_search=(SearchView)findViewById(R.id.searchView1);
		wi_progreso=(ProgressBar)findViewById(R.id.progressBar1);
		b_buscar=(Button)findViewById(R.id.B_buscar);
				
		/* Registra los listeners */
		b_buscar.setOnClickListener(this);
		
		/* Configuraciones de layout */
		b_buscar.setVisibility(View.VISIBLE);
		wi_progreso.setVisibility(View.GONE);
		// wi_search.setSubmitButtonEnabled(true);
		
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    // Assumes current activity is the searchable activity
	    wi_search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    
		
		/* Recoge el Intent de llamada y gestiona la acción */
	    //Toast.makeText(getApplicationContext(), "+++onCreate()", Toast.LENGTH_LONG).show();
		gestionaIntent(getIntent());	
	}
	
	/**
	 *  launchMode="singleTop" en manifest --> El sistema llama a este método
	 *  para entregarle el Intent si esta actividad ya está al frente
	 *  y evitar así invocarla 2 veces
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		//Toast.makeText(getApplicationContext(), "---onNewIntent()", Toast.LENGTH_LONG).show();
		gestionaIntent(intent);
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
    
    
    protected void gestionaIntent(Intent intent) {
    	
    	//Toast.makeText(getApplicationContext(), "***gestionaIntent()", Toast.LENGTH_SHORT).show();

    	if (Intent.ACTION_VIEW.equals(intent.getAction())) {
    		// handles a click on a search suggestion; launches activity to show word
    		Toast.makeText(getApplicationContext(), "Las sugerencias no funcionan aún", Toast.LENGTH_SHORT).show();
    		// startActivity(wordIntent);
    	} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
    		// Ejecuta la búsqueda
    		String query = intent.getStringExtra(SearchManager.QUERY);
    		
    		new TareaBusqueda(this).execute(query);    		    		
    	}	
    	
    }
	

    /**
     * Reacciona a los eventos de click
     */
	@Override
	public void onClick(View view) {
		int btn = view.getId();
    	switch (btn) {
    	case R.id.B_buscar:
    		// Se ha pulsado buscar... activa la búsqueda
    		wi_search.setQuery(wi_search.getQuery(), true);    		
    		break;    		
    	}		
	}
	
	/**
	 * Activa/desactiva la progress bar redondita
	 */
	private void startProgressBar() {
	    b_buscar.setVisibility(View.GONE);
	    wi_progreso.setVisibility(View.VISIBLE);
	}
    private void stopProgressBar() {
    	wi_progreso.setVisibility(View.GONE);
    	b_buscar.setVisibility(View.VISIBLE);
	}
    
    
    /**********************************************************************
     * Los siguientes métodos definen la interfaz para la realimentación 
     * desde la TareaBusqueda
     */
    
    /*
     * (non-Javadoc)
     * @see es.ulpgc.IST.infosierrapp.main.IfazActBuscador#busquedaIniciada()
     */
	@Override
	public void busquedaIniciada() {
		startProgressBar();		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.IfazActBuscador#busquedaFinalizada(boolean)
	 */
	@Override
	public void busquedaFinalizada(boolean result) {
		stopProgressBar();

		//pasar al maestro-detalle
		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.IfazActBuscador#busquedaCancelada()
	 */
	@Override
	public void busquedaCancelada() {
		stopProgressBar();		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.IfazActBuscador#progresoBusqueda(int)
	 */
	@Override
	public void progresoBusqueda(int progreso) {
		Toast.makeText(getApplicationContext(), 
				"Buscando...<"+progreso+">", Toast.LENGTH_SHORT).show();		
	}	

}
