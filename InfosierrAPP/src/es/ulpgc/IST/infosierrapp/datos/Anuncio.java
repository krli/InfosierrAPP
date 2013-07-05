package es.ulpgc.IST.infosierrapp.datos;

import java.net.URL;
import java.util.Arrays;


/**
 * Modelo de datos de los items de la aplicación.
 * 
 * @author krlo
 */
public class Anuncio {

    public static final int NO_ID = 0;
    // Número de etiquetas para un anuncio
    public static final int N_TAGS = 4;
    // Números de teléfono
    public static final int N_TLFS = 2;
        
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
     */
    private String[]	_telefonos;  
    /**
     * Dirección de correo electrónico
     */
    private String		_email;
    /**
     * URL a la página web.
     */
    private URL			_web;    
    /**
     * Breve descripción de la actividad del negocio
     */    
    private String		_descripcion;
    /**
     * Etiquetas descriptivas que definen en qué búsquedas
     * aparecerá el negocio
     */
    private String[]	_tags;
    /**
     * URL al fichero con la foto
     */
    private URL			_foto;    
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
    private int			_id;

    
    
    /**
     * Constructor vacío
     */
    public Anuncio() {
		set_nombre("");
		set_direccion("");
		set_telefonos(null);
		set_email("");
		set_web(null);
		set_descripcion("");
		set_tags(null);
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
			String email, URL web, String descripcion, String[] tags,
			URL foto, double X, double Y, int id) {

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

    /********* GETTERS / SETTERS *********/
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
	public String[] get_telefonos() {
		return _telefonos;
	}
	public String get_tlf() {
		if ( _telefonos != null ) {
			return _telefonos[0];
		}
		return null;
	}
	public void set_tlf(String tlf) {
		_telefonos[0]=tlf;
	}
	public void set_telefonos(String[] telefonos) {
		if ( telefonos != null ) {
			this._telefonos = Arrays.copyOf(telefonos, N_TLFS); 
		} else {
			this._telefonos = new String[N_TLFS];
		}
	}
	//***********************************
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	//***********************************
	public URL get_web() {
		return _web;
	}
	public void set_web(URL _web) {
		this._web = _web;
	}
	//***********************************
	public String get_descripcion() {
		return _descripcion;
	}
	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
	//***********************************
	public String[] get_tags() {
		return _tags;
	}
	public String get_tag(int n) {
		if (n < N_TAGS) {
			return _tags[n];
		}
		return null;
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
	//***********************************
	public URL get_foto() {
		return _foto;
	}
	public void set_foto(URL _foto) {
		this._foto = _foto;
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
	public int get_id() {
		return _id;
	}
	public void set_id(int id) {
		this._id = id;
	}

}
