/**
 * 
 */
package es.ulpgc.IST.infosierrapp.datos.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Un ContentProvider es una interfaz para el acceso a una base de datos.
 * Proporciona una serie de métodos que pueden ser usados por otras
 * actividades para trabajar con la BD.
 * Es "equivalente" al DAO pero estandarizado en Android y más eficiente.
 * Puede ser utilizado por el S.O para las sugerencias de búsqueda
 * en un SearchView, etc...
 * 
 * @author krlo
 *
 */
public class BD_local_ContentProvider extends ContentProvider {

	private BD_local_SQLiteHelper helper;
	
	public static String AUTHORITY = "es.ulpgc.IST.infosierrapp.local.BD_local_ContentProvider";

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("No se soporta la inserción de datos");
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		helper = BD_local_SQLiteHelper.getHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
