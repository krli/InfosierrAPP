package es.ulpgc.IST.infosierrapp.main;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;
import es.ulpgc.IST.infosierrapp.datos.ListenerTareaAsync;
import es.ulpgc.IST.infosierrapp.datos.TareaBusqueda;
import es.ulpgc.IST.infosierrapp.eltiempo.Presentador_weather;
import es.ulpgc.IST.infosierrapp.maestrodetalle.ListPresenter;
import es.ulpgc.IST.infosierrapp.recursos.FuentesTTF;
import es.ulpgc.IST.infosierrapp.recursos.FuentesTTF.Fuentes;

public class PresentadorV_main extends MenuActivity implements OnClickListener, ListenerTareaAsync {
	
	/**
	 * Valores de configuración del Intent para el
	 * cambio entre este presentador y el horizontal 
	 */
	protected final String INTENT_ACTION="cambio_orientacion";
	protected final String INTENT_CONTENT_WISEARCH="contenido_wisearch";
	
	/**
	 * Número de botones de sugerencia
	 */
	protected final int N_SUGERENCIAS=6;
	
	/**
	 * Sugerencias por defecto para los botones
	 */
	public static final String[] DEF_SUGERENCIAS = {"Talleres", "Restaurantes",
			"Fontanería", "Médico",
			"Transportes", "Librerías" };
	
	/**
	 * Clase buscador de datos que proporciona el historial de búsquedas 
	 */
	protected BuscadorDatos buscador;
	
	/**
	 * Tarea asíncrona que se lanza en segundo plano para ejecutar la búsqueda
	 */
	protected TareaBusqueda busqueda;
	
	/*
	 * Referencias a componentes del layout;
	 */
	protected SearchView	wi_search;
	private RelativeLayout	ly_progreso;
	private Button			b_buscar;
	private Button			b_weather;
	private Button			b_cancelar;
	private Button[] 		b_sugerencias;
	private TextView		txt_subtitulo;
	
	
	/**
	 * Creación de la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
				
		/* Comprobaciones de arranque */
		// Inicio.loquesea();
		
		/* Carga el layout */
		loadView();
		
		/* Inicializa las refs al layout */
		wi_search=(SearchView)findViewById(R.id.searchView1);
		ly_progreso=(RelativeLayout)findViewById(R.id.lytProgreso2);
		txt_subtitulo=(TextView)findViewById(R.id.textSubtitulo);
		b_buscar=(Button)findViewById(R.id.B_buscar);
		b_weather=(Button)findViewById(R.id.B_weather);
		b_cancelar=(Button)findViewById(R.id.B_cancelar);		
		// Botones de sugerencias...
		b_sugerencias = new Button[N_SUGERENCIAS];
		b_sugerencias[0]=(Button)findViewById(R.id.B_suge1);
		b_sugerencias[1]=(Button)findViewById(R.id.B_suge2);
		b_sugerencias[2]=(Button)findViewById(R.id.B_suge3);
		b_sugerencias[3]=(Button)findViewById(R.id.B_suge4);
		b_sugerencias[4]=(Button)findViewById(R.id.B_suge5);
		b_sugerencias[5]=(Button)findViewById(R.id.B_suge6);
				
		/* Registra los listeners */
		b_buscar.setOnClickListener(this);
		b_weather.setOnClickListener(this);
		b_cancelar.setOnClickListener(this);		
		for (int k=0; k<b_sugerencias.length; k++) {
			b_sugerencias[k].setOnClickListener(this);
		}
		
		/* Configuraciones de layout */
		//Botón buscar visible y progressbar oculta
		b_buscar.setVisibility(View.VISIBLE);
		ly_progreso.setVisibility(View.GONE);
		// fuentes
		FuentesTTF.setFont(this, txt_subtitulo, Fuentes.segoe);
		FuentesTTF.setFont(this, b_weather, Fuentes.segoe);		
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    // Assumes current activity is the searchable activity
	    wi_search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    
		/* Engancha con el buscador  */
		buscador = BuscadorDatos.getBuscador(getApplicationContext());
	    		
		/* Y reacciona según el intent de llamada */	    
		gestionaIntent(getIntent());	
	}
	
	/**
	 *  launchMode="singleTop" en manifest --> El sistema llama a este método
	 *  para entregarle el Intent si esta actividad ya está al frente
	 *  y evitar así invocarla 2 veces
	 */
	@Override
	protected void onNewIntent(Intent intent) {		
		if (intent != null) {
			gestionaIntent(intent);
		}		
	}
	
	
	/**
	 * onCreate() -> onStart() -> onResume() -> Actividad
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// Verifica la orientación
		checkOrientation();
    	// Actualiza los botones de sugerencias
    	actualizaSugerencias();
	}
	
	/**
	 * Actividad -> onPause() -> onStop() -> onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Si se sale de la APP libera la búsqueda
		// y desconecta la bd
		buscador.liberar_busqueda();
	}
	
	
	/**
	 * Se llamará si hay algún cambio en las configuraciones del teléfono, como
	 * por ejemplo, un cambio de orientación.
	 */
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Verifica la orientación
        checkOrientation();    
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
    		wi_search.setQuery(b_sugerencias[0].getText(), true);
    		break;
    	case R.id.B_suge2:
    		wi_search.setQuery(b_sugerencias[1].getText(), true);
    		break;
    	case R.id.B_suge3:
    		wi_search.setQuery(b_sugerencias[2].getText(), true);
    		break;
    	case R.id.B_suge4:
    		wi_search.setQuery(b_sugerencias[3].getText(), true);
    		break;
    	case R.id.B_suge5:
    		wi_search.setQuery(b_sugerencias[4].getText(), true);
    		break;
    	case R.id.B_suge6:
    		wi_search.setQuery(b_sugerencias[5].getText(), true);
    		break;
    	case R.id.B_cancelar:
    		cancelarBusqueda();
    		break;
    	}
	}
	
    /**
	 * Activa el item menu_borrar_busquedas
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Primero configura el menu de MenuActivity
		super.onCreateOptionsMenu(menu);
		// Activa el item
		MenuItem item = menu.findItem(R.id.menu_borrar_busquedas);		
		if (item !=null) {
			item.setEnabled(true);
			item.setVisible(true);
		}		
		return true;
	}
	
	/**
	 * Configura el click en menu_borrar_busquedas
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_borrar_busquedas:
			do_limpiar_sugerencias();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/* *************************************************
	 * Los siguientes 4 métodos gestionan el cambio de 
	 * layout con la orientación. Los 3 primeros debenrán
	 * ser sobreescritos por el presentador que herede
	 * ************************************************* */
	
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
     * Devuelve un intent para cambiar al presentador que
     * corresponda:
     * PresenV --> PresenH
     * PresenH --> PresenV 
     * @return
     */
    protected Intent getIntentForChangePresenter() {
    	// Destino: Presentador H
    	Intent intent = new Intent(PresentadorV_main.this,
    			PresentadorH_main.class);
		intent.setAction(INTENT_ACTION);	
		// Guarda en el intent el contenido de la searchview
		// ojo: es tipo CharSequence		
		intent.putExtra(INTENT_CONTENT_WISEARCH, wi_search.getQuery());
    	return intent;
    }
    /**
     * Carga el layout.
     */
    protected void loadView() {
        setContentView(R.layout.main_vista_v);
    }
    /**
     * Cambia de presentador
     */
    protected void changePresenter() {
    	// Get the next Controller
    	Intent intent = getIntentForChangePresenter();    	
    	// Start the next and finish the current Controller
    	startActivity(intent);
    	finish();
    }    
    /* ************************************************* */

    /**
     * Gestiona el intent que se recibe en el arranque o la llamada
     * a onNewIntent de la actividad
     * 
     * @param intent
     */
    private void gestionaIntent(Intent intent) {

    	if (Intent.ACTION_VIEW.equals(intent.getAction())) {
   		/*** Click en una sugerencia de searchview... (no soportado aún)  ***/
    		
    	} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
   		/*** Ejecutar una búsqueda ***/

    		// Extrae la cadena desde el Intent
    		String query_string = intent.getStringExtra(SearchManager.QUERY);

    		// Ejecuta la búsqueda en segundo plano
    		busqueda = new TareaBusqueda(buscador, this);
    		if (busqueda !=null) {
    			busqueda.execute(query_string);
    		}    		
    		
    	} else if (INTENT_ACTION.equals(intent.getAction())) {
   		/*** Cambio de orientación ***/
    		// Mantiene el contenido de la searchview
    		wi_search.setQuery( intent.getCharSequenceExtra(INTENT_CONTENT_WISEARCH), false);
    		
    	} else {
   		/*** No se hace nada ***/
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
    
	/**
	 * Sobreescribe el método goMain de MenuActivity
	 * para no hacer nada (ya estamos en Main)
	 */
	@Override
	protected boolean goMain() {
		return true;
	}

    /**
     * Actualiza los botones de sugerencias
     */
    private void actualizaSugerencias() {    	   	

    	// Pide un vector con las últimas N_SUGERENCIAS búsquedas
    	// get_historial siempre devuelve un vector tamaño N_SUGERENCIAS
    	// relleno con null si no las hay
    	String[] historial = buscador.get_historial(N_SUGERENCIAS);
    	
    	// Establece el texto para cada botón...
    	for(int k=0; k < historial.length; k++) {    		    		
    		
    		String texto = historial[k]; 
    		// Si la entrada k está vacía..
    		if ( texto == null) {
    			// Rellena el botón con el valor por defecto
    			texto = DEF_SUGERENCIAS[k];
    			// Y lo añade al historial para que haya concordancia
    			buscador.add_to_historial(texto);
    		}    		
    		b_sugerencias[k].setText(texto);
    	}    	
    }
    
    /**
     * Fuerza la parada de la tarea de búsqueda
     */
    private void cancelarBusqueda() {
    	if (busqueda !=null) {
			busqueda.cancel(true);
		}    	
    }    
	
	/**
	 * Activa/desactiva la progress bar
	 */
	private void startProgressBar() {
	    b_buscar.setVisibility(View.GONE);
	    // wi_progreso.setVisibility(View.VISIBLE);
	    ly_progreso.setVisibility(View.VISIBLE);
	}
    private void stopProgressBar() {
    	// wi_progreso.setVisibility(View.GONE);
    	ly_progreso.setVisibility(View.GONE);
    	b_buscar.setVisibility(View.VISIBLE);
	}    
    
	/**
	 * Limpia los botones de sugerencias, mostrando previamente
	 * un diálogo de confirmación.
	 */
	private void do_limpiar_sugerencias() {		
		//Crea un simple diálogo SI/NO para confirmar
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Restableciendo sugerencias...");
    	builder.setMessage("¿Estás seguro?");
    	builder.setIcon(android.R.drawable.ic_delete);
    	builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int which) {			      	
    	    	//Si la respuesta es sí...
    	    	buscador.limpiar_historial();
    			actualizaSugerencias();    	    	
    	    }
    	});
    	builder.setNegativeButton("No", null);
    	builder.show();		
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
	public void tareaIniciada() {
		startProgressBar();		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#busquedaFinalizada(boolean)
	 */
	@Override
	public void tareaFinalizada(boolean result) {
		
		// Desenlaza la tarea 
		busqueda = null;
		
		// Para la barra de progreso
		stopProgressBar();
		
		// Limpia la searchview (por comodidad)    		
		wi_search.setQuery("", false);

		// Y pasa al maestro-detalle
		goToMaestroDetalle();
		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#busquedaCancelada()
	 */
	@Override
	public void tareaCancelada() {
		stopProgressBar();
		busqueda = null;
		Toast.makeText(getApplicationContext(), 
				"Búsqueda cancelada.", 
				Toast.LENGTH_SHORT).show();
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#progresoBusqueda(int)
	 */
	@Override
	public void progresoTarea(int progreso) {
		Toast.makeText(getApplicationContext(), 
				"Buscando...<"+progreso+">", Toast.LENGTH_SHORT).show();		
	}	

}
