package es.ulpgc.IST.infosierrapp.datos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class BD_resultadosCursorAdapter extends CursorAdapter
{
	private BD_resultadosAdapter dbAdapter = null ;
	public BD_resultadosCursorAdapter(Context context, Cursor c){
		super(context, c);
		dbAdapter = new BD_resultadosAdapter(context);
		dbAdapter.abrir();
	}
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView tv = (TextView) view ;
		tv.setText(cursor.getString(cursor.getColumnIndex(BD_resultadosAdapter.C_COLUMNA_NOMBRE)));
	}
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent){
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
		return view;
	}
}