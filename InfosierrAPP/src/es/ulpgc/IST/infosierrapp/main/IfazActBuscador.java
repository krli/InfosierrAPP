package es.ulpgc.IST.infosierrapp.main;

public interface IfazActBuscador {
	
	public final int MAX_PROGRESS=100;
	public final int MIN_PROGRESS=0;
	
	/**
	 * Notifica a la actividad que la búsqueda ha comenzado
	 */
	public void busquedaIniciada();
	
	/**
	 * Notifica a la actividad que la búsqueda ha finalizado
	 * @return true si se han encontrado resultados
	 */
	public void busquedaFinalizada(boolean result);
	
	/**
	 * Notifica a la actividad que la búsqueda se ha cancelado
	 */
	public void busquedaCancelada();
	
	/**
	 * Informa a la actividad del progreso de la búsqueda
	 */
	public void progresoBusqueda(int progreso);

}
