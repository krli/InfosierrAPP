package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.Anuncio;


public class Modelo_buscador {
	
	// Singleton Pattern
	private static Modelo_buscador instance = new Modelo_buscador();
	
	//TODO Adaptar el tipo a la lista adecuada 
	private Anuncio resultados[];

	private Modelo_buscador() {
		// TODO Auto-generated constructor stub
	}
	
	public static Modelo_buscador getModel() {
	  	return instance;
	}
	
	/**
	 * 
	 * @param cadena patrón para la búsqueda
	 */
	public void buscar(String cadena) {
		
	}
	
	/**
	 * @return los datos almacenados
	 */
	public Anuncio[] getResultados() {
		return resultados;
	}
	
}
