package es.ulpgc.IST.infosierrapp.maestrodetalle;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;

/**
 * Presentador asignado a cada anuncio
 * Muestra la disposicion de la informacion
 * @author jesus
 *
 */
public class ItemPresenter extends Activity implements OnClickListener{

	private EditText nombre;
	private EditText direccion;
	private EditText telefono;
	private EditText email;
	private EditText descripcion;
	private EditText web;
	private TextView pos;
	
	
	private ImageView image;

	private Anuncio anuncio;
	private String action;

	private Button b_mapa;


	//coordenadas
	private double X;
	private double Y;



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_v_detalle);



		pos = (TextView)findViewById(R.id.lblPos);
		nombre = (EditText)findViewById(R.id.txtNombre);
		direccion = (EditText)findViewById(R.id.txtDireccion);
		telefono = (EditText)findViewById(R.id.txtTelefono);
		email = (EditText)findViewById(R.id.txtEmail);
		web = (EditText)findViewById(R.id.txtWeb);
		descripcion = (EditText)findViewById(R.id.txtDescripcion);
		
		image = (ImageView)findViewById(R.id.image);


		b_mapa=(Button)findViewById(R.id.B_map);
		
		b_mapa.setOnClickListener(this);




		Intent intent = getIntent();
		anuncio = (Anuncio)intent.getSerializableExtra(Intent.ACTION_EDIT);
		String nombre = getIntent().getStringExtra("nombre");
		String descripcion = getIntent().getStringExtra("descripcion");
		String direccion = getIntent().getStringExtra("direccion");
		String email = getIntent().getStringExtra("email");
		String web = getIntent().getStringExtra("web");
		String telefono = getIntent().getStringExtra("telefono");
		String foto = getIntent().getStringExtra("foto");
		X = getIntent().getDoubleExtra("mapx",0);
		Y = getIntent().getDoubleExtra("mapy",0);




		this.nombre.setText(nombre);
		this.descripcion.setText(descripcion);
		this.direccion.setText(direccion);
		this.email.setText(email);
		this.web.setText(web);
		this.telefono.setText(telefono);
		


		
		if (foto.length()>0)
        {
			Toast.makeText(this, "descargando imagen...", Toast.LENGTH_SHORT).show();

			image.setTag(foto);
			new DownloadImagesTask().execute(image);
        }




		//CON CONTENT PROVIDER//

		//		Uri uri = getIntent().getData();
		//		Cursor cursor = managedQuery(uri, null, null, null, null);
		//
		//		if (cursor == null) {
		//			finish();
		//		} else {
		//			cursor.moveToFirst();
		//
		//			//TODO: meter imagenes
		//			TextView nombre = (TextView) findViewById(R.id.txtNombre);
		//			// TextView etiquetas = (TextView) findViewById(R.id.txtEtiquetas);
		//			TextView direccion = (TextView) findViewById(R.id.txtDireccion);
		//			TextView telefono = (TextView) findViewById(R.id.txtTelefono);
		//			TextView email = (TextView) findViewById(R.id.txtEmail);
		//			TextView web = (TextView) findViewById(R.id.txtWeb);
		//			TextView descripcion = (TextView) findViewById(R.id.txtDescripcion);
		//			//            TextView posX = (TextView) findViewById(R.id.posX);
		//			//            TextView posY = (TextView) findViewById(R.id.posY);
		//
		//
		//			int aIndex = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_NOMBRE);
		//			// int bIndex = cursor.getColumnIndexOrThrow(BD_infosierra.KEY_ETIQUETAS);
		//			int cIndex = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_DIRECCION);
		//			int dIndex = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_TELEFONOS);
		//			int eIndex = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_EMAIL);
		//			int fIndex = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_WEB);
		//			int gIndex = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_DESC);
		//			X = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_MAPX);
		//			Y = cursor.getColumnIndexOrThrow(BD_local_SQLiteHelper.COL_MAPY);
		//
		//
		//			nombre.setText(cursor.getString(aIndex));
		//			// etiquetas.setText(cursor.getString(bIndex));
		//			direccion.setText(cursor.getString(cIndex));
		//			telefono.setText(cursor.getString(dIndex));
		//			email.setText(cursor.getString(eIndex));
		//			web.setText(cursor.getString(fIndex));
		//			descripcion.setText(cursor.getString(gIndex));
		//			//            posX.setText(cursor.getString(hIndex));
		//			//            posY.setText(cursor.getString(iIndex));
		//

	}





	/**
	 * Cambia a la actividad que muestra 
	 * el widget del tiempo
	 */
	private void goToMap(){
		Intent intent = new Intent(ItemPresenter.this,
				Presentador_map.class);   
		/*intent.putExtra("nombre", this.nombre.getText());
		intent.putExtra("X", this.X);
		intent.putExtra("Y", this.Y);*/
		startActivity(intent);   	
	}


	@Override
	public void finish() {
		Intent intent = new Intent();
		intent.putExtra(action, anuncio);
		setResult(RESULT_OK, intent);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail_menu, menu);
		return true;
	}


	/**
	 * Reacciona a los eventos de click
	 */
	@Override
	public void onClick(View view) {
		int btn = view.getId();
		switch (btn) {

		case R.id.B_map:
			goToMap();
			break;   
		}
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
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(anuncio.get_tlf(0)));
			startActivity(intent);
			finish();
			return true;
			//            case R.id.menu_delete:
			//                action = Intent.ACTION_DELETE;
			//                finish();
			//                return true;
		case R.id.menu_compartir:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Te recomiendo visitar "+this.nombre.getText());
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			return true;

		case R.id.menu_email:
			Intent emailintent = new Intent(Intent.ACTION_SEND);
			emailintent.setType("plain/text");
			emailintent.putExtra(Intent.EXTRA_EMAIL,this.email.getText());
			emailintent.putExtra(Intent.EXTRA_SUBJECT, "RECOMENDACION INFOSIERRA");
			emailintent.putExtra(Intent.EXTRA_TEXT, "Te recomiendo visitar "+this.nombre.getText());
			startActivity(Intent.createChooser(emailintent, "Envialo por email..."));
			return true;

		case R.id.menu_back:
			action = Intent.ACTION_DEFAULT;
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/** 
	 * Clase que se encarga de ejecutar la descarga de la foto
	 * @author Usuario
	 *
	 */
	
	public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

	    ImageView imageView = null;

	    @Override
	    protected Bitmap doInBackground(ImageView... imageViews) {
	        this.imageView = imageViews[0];
	        return download_Image((String)imageView.getTag());
	    }

	    @Override
	    protected void onPostExecute(Bitmap result) {
	        imageView.setImageBitmap(result);
	    }

	    private Bitmap download_Image(String url) {

	        Bitmap bmp =null;
	        try{
	            URL ulrn = new URL(url);
	            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
	            InputStream is = con.getInputStream();
	            bmp = BitmapFactory.decodeStream(is);
	            if (null != bmp)
	                return bmp;

	            }catch(Exception e){}
	        return bmp;
	    }
	}
	
	



}