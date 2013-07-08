package es.ulpgc.IST.infosierrapp.main;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FuentesTTF {
	
	public static enum Fuentes {
		segoe, roboto; 
	}
	
    /**
     * Establece la una fuente TTF a un TextView
     * 
     * @param context Contexto de la actividad 
     * @param txtView TextView al que se quiere establecer la fuente
     * @param fuente qué fuente
     */
    public static void setFont( Context context, TextView txtView, Fuentes fuente ) {
  
    	String path = null;
    	switch(fuente){
		case roboto:
			path = "fonts/roboto.ttf";
			break;
		case segoe:
			path = "fonts/segoe.ttf";
			break;
		default:
			return;  	
    	}    	
    	// "Fabrica la fuente"
        Typeface font = Typeface.createFromAsset(context.getAssets(), path); 
        // La establece
        txtView.setTypeface(font);

    }

}
