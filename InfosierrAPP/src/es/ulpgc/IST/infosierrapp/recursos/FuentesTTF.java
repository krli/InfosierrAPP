package es.ulpgc.IST.infosierrapp.recursos;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

/**
 * Gestión de fuentes externas de Android.
 * Esta clase proporciona métodos para cambiar la
 * apariencia a los componentes de los layouts, usando
 * fuentes propias.
 * 
 */
public class FuentesTTF {
	
	/**
	 * Fuentes disponibles
	 */
	public static enum Fuentes {
		segoe, roboto; 
	}
	
	/**
	 * Paths donde se almacenan los ficheros ttf
	 * ( desde proyecto/Assets )
	 */
	public static final String path_segoe = "fonts/segoe.ttf";
	public static final String path_roboto = "fonts/roboto.ttf";
	    
    
    /**
     * Devuelve el Typeface a partir de la enum y el contexto de la app.
     * Devuelve null si la fuente es desconocida.
     */
    private static Typeface getFont(Context context, Fuentes fuente) {
    	
    	String path = null;
    	Typeface font = null;
    	    	
    	switch(fuente){
		case roboto:
			path = path_roboto;
			break;
		case segoe:
			path = path_segoe;
			break;
		default:
			return null;
    	} 	
    	
   		// "Fabrica" y devuelve la fuente
   		font = Typeface.createFromAsset(context.getAssets(), path);
    	return font;
    }
	
	
    /**
     * Establece la fuente a un TextView
     * 
     * @param context Contexto de la actividad 
     * @param item TextView al que se quiere cambiar la fuente
     * @param fuente qué fuente de las disponibles (enum Fuentes)
     */
    public static void setFont( Context context, TextView item, Fuentes fuente ) {  
    	Typeface font = getFont(context, fuente);
    	if (font != null) {
    		item.setTypeface(font);
    	}
    }
    /**
     * Establece la fuente a un Button
     * 
     * @see setFont( Context context, TextView item, Fuentes fuente )
     */
    public static void setFont( Context context, Button item, Fuentes fuente ) {    	  
    	Typeface font = getFont(context, fuente);
    	if (font != null) {
    		item.setTypeface(font);
    	}
    }

}
