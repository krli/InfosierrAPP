package es.ulpgc.IST.infosierrapp.maestrodetalle;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import android.content.AsyncTaskLoader;
import android.content.Context;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;


public class CursorLoader extends AsyncTaskLoader<List<Anuncio>> {


	// Mantenemos una referencia a los datos del Loader
	private List<Anuncio> anuncios;

	public CursorLoader(Context ctx) {

		super(ctx);
	}


	/****************************************************/
	/** (1) A task that performs the asynchronous load **/
	/****************************************************/

	@Override
	public List<Anuncio> loadInBackground() {

		// This method is called on a background thread and should generate a
		// new set of data to be delivered back to the client.
		// Este metodo es llamado en un hilo background y debe generar
		// una nueva Lista de anuncios para devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();

		// TODO: Perform the query here and add the results to 'data'.


		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			temp.add("Counting-----" + i);
		}
		try {
			synchronized (this) {
				wait(5000);
			}
		} catch (Exception e) {
			e.getMessage();
		}


		return anuncios;

	}

	/********************************************************/
	/** (2) Deliver the results to the registered listener **/
	/********************************************************/

	@Override
	public void deliverResult(List<Anuncio> data) {

		if (isReset()) {
			// Si el loader ha sido reseteado ignora el resultado
			releaseResources(data);
			return;
		}


		// Mantiene una referencia a la antiua data hasta que la nueva sea enviada
		List<Anuncio> oldData = anuncios;
		anuncios = data;

		if (isStarted()) {
			// Si el Loader esta en estado 'started', envia los resultados
			super.deliverResult(data);
		}

		// Ya se puede invalidar la antigua data.
		if (oldData != null && oldData != data) {
			releaseResources(oldData);
		}
	}


	/*********************************************************/
	/** (3) Implement the Loader’s state-dependent behavior **/
	/*********************************************************/

	@Override
	protected void onStartLoading() {

		if (anuncios != null) {
			// Envia cualquier data cargada anteriormente
			deliverResult(anuncios);
		}

		// Begin monitoring the underlying data source.
		if (mObserver == null) {
			//	mObserver = new Observer();
			// TODO: register the observer
		}

		if (takeContentChanged() || anuncios == null) {
			// Cuando el observador detecta un cambio fuerza una nueva carga
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {

		// El loader esta en estado 'stopped'. Cancelamos la carga actual
		cancelLoad();

	}

	@Override
	protected void onReset() {

		// Aseguramos que el Loader esta stop
		onStopLoading();

		// At this point we can release the resources associated with 'mData'.
		if (anuncios != null) {
			releaseResources(anuncios);
			anuncios = null;
		}

		// The Loader is being reset, so we should stop monitoring for changes.
		if (mObserver != null) {
			// TODO: unregister the observer
			mObserver = null;
		}
	}

	@Override
	public void onCanceled(List<Anuncio> data) {

		// Cancela la actual carga
		super.onCanceled(data);

		// The load has been canceled, so we should release the resources
		// associated with 'data'.
		// La carga ha sido cancelada, debemos release los recursos asociados a data
		releaseResources(data);
	}

	private void releaseResources(List<Anuncio> data) {


		// Para una liste simple no hace falta hacer nada. Si cursor-> cerrar cursor
	}


	/*********************************************************************/
	/** (4) Observer which receives notifications when the data changes **/
	/*********************************************************************/

	// NOTE: Implementing an observer is outside the scope of this post (this example
	// uses a made-up "SampleObserver" to illustrate when/where the observer should 
	// be initialized). 

	// The observer could be anything so long as it is able to detect content changes
	// and report them to the loader with a call to onContentChanged(). For example,
	// if you were writing a Loader which loads a list of all installed applications
	// on the device, the observer could be a BroadcastReceiver that listens for the
	// ACTION_PACKAGE_ADDED intent, and calls onContentChanged() on the particular 
	// Loader whenever the receiver detects that a new application has been installed.
	// Please don’t hesitate to leave a comment if you still find this confusing! :)
	private Observer mObserver;

}

