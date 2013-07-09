package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;
import es.ulpgc.IST.infosierrapp.datos.ListenerTareaBusqueda;
import es.ulpgc.IST.infosierrapp.datos.TareaBusqueda;
import es.ulpgc.IST.infosierrapp.maestrodetalle.ListPresenter;
import es.ulpgc.IST.infosierrapp.main.FuentesTTF.Fuentes;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class PresentadorV_main extends MenuActivity implements OnClickListener, ListenerTareaBusqueda {
	
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
	private Button			b_weather;
	private Button			b_suge1;
	private Button			b_suge2;
	private TextView		txt_titulo;
	private TextView		txt_subtitulo;
	
	
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
		b_weather=(Button)findViewById(R.id.B_weather);
		b_suge1=(Button)findViewById(R.id.B_suge1);
		b_suge2=(Button)findViewById(R.id.B_suge2);
		txt_titulo=(TextView)findViewById(R.id.textTitulo);
		txt_subtitulo=(TextView)findViewById(R.id.textSubtitulo);
				
		/* Registra los listeners */
		b_buscar.setOnClickListener(this);
		b_weather.setOnClickListener(this);
		b_suge1.setOnClickListener(this);
		b_suge2.setOnClickListener(this);
		
		/* Configuraciones de layout */
		b_buscar.setVisibility(View.VISIBLE);
		wi_progreso.setVisibility(View.GONE);
		// wi_search.setSubmitButtonEnabled(true);
		FuentesTTF.setFont(this, txt_subtitulo, Fuentes.segoe);
		FuentesTTF.setFont(this, b_weather, Fuentes.segoe);
		
		actualizaSugerencias();
		
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
		
		actualizaSugerencias();
		
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
        setContentView(R.layout.main_vista_v);
    }
    

    /**
     * 
     * @param intent
     */
    protected void gestionaIntent(Intent intent) {

    	
    	// Click en una sugerencia de búsqueda... (no soportado aún)
    	if (Intent.ACTION_VIEW.equals(intent.getAction())) {
    		
    		Toast.makeText(getApplicationContext(), 
    				"Las sugerencias no funcionan aún",
    				Toast.LENGTH_SHORT).show();

    		// Intro o click en Buscar --> Ejecuta la búsqueda
    	} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

    		// Extrae la cadena desde el Intent
    		String query_string = intent.getStringExtra(SearchManager.QUERY);

    		// Ejecuta la búsqueda en segundo plano
    		TareaBusqueda busqueda = new TareaBusqueda(getApplicationContext(), this); 
    		busqueda.execute(query_string);    		    		
    	}	

    }
	
    /**
     * Cambia a la actividad que muestra los resultados de
     * la búsqueda.
     */
    private void goToMaestroDetalle(){
    	Intent intent = new Intent(PresentadorV_main.this,
    			ListPresenter.class);    	
    	startActivity(intent);   	
    }
    
    /**
     * Cambia a la actividad que muestra 
	 * el widget del tiempo
     */
    private void goToWeather(){
    	Intent intent = new Intent(PresentadorV_main.this,
    			Presentador_weather.class);    	
    	startActivity(intent);   	
    }

    
    private void actualizaSugerencias() {
    	BuscadorDatos buscador = BuscadorDatos.getBuscador(getApplicationContext());    	
    	String[] busquedas = buscador.get_historial(6);
    	
    	Toast.makeText(getApplicationContext(), 
				"busquedas["+busquedas.length+"]", Toast.LENGTH_SHORT).show();
    	
    	Toast.makeText(getApplicationContext(), 
				"busquedas[0]:"+busquedas[0], Toast.LENGTH_SHORT).show();
    	Toast.makeText(getApplicationContext(), 
				"busquedas[1]:"+busquedas[1], Toast.LENGTH_SHORT).show();
    	
    	for(int k=0; k<busquedas.length; k++) {
    		if ( busquedas[k] != null) {
    			setSugeText(k, busquedas[k]);
    		}
    	}
    }
    private void setSugeText(int index, String text) {
    	switch(index) {
    	case 0:
    		b_suge1.setText(text);
    		break;
    	case 1:
    		b_suge2.setText(text);
    		break;
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
    		
    	case R.id.B_weather:
    		goToWeather();
    		break;
    		
    	case R.id.B_suge1:
    		wi_search.setQuery(b_suge1.getText(), true);
    		break;
    	}		
    	
    	
	}
	
	/**
	 * Activa/desactiva la progress bar
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
     * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#busquedaIniciada()
     */
	@Override
	public void busquedaIniciada() {
		startProgressBar();		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#busquedaFinalizada(boolean)
	 */
	@Override
	public void busquedaFinalizada(boolean result) {
		stopProgressBar();

		//pasar al maestro-detalle
		// goToMaestroDetalle();
		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#busquedaCancelada()
	 */
	@Override
	public void busquedaCancelada() {
		stopProgressBar();		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#progresoBusqueda(int)
	 */
	@Override
	public void progresoBusqueda(int progreso) {
		Toast.makeText(getApplicationContext(), 
				"Buscando...<"+progreso+">", Toast.LENGTH_SHORT).show();		
	}	

}
