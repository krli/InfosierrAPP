package es.ulpgc.IST.infosierrapp.maestrodetalle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;

/**
 * Presentador asignado a cada anuncio
 * Muestra la disposicion de la informacion
 * @author jesus
 *
 */
public class ItemPresenter extends FragmentActivity  {

    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private EditText email;
    private EditText descripcion;
    private EditText web;
    private TextView pos;

    private Anuncio anuncio;
    private String action;
    
    private GoogleMap mMap;
    //coordenadas
    private int X;
    private int Y;

    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_v_detalle);
        setUpMapIfNeeded();

        pos = (TextView)findViewById(R.id.lblPos);
        nombre = (EditText)findViewById(R.id.txtNombre);
        direccion = (EditText)findViewById(R.id.txtDireccion);
        telefono = (EditText)findViewById(R.id.txtTelefono);
        email = (EditText)findViewById(R.id.txtEmail);
        web = (EditText)findViewById(R.id.txtWeb);
        descripcion = (EditText)findViewById(R.id.txtDescripcion);
       



        Intent intent = getIntent();
        anuncio = (Anuncio)intent.getSerializableExtra(Intent.ACTION_EDIT);

        pos.setText(anuncio.getPos());

        nombre.setText(anuncio.getNombre());
        direccion.setText(anuncio.getDireccion());
        telefono.setText(anuncio.getTelefono());
        email.setText(anuncio.getEmail());
        web.setText(anuncio.getWeb());
        descripcion.setText(anuncio.getDescripcion());
        X=(anuncio.getX());
        Y=(anuncio.getY());


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    
    /**
     * Intenta abrir el mapa
     */
    private void setUpMapIfNeeded() {
    	// Si el nMap esta null entonces es porque no se instancio el mapa.
        if (mMap == null) {
        	// Intenta obtener el mapa del SupportMapFragment. 
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Comprueba si hemos tenido exito en la obtencion del mapa.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    
   /**
    * Coloca marcador en la posicion del anuncio
    */
   
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(X, Y)).title("Marker"));
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        //intent.putExtra(action, anuncio);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_save:
//                model.setNombre(nombre.getText().toString());
//                model.setDireccion(direccion.getText().toString());
//                model.setTelefono(telefono.getText().toString());
//                model.setEmail(email.getText().toString());
//                model.setDescripcion(descripcion.getText().toString());
//                action = Intent.ACTION_EDIT;
//                finish();
//                return true;
            case R.id.menu_llamar:
            	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(anuncio.getTelefono()));
            	startActivity(intent);
            	finish();
            	return true;
//            case R.id.menu_delete:
//                action = Intent.ACTION_DELETE;
//                finish();
//                return true;
            case R.id.menu_back:
                action = Intent.ACTION_DEFAULT;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}