package es.ulpgc.IST.infosierrapp.main;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class FuentesTTF {
	
	public static enum Fuentes {
		segoe, roboto; 
	}
	
    /**
     * Establece la una fuente TTF a un TextView
     * 
     * @param context Contexto de la actividad 
     * @param item TextView al que se quiere establecer la fuente
     * @param fuente qué fuente
     */
    public static void setFont( Context context, TextView item, Fuentes fuente ) {
  
    	Typeface font = getFont(context, fuente);
    	if (font != null) {
    		item.setTypeface(font);
    	}

    }
    
    public static void setFont( Context context, Button item, Fuentes fuente ) {
    	  
    	Typeface font = getFont(context, fuente);
    	if (font != null) {
    		item.setTypeface(font);
    	}

    }
    
    
    private static Typeface getFont(Context context, Fuentes fuente) {
    	
    	String path = null;
    	Typeface font = null;
    	
    	// Obtiene el path
    	switch(fuente){
		case roboto:
			path = "fonts/roboto.ttf";
			break;
		case segoe:
			path = "fonts/segoe.ttf";
			break;
		default:
			return null;
    	} 	

   		// "Fabrica" la fuente
   		font = Typeface.createFromAsset(context.getAssets(), path);

    	return font;
    }

}
