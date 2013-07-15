package es.ulpgc.IST.infosierrapp.recursos;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Vector;

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
	private Vector<String> historial;

	/**
	 * Tamaño máximo del historial
	 */
	private int max_size;

	/**
	 * Constructor
	 * @param size tamaño del historial
	 */
	public HistorialBusquedas(int max_size) {
		this.historial=new Vector<String>(max_size, 1);
		this.max_size=max_size;
	}	

	/**
	 * Devuelve la cantidad de entradas que CABEN en el
	 * historial (=parámetro max_size del constructor)
	 */
	public int getCapacity() {
		return historial.capacity();
	}

	/**
	 * Devuelve el número de entradas que HAY
	 * el historial
	 */
	public int getSize() {
		return historial.size();
	}

	/**
	 * Devuelve todo el historial
	 */
	public String[] getHistorial() {		
		return historial.toArray( new String[getSize()]);
	}	

	/**
	 * Devuelve la última String añadida.
	 * Null si el historial está vacío.
	 */
	public String getLast() {
		String last = null;
		try {
			last = historial.firstElement();
		} catch(NoSuchElementException e) {
			// Historial vacío
		}		
		return last;
	}

	/**
	 * Devuelve las últimas N cadenas añadidas. Si N
	 * es mayor que el tamaño del historial o no hay 
	 * tantas entradas se rellena con null: siempre se
	 * devolverá una vector de tamaño N, excepto si
	 * N es negativo o cero, entonces se devuelve null.
	 *  
	 * @param N número de Strings que se quieren recuperar
	 * @return vector de búsquedas ordenadas desde la más reciente
	 */
	public String[] getLastN(int N) {
		
		if ( N <= 0 ) {
			return null;
		}

		String[] queries;
		try {
			// Rellena con entradas del historial
			// y null hasta tamaño N 
			queries = Arrays.copyOf(getHistorial(), N);
		} catch (NullPointerException e) {
			// Historial vacío:
			// queries = vector de tamaño N vacío
			queries = new String[N];
		}		
		return queries;
	}

	/**
	 * Borra todo el historial
	 */
	public void limpiarHistorial() {
		historial.clear();
	}

	/**
	 * Añade una cadena al historial. Borra la entrada más antigua
	 * si se va a exceder el tamaño máximo definido. Borra también
	 * la entrada antigua si la cadena ya existía y la añade en 
	 * última posición.
	 */
	public void add(String cadena) {

		/* Si cadena es null no se hace nada */
		if (cadena==null) {
			return;
		}

		/* Si ya estaba en el historial borra la entrada
		 antigua y la añade de nuevo en la primera posición */
		int index = historial.indexOf( cadena );
		if ( index >= 0) {
			historial.remove( index );
			historial.insertElementAt(cadena, 0);			
			return;
		}

		/* Si no estaba en el historial... */		
		// Si está lleno borra la entrada más antigua
		if ( getSize() >= max_size ) {			
			historial.remove( getSize() -1 );
		}
		// Y añade
		historial.insertElementAt(cadena, 0);
	}
}

