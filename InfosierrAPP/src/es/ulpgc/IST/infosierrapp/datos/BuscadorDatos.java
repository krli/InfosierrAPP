package es.ulpgc.IST.infosierrapp.datos;


/**
 * Clase para gestionar las b��squedas y el
 * almacenamiento local de los resultados usando
 * las clases BD_*. Ser�� la interfaz para todas
 * las actividades de la APP.
 * 
 * @author krlo
 *
 */
public class BuscadorDatos {

	// Singleton Pattern
	private static BuscadorDatos instance = new BuscadorDatos();


	private BuscadorDatos() {
		// TODO Auto-generated constructor stub
	}

	public static BuscadorDatos getBuscador() {
		return instance;
	}
	
	

	/**
	 * Busca en la base de datos completa (BD_infosierra)
	 * y almacena los resultados en local (BD_resultados)
	 * 
	 * @param cadena patr��n para la b��squeda
	 */
	public void buscar(String cadena) {
		
		// 0. Comprobar que no es la misma b��squeda anterior,
		// y si lo es, omitir los pasos siguientes
		// 1. Busca en BD_infosierra
		// 2. Guarda en BD_resultados

	}
	public void buscar(String cadena, int cp) {
		// otros m��todos para b��squedas avanzadas
	}

	/**
	 * Devuelve los resultados que se mantienen de la ��ltima
	 * b��squeda en local (BD_resultados). Digamos que traduce
	 * del tipo en que se guarden en la BD al tipo de objetos
	 * que usamos en nuestra APP
	 * 
	 * @return los datos almacenados
	 */
	//TODO: definir el tipo que usaremos para esta lista
	// (ArrayList, Vector...) que sea m��s ��ptimo
	public static  Anuncio[] getResultados() {
		Anuncio[] resultados = new Anuncio[3];
		
		return resultados;
	}
	
	/**
	 * ���� Devuelve los resultados que se mantienen de la ��ltima
	 * b��squeda en local (BD_resultados) UNO a UNO. ??
	 *  
	 * @return los datos almacenados
	 */

	public  Anuncio getResultadosUnoAuno() {
		Anuncio resultados = new Anuncio();
		
		return resultados;
	}


}
