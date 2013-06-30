package es.ulpgc.IST.infosierrapp.main;

import es.ulpgc.IST.infosierrapp.datos.Anuncio;


public class Modelo_main {
	
	// Singleton Pattern
	private static Modelo_main instance = new Modelo_main();
	
	//TODO Adaptar el tipo a la lista adecuada 
	private Anuncio resultados[];

	private Modelo_main() {
		// TODO Auto-generated constructor stub
	}
	
	public static Modelo_main getModel() {
	  	return instance;
	}
	
	
	
	/**
	 * 
	 * @param cadena patr��n para la b��squeda
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
