package es.ulpgc.IST.infosierrapp.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.main.FuentesTTF.Fuentes;

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
     * Configura el widget que muestra el tiempo
     */
    private void wi_weather_config(){
    	wi_webview.setClickable(false);
    	wi_webview.getSettings().setLoadWithOverviewMode(true);
    	// wi_webview.getSettings().setUseWideViewPort(true);
		wi_webview.getSettings().setJavaScriptEnabled(true);				
		wi_webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){
				// view.loadUrl(url); no abrimos nada
				return true;
			}
		});
		wi_webview.getSettings().setAllowContentAccess(false);
		wi_webview.getSettings().setSupportZoom(false);
        wi_webview.getSettings().setSaveFormData(false);
        wi_webview.getSettings().setBuiltInZoomControls(false);
		wi_webview.loadUrl(WEATHER_HTML_FILE);
    }

    /**
     * Reacciona a los eventos de click
     */
	@Override
	public void onClick(View view) {
		int btn = view.getId();
	
	}

}
