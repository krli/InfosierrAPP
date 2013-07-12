package es.ulpgc.IST.infosierrapp.recursos;

import java.util.Locale;


public class HerramientasStrings {

	// Separador para concatenar strings y poder guardarlas
	// en un solo campo de la base de datos
	public static final String SEPARATOR_DB= "#";

	// Separador para concatenar vectores de strings e
	// "imprimirlas bonitas"
	public static final String SEPARATOR_PRINT=", ";

	// Número máximo de caracteres a que se reduce una
	// String para usarla en búsquedas
	public static final int MAX_QUERY_CHARS=5;


	/**
	 * Trocea una cadena en subcadenas separadas por SEPARATOR_DB,
	 * y en un máximo de maxParts trozos. 
	 * Para usar en conjunto con joinStrings().
	 * 
	 * @param string la cadena a descomponer
	 * @param n_parts los trozos que se deben extraer. Si se
	 * encuentran menos trozos de n_parts, esas componentes del
	 * vector resultador tendrán valor null. 
	 * @return un vector de Strings con los trozos separados
	 */
	public static String[] splitString(String string, int n_parts) {		

		int substrings_counter = 0;		
		int start_index = 0;
		int next_separator_index = 0;
		boolean flag_fin=false;
		String resultado[] = new String[n_parts];

		// Comprob argumentos
		if(string == null){
			return null;
		}		
		// Recorremos la string 
		while ( ( ! flag_fin ) && (substrings_counter < resultado.length) ){
			// Busca el siguiente separador
			next_separator_index = string.indexOf(SEPARATOR_DB, start_index);			
			// Si lo encuentra
			if (next_separator_index > 0) {
				// Extrae la etiqueta entre el anterior y el actual separador
				resultado[substrings_counter]=
						string.substring(start_index, next_separator_index);
				// Actualiza el puntero de proceso
				start_index=next_separator_index+SEPARATOR_DB.length();
				// Actualiza la cuenta
				substrings_counter++;					
			} else { // No hay más separadores, FIN.
				flag_fin=true;
			}
		}

		return resultado;
	}

	/**
	 * Une un vector de Strings en una única String 
	 * separándolas mediante SEPARATOR. 
	 * Complementario a splitStrings.
	 * 
	 * @param vector de Strings a separar
	 * @return String unión de las componentes del vector
	 */
	public static String joinStrings(String[] vector) {

		StringBuffer str = new StringBuffer();

		if (vector == null) {
			return null;
		}
		for (int i=0; i<vector.length; i++) {
			str.append(vector[i] + SEPARATOR_DB);			
		}

		// Devuelve la String generada eliminando el último separator		
		return str.toString();		
	}

	/**
	 * Une un vector de Strings en una sola String,
	 * separando las componentes por SEPARATOR_PRINT
	 * para poder "imprimirlo bonito"
	 * @param v vector de Strings que se quiere fusionar
	 * @return String con el resultado
	 */
	public static String vectorToString(String []v) {

		if ( v==null ) {
			return null;
		}

		StringBuffer result = new StringBuffer();

		// Une las componentes añadiendo separadores
		for (int k=0; k < v.length; k++) {
			if ( (v[k] != null) 
			  && (v[k].length() > 0 ) 
			  && (! v[k].equals("null")) ){
				result.append(v[k] + SEPARATOR_PRINT);
			}
		}
		// Elimina el último separador
		return result.substring(0, result.lastIndexOf(SEPARATOR_PRINT) );		
	}

	/**
	 * Quita a una cadena los espacios iniciales y finales,
	 * las vocales acentuadas, convierte a minúsculas,
	 * escapa caracteres SQL...
	 * 
	 * @param orig cadena que se quiere limpiar
	 * @return cadena adaptada
	 */
	public static String adaptarCadenaParaBusqueda(String orig) {
		
		String result = null;		
		
		if (orig != null) {
			
			/* Nos quedamos con los  primeros caracteres para que 
			 * las búsquedas tipo fontanero, fontanería, fontaneros...
			 * tengan éxito			  
			 */
			if ( orig.length() > MAX_QUERY_CHARS ) {
				result = orig.substring(0, MAX_QUERY_CHARS);
			} else {
				result = orig;
			}
			
			/* Minúsculas, quita tildes y espacios... */
			result = result.toLowerCase( Locale.getDefault() );
			result = result.trim();
			result = result.replace('á', 'a');
			result = result.replace('é', 'e');
			result = result.replace('í', 'i');
			result = result.replace('ó', 'o');
			result = result.replace('ú', 'u');
			
			/* Escapar caracteres SQL */
			result = result.replace("\\", "\\\\");
			result = result.replace("'", "\\'");
			result = result.replace("_", "\\_");
			result = result.replace("%", "\\%");
			
		}		
		return result;
	}

}
