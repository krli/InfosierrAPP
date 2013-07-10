package es.ulpgc.IST.infosierrapp.recursos;

/******************* Clase HistorialBusquedas *****************/
/**
 * Clase que implementa un historial para
 * almacenar un número fijo de cadenas. Cuando está
 * lleno y se guardan más cadenas va borrando
 * las más viejas.
 *  
 */
public class HistorialBusquedas {
	
	/**
	 * Vector almacén
	 */
	private String[] history;
	
	/**
	 * Índice de la posición para guardar la
	 * próxima cadena
	 */
	private int _pointer;
	
	/**
	 * Constructor
	 * @param size tamaño del historial
	 */
	public HistorialBusquedas(int size) {
		history=new String[size];
		_pointer=0;
	}	
	
	/**
	 * Incrementa el índice que indica la posición
	 * para la próxima cadena almacenada. Vuelve a 0
	 * cuando llega al final del vector.
	 */
	private void increPointer() {
		_pointer++;
		if (getPointer() >= history.length) {
			_pointer=0;
		}
	}
	/**
	 * Devuelve la posición para guardar la próxima búsqueda 
	 */
	private int getPointer(){
		return _pointer;
	}
	/**
	 * Devuelve la última String añadida 
	 */
	public String getLast() {		
		return history[getLastIndex()];
	}
	/**
	 * Devuelve la posición de la última búsqueda guardada 
	 */
	private int getLastIndex() {
		int last = getPointer()-1;
		if (last < 0) {
			last = history.length - 1;
		}
		return last;
	}
	
	/**
	 * Añade una cadena al historial
	 */
	public void add(String cadena) {			
		history[ getPointer() ] = cadena;		
		increPointer();
	}		
	/**
	 * Borra todo el historial
	 */
	public void clearHistory() {
		history = new String[history.length];
		_pointer=0;
	}
	/**
	 * Devuelve todo el historial
	 */
	public String[] getHistory() {
		return history;
	}		
	/**
	 * Devuelve las últimas N cadenas añadidas. Si N
	 * es mayor que el tamaño del historial se devuelve
	 * se ignora y se devuelve un vector del tamaño 
	 * del historial
	 *  
	 * @param N número de Strings que se quieren recuperar
	 * @return vector de búsquedas ordenadas desde la más reciente
	 */
	public String[] getLastN(int N) {			
		// Vector respuesta
		String[] queries;			
		// Calcula tamaño de queries
		if (N > history.length) {
			queries = new String[history.length];
		} else {
			queries = new String[N];	
		}			
		
		// Rellena queries con las últimas búsquedas:
		// desde LastIndex hacia atrás
		int index = getLastIndex();
		for(int k=0; k < queries.length; k++) {
			
			queries[k]=history[index];				
			// Actualiza index (vuelve al final cuando llega a 0)
			index--;
			if (index < 0 ) {
				index=history.length -1;
			}
		}			
		return queries;
	}
	
}

