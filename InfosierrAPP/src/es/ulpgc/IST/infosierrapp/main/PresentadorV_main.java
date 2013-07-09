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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.BuscadorDatos;
import es.ulpgc.IST.infosierrapp.datos.ListenerTareaBusqueda;
import es.ulpgc.IST.infosierrapp.datos.TareaBusqueda;
import es.ulpgc.IST.infosierrapp.maestrodetalle.ListPresenter;
import es.ulpgc.IST.infosierrapp.main.FuentesTTF.Fuentes;

public class PresentadorV_main extends MenuActivity implements OnClickListener, ListenerTareaBusqueda {
	
	/**
	 * Modelo de datos.
	 */
	protected Modelo_main modelo;
	/**
	 * Clase buscador de datos que proporciona el historial de búsquedas 
	 */
	protected BuscadorDatos buscador; 
	protected TareaBusqueda busqueda;
	
	/*
	 * Referencias a componentes del layout;
	 */
	private SearchView		wi_search;
	private ProgressBar 	wi_progreso;
	private RelativeLayout	ly_progreso;
	private Button			b_buscar;
	private Button			b_weather;
	private Button			b_cancelar;
	private Button			b_suge1;	
	private Button			b_suge2;
	private Button			b_suge3;
	private Button			b_suge4;
	private Button			b_suge5;
	private Button			b_suge6;
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
		buscador = BuscadorDatos.getBuscador(getApplicationContext());
		
		/* Inicializa las refs al layout */
		wi_search=(SearchView)findViewById(R.id.searchView1);
		wi_progreso=(ProgressBar)findViewById(R.id.progressBar1);	
		ly_progreso=(RelativeLayout)findViewById(R.id.lytProgreso2);
		b_buscar=(Button)findViewById(R.id.B_buscar);
		b_weather=(Button)findViewById(R.id.B_weather);
		b_cancelar=(Button)findViewById(R.id.B_cancelar);
		b_suge1=(Button)findViewById(R.id.B_suge1);
		b_suge2=(Button)findViewById(R.id.B_suge2);
		b_suge3=(Button)findViewById(R.id.B_suge3);
		b_suge4=(Button)findViewById(R.id.B_suge4);
		b_suge5=(Button)findViewById(R.id.B_suge5);
		b_suge6=(Button)findViewById(R.id.B_suge6);
		txt_titulo=(TextView)findViewById(R.id.textTitulo);
		txt_subtitulo=(TextView)findViewById(R.id.textSubtitulo);
				
		/* Registra los listeners */
		b_buscar.setOnClickListener(this);
		b_weather.setOnClickListener(this);
		b_suge1.setOnClickListener(this);
		b_suge2.setOnClickListener(this);
		b_suge3.setOnClickListener(this);
		b_suge4.setOnClickListener(this);
		b_suge5.setOnClickListener(this);
		b_suge6.setOnClickListener(this);
		b_cancelar.setOnClickListener(this);
		
		/* Configuraciones de layout */
		b_buscar.setVisibility(View.VISIBLE);
		ly_progreso.setVisibility(View.GONE);
		// wi_progreso.setVisibility(View.GONE);
		// wi_search.setSubmitButtonEnabled(true);
		FuentesTTF.setFont(this, txt_subtitulo, Fuentes.segoe);
		FuentesTTF.setFont(this, b_weather, Fuentes.segoe);
		
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
		
		if (intent != null) {
			gestionaIntent(intent);
		}
		
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
     * Gestiona el intent que se recibe en el arranque o la llamada
     * a onNewIntent de la actividad
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
    		busqueda = new TareaBusqueda(buscador, this);
    		if (busqueda !=null) {
    			busqueda.execute(query_string);
    		}
    		
    	} else {
//    		Toast.makeText(getApplicationContext(), 
//    				"gestionaIntent: No Hago Nada",
//    				Toast.LENGTH_SHORT).show();
    	}
    	
    	// Actualiza los botones de sugerencias
    	actualizaSugerencias();

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
     * Actualiza los botones de sugerencias
     */
    private void actualizaSugerencias() {    	   	
    	String[] busquedas = buscador.get_historial(6);
    	for(int k=0; k<busquedas.length; k++) {
    		setSugeText(k, busquedas[k]);
    	}
    }
    /**
     * Método auxiliar para facilitar el establecimiento
     * del texto en los botones de sugerencias
     */
    private void setSugeText(int index, String text) {
    	switch(index) {
    	case 0:
    		if (text != null) {
    			b_suge1.setText(text);
    		} else {
    			b_suge1.setText(R.string.B_suge1);
    		}
    		break;
    	case 1:
    		if (text != null) {
    			b_suge2.setText(text);
    		} else {
    			b_suge2.setText(R.string.B_suge2);
    		}
    		break;
    	case 2:
    		if (text != null) {
    			b_suge3.setText(text);
    		} else {
    			b_suge3.setText(R.string.B_suge3);
    		}
    		break;
    	case 3:
    		if (text != null) {
    			b_suge4.setText(text);
    		} else {
    			b_suge4.setText(R.string.B_suge4);
    		}
    		break;
    	case 4:
    		if (text != null) {
    			b_suge5.setText(text);
    		} else {
    			b_suge5.setText(R.string.B_suge5);
    		}
    		break;
    	case 5:
    		if (text != null) {
    			b_suge6.setText(text);
    		} else {
    			b_suge6.setText(R.string.B_suge6);
    		}
    		break;
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
    	case R.id.B_suge2:
    		wi_search.setQuery(b_suge2.getText(), true);
    		break;
    	case R.id.B_suge3:
    		wi_search.setQuery(b_suge3.getText(), true);
    		break;
    	case R.id.B_suge4:
    		wi_search.setQuery(b_suge4.getText(), true);
    		break;
    	case R.id.B_suge5:
    		wi_search.setQuery(b_suge5.getText(), true);
    		break;
    	case R.id.B_suge6:
    		wi_search.setQuery(b_suge6.getText(), true);
    		break;
    	case R.id.B_cancelar:
    		cancelarBusqueda();
    		break;
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
	 * Activa el item borrar historial.
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
	 * Comportamiento de menu_borrar_busquedas
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
		
		busqueda = null;

		//pasar al maestro-detalle
		goToMaestroDetalle();
		
	}
	/*
	 * (non-Javadoc)
	 * @see es.ulpgc.IST.infosierrapp.main.ListenerTareaBusqueda#busquedaCancelada()
	 */
	@Override
	public void busquedaCancelada() {
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
	public void progresoBusqueda(int progreso) {
		Toast.makeText(getApplicationContext(), 
				"Buscando...<"+progreso+">", Toast.LENGTH_SHORT).show();		
	}	

}
