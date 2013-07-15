package es.ulpgc.IST.infosierrapp.datos;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import android.database.Cursor;
import es.ulpgc.IST.infosierrapp.datos.local.TablaResultados;
import es.ulpgc.IST.infosierrapp.recursos.HerramientasStrings;


/**
 * Modelo de datos de los items de la aplicación.
 * 
 * @author krlo
 */
public class Anuncio implements Serializable {

	private static final long serialVersionUID = -1003039284438735824L;
	// valor para inicializar el campo ID
	public static final int NO_ID = 0;
	/**
	 *  Número máximo de etiquetas de un anuncio
	 */
	public static final int N_TAGS = 4;
	/**
	 *  Cantidad máxima de números de teléfono que
	 *  contiene un anuncio
	 */
	public static final int N_TLFS = 2;	
	/**
	 *  Valor por defecto para campos no
	 *  establecidos
	 */
	public static final String SIN_VALOR="<no establecido>";

	/**
	 * Nombre del negocio
	 */
	private String		_nombre;
	/**
	 * Dirección postal
	 */
	private String		_direccion;    
	/**
	 * Números de teléfono
	 * @see N_TLFS
	 */
	private String[]	_telefonos;  
	/**
	 * Dirección de correo electrónico
	 */
	private String		_email;
	/**
	 * URL a la página web.
	 */
	private String		_web;    
	/**
	 * Breve descripción de la actividad del negocio
	 */    
	private String		_descripcion;
	/**
	 * Etiquetas descriptivas que definen en qué búsquedas
	 * aparecerá el negocio
	 * @see Anuncio.N_TAGS
	 */
	private String[]	_tags;
	/**
	 * URL al fichero con la foto
	 */
	private String		_foto;    
	/**
	 * Coordenadas para GoogleMaps
	 */
	private double 		_X;
	private double 		_Y;
	/**
	 * Identificador único de item 
	 * (No tiene utilidad alguna, pero es
	 * necesario para la gestión de las BD, los
	 * Providers...etc.)
	 */
	private long		_id;



	/**
	 * Constructor vacío
	 */
	public Anuncio() {
		set_nombre(SIN_VALOR);
		set_direccion(SIN_VALOR);
		set_telefonos(new String[N_TLFS]);
		for(int i=0;i<N_TLFS;i++) {
			set_tlf(i, SIN_VALOR);
		}
		set_email(SIN_VALOR);
		set_web(SIN_VALOR);
		set_descripcion(SIN_VALOR);
		set_tags(new String[N_TAGS]);
		for(int i=0;i<N_TAGS;i++) {
			set_tag(i, SIN_VALOR);
		}
		set_foto(null);
		set_X(0);
		set_Y(0);
		set_id(NO_ID);
	}

	/**
	 * Constructor completo del item.
	 * 
	 * @param nombre
	 * @param direccion
	 * @param telefonos
	 * @param email
	 * @param web
	 * @param descripcion
	 * @param tags
	 * @param foto
	 * @param X
	 * @param Y
	 * @param id
	 */
	public Anuncio(String nombre, String direccion, String[] telefonos,
			String email, String web, String descripcion, String[] tags,
			String foto, double X, double Y, int id) {

		set_nombre(nombre);
		set_direccion(direccion);
		set_telefonos(telefonos);
		set_email(email);
		set_web(web);
		set_descripcion(descripcion);
		set_tags(tags);
		set_foto(foto);
		set_X(X);
		set_Y(Y);
		set_id(id);
	}
		
	/**
	 * Procesa una fila de la tabla Resultados 
	 * (un objeto Cursor) y lo convierte en un
	 * objeto Anuncio
	 */
	public static Anuncio cursorToAnuncio(Cursor cursor) {
		Anuncio anuncio = new Anuncio();

		// Para cada atributo:
		// 1. Obtiene el índice de la columna
		// 2. Extrae y establece el valor del atributo
		//------		
		int i_id = cursor.getColumnIndex(TablaResultados.COL_ID);
		anuncio.set_id(cursor.getLong(i_id));
		//------
		int i_nombre = cursor.getColumnIndex(TablaResultados.COL_NOMBRE);
		anuncio.set_nombre(cursor.getString(i_nombre));
		//------
		int i_dir = cursor.getColumnIndex(TablaResultados.COL_DIRECCION);
		anuncio.set_direccion(cursor.getString(i_dir));
		//------
		int i_desc = cursor.getColumnIndex(TablaResultados.COL_DESC);
		anuncio.set_descripcion(cursor.getString(i_desc));
		//------
		int i_email = cursor.getColumnIndex(TablaResultados.COL_EMAIL);
		anuncio.set_email(cursor.getString(i_email));
		//------
		int i_web = cursor.getColumnIndex(TablaResultados.COL_WEB);		
		anuncio.set_web(cursor.getString(i_web));
		//------
		int i_foto = cursor.getColumnIndex(TablaResultados.COL_FOTO);
		anuncio.set_foto((cursor.getString(i_foto)));
		//------
		int i_mapx = cursor.getColumnIndex(TablaResultados.COL_MAPX);		
		anuncio.set_X(cursor.getDouble(i_mapx));
		//------
		int i_mapy = cursor.getColumnIndex(TablaResultados.COL_MAPY);
		anuncio.set_Y(cursor.getDouble(i_mapy));
		//------
		int i_tlfs = cursor.getColumnIndex(TablaResultados.COL_TELEFONOS);
		anuncio.set_AllTelefonos(cursor.getString(i_tlfs));
		//------
		int i_tags = cursor.getColumnIndex(TablaResultados.COL_TAGS);
		anuncio.set_AllTags(cursor.getString(i_tags));

		return anuncio;
	}
	

	/* ******** GETTERS / SETTERS ******** */
	
	public String get_nombre() {
		return _nombre;
	}
	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	//***********************************
	public String get_direccion() {
		return _direccion;
	}
	public void set_direccion(String _direccion) {
		this._direccion = _direccion;
	}
	//***********************************
	public String get_tlf(int n) {
		if (( _telefonos != null ) && (n < _telefonos.length)) {
			return _telefonos[n];
		}
		return null;
	}
	public String[] get_telefonos() {
		return _telefonos;
	}
	public String get_AllTelefonos() {
		return HerramientasStrings.joinStrings(get_telefonos());
	}
	public void set_tlf(int n, String tlf) {
		if (n < N_TLFS) {
			_telefonos[n]=tlf;
		}
	}
	public void set_telefonos(String[] telefonos) {
		if ( telefonos != null ) {
			this._telefonos = Arrays.copyOf(telefonos, N_TLFS); 
		} else {
			this._telefonos = new String[N_TLFS];
		}
	}
	public void set_AllTelefonos(String tlfs) {
		set_telefonos( HerramientasStrings.splitString(tlfs, N_TLFS) );
	}
	//***********************************
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	//***********************************
	public String get_web() {
		return _web;
	}
	public URL get_web_URL() {		
		URL url;
		try {
			url=new URL(get_web());
		} catch (MalformedURLException e) {
			url=null;
		}
		return url;
	}
	public void set_web(String web) {
		this._web=web;
	}
	
	//***********************************
	public String get_descripcion() {
		return _descripcion;
	}
	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
	//***********************************
	public String get_tag(int n) {
		if (( _tags != null ) && (n < _tags.length)) {
			return _tags[n];
		}
		return null;
	}
	public String[] get_tags() {
		return _tags;
	}
	public String get_AllTags() {
		return HerramientasStrings.joinStrings(get_tags());
	}	
	public void set_tag(int n, String tag) {
		if (n < N_TAGS) {
			_tags[n]=tag;
		}
	}
	public void set_tags(String[] tags) {
		if ( tags != null ) {
			this._tags = Arrays.copyOf(tags, N_TAGS); 
		} else {
			this._tags = new String[N_TAGS];
		}
	}
	public void set_AllTags(String tags) {		
		set_tags( HerramientasStrings.splitString(tags, N_TAGS));
	}
	//***********************************
	public String get_foto() {
		return _foto;
	}
	public URL get_foto_URL() {		
		URL url;
		try {
			url=new URL(get_foto());
		} catch (MalformedURLException e) {
			url=null;
		}
		return url;
	}
	public void set_foto(String foto) {
		this._foto=foto;
	}
	
	//***********************************
	public double get_X() {
		return _X;
	}
	public void set_X(double X) {
		this._X = X;
	}
	public double get_Y() {
		return _Y;
	}
	public void set_Y(double Y) {
		this._Y = Y;
	}
	//***********************************
	public long get_id() {
		return _id;
	}
	public void set_id(long id) {
		this._id = id;
	}
	//************************************

	public String toString() {		
		return get_nombre() + "\n" + get_descripcion();
	}



}



