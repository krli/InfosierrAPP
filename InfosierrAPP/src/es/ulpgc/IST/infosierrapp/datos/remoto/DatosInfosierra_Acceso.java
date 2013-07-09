package es.ulpgc.IST.infosierrapp.datos.remoto;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.IST.infosierrapp.datos.Anuncio;


/**
 * Clase DAO (Data Access Object) (o Facade) que gestionará
 * el acceso a los datos de la página web: abstrae a la aplicación
 * de si estos se almacenan en una BD o se obtienen mediante parseo
 * de html. Devolverá los datos en objetos java (Anuncio).
 * 
 * @author krlo
 */
public class DatosInfosierra_Acceso {

	public DatosInfosierra_Acceso() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Realiza una búsqueda de anuncios basada en las
	 * etiquetas y devuelve una lista con los resultados
	 * 
	 * @param cadena 
	 * @return resultados de la búsqueda
	 */
	public List<Anuncio> buscar(String cadena) {
		// Lista a devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();
		
		
		
	
		return anuncios;
	}
	
	
	
	

}
