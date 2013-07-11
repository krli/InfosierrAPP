package es.ulpgc.IST.infosierrapp.recursos;

public class HerramientasStrings {
	
	// Separador para múltiples strings concatenadas
	public static final String SEPARATOR= ", ";
	

	/**
	 * Trocea una cadena en subcadenas separadas por SEPARATOR,
	 * y en un máximo de maxParts trozos. Complementario
	 * a joinStrings().
	 * @param string la cadena a descomponer
	 * @param maxParts el máximo de trozos que se deben extraer
	 * @return un vector de Strings con los trozos separados
	 */
	public static String[] splitString(String string, int maxParts) {		

		int substrings_counter = 0;		
		int start_index = 0;
		int next_separator_index = 0;
		boolean flag_fin=false;
		String resultado[] = new String[maxParts];

		// Comprob argumentos
		if(string == null){
			return null;
		}		
		// Recorremos la string 
		while ( ( ! flag_fin ) && (substrings_counter < maxParts) ){
			// Busca el siguiente separador
			next_separator_index = string.indexOf(SEPARATOR, start_index);			
			// Si lo encuentra
			if (next_separator_index > 0) {
				// Extrae la etiqueta entre el anterior y el actual separador
				resultado[substrings_counter]=
						string.substring(start_index, next_separator_index);
				// Actualiza el puntero de proceso
				start_index=next_separator_index+SEPARATOR.length();
				// Actualiza la cuenta
				substrings_counter++;					
			} else { // No hay más separadores, coge la última substring y FIN.
				flag_fin=true;
				resultado[substrings_counter]=string.substring(start_index,
																string.length());
				substrings_counter++;
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
		for (int i=0; i<vector.length;i++) {
			if (vector[i] !=  null) {
				str.append(vector[i] + SEPARATOR);
			}
		}

		// Devuelve la String generada eliminando el último separator		
		return str.substring(0, str.lastIndexOf(SEPARATOR) );		
	}

}
