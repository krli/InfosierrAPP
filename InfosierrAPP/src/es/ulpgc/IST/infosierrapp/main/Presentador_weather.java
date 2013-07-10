package es.ulpgc.IST.infosierrapp.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.recursos.FuentesTTF;
import es.ulpgc.IST.infosierrapp.recursos.FuentesTTF.Fuentes;

/**
 * Presentador para la actividad weather. Simplemente 
 * tiene un componente webview que carga un html con un 
 * applet desde eltiempo.es
 *
 */
public class Presentador_weather extends MenuActivity implements OnClickListener {
		
	// Ubicación del fichero con el widget html para el tiempo
	public final String WEATHER_HTML_FILE="file:///android_asset/tiempo_aracena.html";
	
	
	/*
	 * Referencias a componentes del layout;
	 */
	private WebView			wi_webview;
	private TextView		txt_titulo;
	
	
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

				
		/* Registra los listeners */
		
		/* Configuraciones de layout */
		FuentesTTF.setFont(this, txt_titulo, Fuentes.segoe);
		wi_weather_config();
		
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
    	// wi_webview.getSettings().setUseWideViewPort(true);
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
        // Nada de formularios, etc...
		wi_webview.getSettings().setSaveFormData(false);
		//Y carga la web
		wi_webview.loadUrl(WEATHER_HTML_FILE);
    }

    /**
     * Reacciona a los eventos de click
     */
	@Override
	public void onClick(View view) {
	
	}

}
