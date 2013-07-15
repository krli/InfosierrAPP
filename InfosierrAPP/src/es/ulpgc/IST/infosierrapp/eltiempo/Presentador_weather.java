package es.ulpgc.IST.infosierrapp.eltiempo;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.ListenerTareaAsync;
import es.ulpgc.IST.infosierrapp.main.MenuActivity;
import es.ulpgc.IST.infosierrapp.recursos.FuentesTTF;
import es.ulpgc.IST.infosierrapp.recursos.FuentesTTF.Fuentes;

/**
 * Presentador para la actividad weather. Simplemente 
 * tiene un componente webview que carga un html con un 
 * applet desde eltiempo.es
 *
 */
public class Presentador_weather extends MenuActivity implements ListenerTareaAsync {
		
	// Ubicación de los ficheros con el widget html para el tiempo
	public final String WEATHER_HTML_FILE_V="file:///android_asset/tiempo_sierra_v.html";
	public final String WEATHER_HTML_FILE_H="file:///android_asset/tiempo_sierra_h.html";
	
	/*
	 * Referencias a componentes del layout;
	 */
	private WebView			wi_webview;
	private TextView		txt_titulo;
	private ProgressBar		pbar;
	
	
	/**
	 * Inicio de la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
				
		/* Comprobaciones de arranque */
		// Inicio.loquesea();
			
		/* Carga el layout */
		loadView();
		
	
		/* Inicializa las refs al layout */
		wi_webview=(WebView)findViewById(R.id.webView1);
		txt_titulo=(TextView)findViewById(R.id.textWeather);
		pbar=(ProgressBar)findViewById(R.id.progressBar1);
				
		/* Registra los listeners */
		
		/* Configuraciones de layout */
		FuentesTTF.setFont(this, txt_titulo, Fuentes.segoe);
		wi_webview.setVisibility(View.GONE);
		pbar.setVisibility(View.GONE);		
		wi_weather_config();
		
		// Carga el widget según la orientación
		checkOrientation(); 
		
	}
	 
    /**
     * Carga el layout.
     */
    protected void loadView() {
        setContentView(R.layout.weather);
    }
    
  
    /**
     * Configura el webview que muestra el tiempo
     */
    @SuppressLint("SetJavaScriptEnabled")
	private void wi_weather_config(){
    	// Modo de vista
    	wi_webview.getSettings().setLoadWithOverviewMode(true);
		// Activa javascript para que funcione el widget
    	wi_webview.getSettings().setJavaScriptEnabled(true);	
		// Evita clicks y apertura del navegador externo
		wi_webview.setClickable(false);
		wi_webview.getSettings().setAllowContentAccess(false);
		wi_webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){
				// view.loadUrl(url); no abrimos nada
				return true;
			}
		});
		// Permite zoom para facilitar la vista
		wi_webview.getSettings().setSupportZoom(true);
		wi_webview.getSettings().setBuiltInZoomControls(true);
		wi_webview.getSettings().setDisplayZoomControls(true);
		// Muestra siempre la barra de scroll
		wi_webview.setScrollbarFadingEnabled(false);
		wi_webview.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        // Nada de formularios, etc...
		wi_webview.getSettings().setSaveFormData(false);		
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
	 * Comprueba la orientación del dispositivo para cargar
	 * el widget del tiempo correspondiente
	 */
	private void checkOrientation() {      
		Configuration config = getResources().getConfiguration();      
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// wi_webview.loadUrl(WEATHER_HTML_FILE_H);
			new TareaWebLoad(wi_webview,this).execute(WEATHER_HTML_FILE_H);
		} else {
			// wi_webview.loadUrl(WEATHER_HTML_FILE_V);
			new TareaWebLoad(wi_webview,this).execute(WEATHER_HTML_FILE_V);
		}      
	}

	@Override
	public void tareaIniciada() {
		wi_webview.setVisibility(View.GONE);
	    pbar.setVisibility(View.VISIBLE);		
	}

	@Override
	public void tareaFinalizada(boolean result) {
		if (result) {
			pbar.setVisibility(View.GONE);
			wi_webview.setVisibility(View.VISIBLE);
		} else {
			Toast.makeText(getApplicationContext(), 
					"Error en la carga del widget.", 
					Toast.LENGTH_SHORT).show();			
			wi_webview.setVisibility(View.GONE);
		}		
	}

	@Override
	public void tareaCancelada() {
		Toast.makeText(getApplicationContext(), 
				"Tarea cancelada.", 
				Toast.LENGTH_SHORT).show();
		pbar.setVisibility(View.GONE);
		wi_webview.setVisibility(View.GONE);
	}

	@Override
	public void progresoTarea(int progreso) {
		// TODO Auto-generated method stub		
	}

}
