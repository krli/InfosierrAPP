package es.ulpgc.IST.infosierrapp.datos;

public interface ListenerTareaAsync {
	
	public final int MAX_PROGRESS=100;
	public final int MIN_PROGRESS=0;
	
	/**
	 * Notifica a la actividad que la búsqueda ha comenzado
	 */
	public void tareaIniciada();
	
	/**
	 * Notifica a la actividad que la búsqueda ha finalizado
	 * @return true si se han encontrado resultados
	 */
	public void tareaFinalizada(boolean result);
	
	/**
	 * Notifica a la actividad que la búsqueda se ha cancelado
	 */
	public void tareaCancelada();
	
	/**
	 * Informa a la actividad del progreso de la búsqueda
	 */
	public void progresoTarea(int progreso);

}
