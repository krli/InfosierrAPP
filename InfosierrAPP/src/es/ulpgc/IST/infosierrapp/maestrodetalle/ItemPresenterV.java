package es.ulpgc.IST.infosierrapp.maestrodetalle;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import es.ulpgc.IST.infosierrapp.R;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;
import es.ulpgc.IST.infosierrapp.main.PresentadorH_main;
import es.ulpgc.IST.infosierrapp.main.PresentadorV_main;

/**
 * Presentador asignado a cada anuncio
 * Muestra la disposicion de la informacion
 *
 */
public class ItemPresenterV extends FragmentActivity implements OnClickListener{

	private EditText nombre;
	private EditText direccion;
	private EditText telefono;
	private EditText email;
	private EditText descripcion;
	private EditText web;
	private ImageView image;
	protected Anuncio anuncio;
	private String action;
	private Button b_mapa;

	//coordenadas
	private double X;
	private double Y;



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Carga layout
		loadView();

		nombre = (EditText)findViewById(R.id.txtNombre);
		direccion = (EditText)findViewById(R.id.txtDireccion);
		telefono = (EditText)findViewById(R.id.txtTelefono);
		email = (EditText)findViewById(R.id.txtEmail);
		web = (EditText)findViewById(R.id.txtWeb);
		descripcion = (EditText)findViewById(R.id.txtDescripcion);
		image = (ImageView)findViewById(R.id.image);
		b_mapa=(Button)findViewById(R.id.B_map);

		b_mapa.setOnClickListener(this);


		// Extrae el anuncio desde el item
		Intent intent = getIntent();
		anuncio = (Anuncio)intent.getSerializableExtra("anuncio");

		// Establece los valores de las coordenadas y la foto
		String foto = anuncio.get_foto();
		X = anuncio.get_X();
		Y = anuncio.get_Y();

		//Y asigna el resto a los EditText correspondientes
		this.nombre.setText(		anuncio.get_nombre());
		this.descripcion.setText(	anuncio.get_descripcion());
		this.direccion.setText(		anuncio.get_email());
		this.email.setText(			anuncio.get_email());
		this.web.setText(			anuncio.get_web());
		this.telefono.setText(		anuncio.get_tlf(0) );

		//Si se ha introducido una direccion con la foto, se inicia su descarga en 2o plano

		if (foto.length()>0)
		{
			image.setTag(foto);
			new DownloadImagesTask().execute(image);
		}


		//SI USAMOS UN CONTENT PROVIDER//

		//		Uri uri = getIntent().getData();
		//		Cursor cursor = managedQuery(uri, null, null, null, null);
		//
		//		if (cursor == null) {
		//			finish();
		//		} else {
		//			cursor.moveToFirst();
		//
		//			
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
	 * onCreate() -> onStart() -> onResume() -> Actividad
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// Verifica la orientación
		checkOrientation();
	}
	
	/**
	 * Se llamará si hay algún cambio en las configuraciones del teléfono, como
	 * por ejemplo, un cambio de orientación.
	 */
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Verifica la orientación
        checkOrientation();    
    }
	
	/* *************************************************
	 * Los siguientes 4 métodos gestionan el cambio de 
	 * layout con la orientación. Los 3 primeros debenrán
	 * ser sobreescritos por el presentador que herede
	 * ************************************************* */
	
	/**
	 * Verifica que la orientación del dispositivo concuerda con
	 * la del presentador en uso. Si no es así fuerza el cambio
	 * al otro presentador.
	 */
    protected void checkOrientation() {      
      Configuration config = getResources().getConfiguration();
      if (config.orientation != Configuration.ORIENTATION_PORTRAIT) {
      	changePresenter();
      } 
    }

    /**
     * Devuelve un intent para cambiar al presentador que
     * corresponda:
     * PresenV --> PresenH
     * PresenH --> PresenV 
     * @return
     */
    protected Intent getIntentForChangePresenter() {
    	// Destino: Presentador H
    	Intent intent = new Intent(ItemPresenterV.this,
    			ItemPresenterH.class);
		// Guarda en el intent el anuncio 
		intent.putExtra("anuncio", anuncio);
    	return intent;
    }
    /**
     * Carga el layout.
     */
    protected void loadView() {
        setContentView(R.layout.detalle_vista_v);
    }
    /**
     * Cambia de presentador
     */
    protected void changePresenter() {
    	// Get the next Controller
    	Intent intent = getIntentForChangePresenter();    	
    	// Start the next and finish the current Controller
    	startActivity(intent);
    	finish();
    }
    
    /* ************************************************* */



	/**
	 * Cambia a la actividad que muestra 
	 * el mapa
	 */
	private void goToMap(){
		Intent intent = new Intent(ItemPresenterV.this,
				Presentador_map.class);   
		//Le pasa al Presentador del mapa el nombre y las coordenadas del anuncio
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

		case R.id.menu_llamar:
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:"+this.telefono.getText()));
			startActivity(intent);
			finish();
			return true;
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
			emailintent.putExtra(Intent.EXTRA_EMAIL,""+this.email.getText());
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
