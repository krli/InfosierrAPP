package es.ulpgc.IST.infosierrapp.datos;

import java.util.List;
import android.content.Context;
import android.database.Cursor;
import es.ulpgc.IST.infosierrapp.datos.local.BD_local_Acceso;
import es.ulpgc.IST.infosierrapp.recursos.HerramientasStrings;
import es.ulpgc.IST.infosierrapp.recursos.HistorialBusquedas;


/**
 * Clase para gestionar las búsquedas y el
 * almacenamiento local de los resultados usando
 * las clases BD_*. Será la interfaz para todas
 * las actividades de la APP.
 * 
 * 
 * @author krlo
 *
 */
public class BuscadorDatos {


	/**
	 * Historial con las última búsquedas realizadas
	 */
	private static final int 	HISTORIAL_SIZE = 10;
	private HistorialBusquedas 	historial;
	
	/**
	 * Cursor o lista con los resultados de última búsqueda
	 */
	private Cursor 			_resultados_cursor;
	private List<Anuncio> 	_resultados_lista;
	
	/**
	 * Cadena de búsqueda para los resultados almacenados
	 */
	private String			_resultados_string;

	/**
	 * Objeto singleton.		
	 */
	private static BuscadorDatos _instance = null;	
	
	/**
	 *  Conexión con base de datos local
	 */
	private BD_local_Acceso dbLocal;	
	
	/**
	 *  Conexón con base de datos remota 
	 */
	// private DatosInfosierra_Acceso dbRemota;
		
	
	// Constructor privado (usar getBuscador() )
	private BuscadorDatos(Context context) {
		
		/* Conexiones con BDs */
		dbLocal=new BD_local_Acceso(context);
		// TODO dbRemota=new DatosInfosierra_Acceso();
		
		/* Inicializa resultados */
		set_resultados_cursor(null);
		set_resultados_lista(null);
		set_resultados_string(null);
		
		/* Historial de búsquedas */
		historial = new HistorialBusquedas(HISTORIAL_SIZE);
		
		/* TODO
		 * Para probar sin BD remota: 
		 * Vaciamos la dbLocal y la rellenamos para pruebas
		 */
		dbLocal.borrarTablaResultados();		
		rellenaDbLocal();	
		
	}

	// "Constructor Singleton"
	public static BuscadorDatos getBuscador(Context context) {
		if(_instance == null){
			_instance = new BuscadorDatos(context);
		}
		return _instance;		
	}
	

	/**** GETTERS / SETTERS ****/
	public Cursor get_resultados_cursor() {		
		return this._resultados_cursor;
	}
	private void set_resultados_cursor(Cursor cursor) {
		this._resultados_cursor = cursor;
	}
	public List<Anuncio> get_resultados_lista() {
		return this._resultados_lista;
	}
	private void set_resultados_lista(List<Anuncio> lista) {
		this._resultados_lista = lista;
	}
	// Devuelve la última cadena buscada
	public String get_resultados_string() {
		return this._resultados_string;
	}
	private void set_resultados_string(String resultados_string) {
		this._resultados_string=resultados_string;
	}
	
	// Devuelve las últimas "num" entradas del historial
	public String[] get_historial(int num) {
		return historial.getLastN(num);
	}	
	public void add_to_historial(String cadena) {
		historial.add(cadena);
	}
	public void limpiar_historial() {
		historial.limpiarHistorial();
	}
	
	/**
	 * Busca en la base de datos completa (infosierra)
	 * y almacena los resultados en local (BD_resultados)
	 *  
	 * @param cadena patrón para la búsqueda
	 */
	public void buscar(String query_string) {
				
		/*
		 * 1. Comprobar si la búsqueda es la misma que la anterior
		 * almacenada. Si lo es omite los pasos siguientes.
		 */
		if (query_string == null) {
			return;
		}
		if ( query_string.equals( get_resultados_string()) ) {
			return;
		}
		
		/* 2. Limpia la búsqueda anterior */
		liberar_busqueda(); 
		
		/* 3. Actualiza el historial y la búsqueda actual */
		set_resultados_string(query_string);
		historial.add(query_string);
				
		/* 4.  Limpia la cadena para mejorar resultados */
		String query = HerramientasStrings.adaptarCadenaParaBusqueda(query_string);
		
		/* 5. Realiza la búsqueda en la DB Remota */
		//TODO
		
		/* 6. Guarda los resultados en la local */
		//TODO
				
		/* 
		 * TODO 
		 * Por ahora solo hacemos una búsqueda en la db local
		 */
		
		set_resultados_cursor( dbLocal.buscarEnTags_cursor(query) );
		// Si se trabaja con listas...
		// set_resultados_lista( dbLocal.buscarEnTags_lista(query) );
	}
	
	/**
	 * Cierra el cursor de resultados, vacía la lista
	 * y desconecta la base de datos
	 */
	public void liberar_busqueda() {
		
		if (get_resultados_cursor() != null) {
			get_resultados_cursor().close();
			set_resultados_cursor(null);
		}		
		if (get_resultados_lista() != null) {
			get_resultados_lista().clear();
			set_resultados_lista(null);
		}
		set_resultados_string(null);
		dbLocal.close_db();
	}
	
	/**
	 * TODO: borrar método y referencias cuando se 
	 * terminen las pruebas iniciales
	 * 
	 * Crea unos cuantos anuncios en la dbLocal.
	 */
	private void rellenaDbLocal() {

		///////////////
		//RESTAURANTES//
		///////////////
		Anuncio anuncio1 = new Anuncio();
		anuncio1.set_nombre("Casa Pepa Restaurante");
		anuncio1.set_descripcion("Desde aquí quiero presentarles el el Restaurante Casa Pepa. Inaugurado en el año 2000 lleva una década ofreciendo a las personas que pasan por aquí un servicio basado en la humildad en un trato cercano y familiar. Está ubicado en el casco antiguo del pueblo en uno de esos rincones tan pintorescos de la sierra donde se respira el silencio y la tranquilidad. A pocos metros pueden visitar la Plaza que posiblemente sea la mas hermosa de todo este entorno Serrano. LA PLAZA DE LA IGLESIA. Todos los platos que pueden degustar son de elaboración casera con excelentes productos autóctonos, desde los criados en las huertas hasta los productos silvestres (Setas, Tagazninas...) aunque la base principal sea el cerdo Ibérico (como pueden imaginar) acompañado por diferentes salsas o cremas. Casi toda nuestra variedad de platos pueden ser degustados desde tapas, raciones, pudiendo adecuar el plato al gusto del cliente. Desde aquí le invitamos que pasen a visitarnos y de camino aprovechen para conocer nuestro pueblo y sus costumbres que les causará una grata impresión. Gracias por visitar nuestra página y esperamos verles pronto por nuestra casa.");
		anuncio1.set_direccion("C/ Portales, 10. 21210 Zufre (Huelva)");
		anuncio1.set_email("frutu6@hotmail.com");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio1.set_X(37.833056);
		anuncio1.set_Y(-6.340833);
		anuncio1.set_tags(new String[]{"bar", "restaurante", "cafeteria"});
		anuncio1.set_telefonos(new String[]{"959198128", "680588108"});	
		anuncio1.set_foto("http://www.infosierra.es/images/galerias_sectores/91/1328862476-mini.jpg");
		dbLocal.insertAnuncio(anuncio1);

		Anuncio anuncio2 = new Anuncio();
		anuncio2.set_nombre("Bar Cafeteria el Bari");
		anuncio2.set_descripcion("Abierto todos los días de la semana. Cerveza y tapa por 1€. Sábados medio día fiesta para todo el día incluyendo comilona. Sábados noche, por tu copa un chupito. Cubatas 3,5");
		anuncio2.set_direccion("C/ San Juan Del Puerto, 50. 21290 Jabugo (Huelva)");
		anuncio2.set_email("bar-elbiri@hotmail.com");
		//anuncio2.set_web("http://www.miweb.com");
		anuncio2.set_X(6.474167);
		anuncio2.set_Y(37.893056);
		anuncio2.set_tags(new String[]{"bar", "restaurante", "cafeteria"});
		anuncio2.set_telefonos(new String[]{"639766842"});	
		anuncio2.set_foto("http://www.infosierra.es/images/galerias_sectores/38/cafeteria_elbiri_1-mini.jpg");
		dbLocal.insertAnuncio(anuncio2);

		Anuncio anuncio3 = new Anuncio();
		anuncio3.set_nombre("Mesón Restaurante el Kiosko");
		anuncio3.set_descripcion("Situado en Zufre en pleno Parque Natural Sierra de Aracena y Picos de Aroche, a 40 minutos de Sevilla. En nuestra cocina se pueden degustar platos típicos como las carrileras con seta y salsa agriculce, aliño de asadura con culantro, arroz caldoso de ibérico y setas. En temporada tenemos una gran variedad de setas a la plancha, en salsa y revueltos. Las carnes del cerdo ibérico ternera y cordero las pueden saborear a la brasa. El comedor cuenta con capacidad para 40 comensales ademas de una amplia terraza. Se hacen reservas, comidas para grupos.");
		anuncio3.set_direccion("Paseo de los Alcaldes S/N21210 Zufre (Huelva)");
		anuncio3.set_email("puertoja@gmail.com");
		//anuncio3.set_web("http://www.miweb.com");
		anuncio3.set_X(6.474167);
		anuncio3.set_Y(37.893056);
		anuncio3.set_tags(new String[]{"bar", "restaurante", "cafeteria"});
		anuncio3.set_telefonos(new String[]{"959198226","615204398" });	
		anuncio3.set_foto("http://www.infosierra.es/images/logo_sectores/73/comercial_73__logo.jpg");
		dbLocal.insertAnuncio(anuncio3);


		///////////////
		//LIBRERIAS//
		///////////////
		Anuncio anuncio4 = new Anuncio();
		anuncio4.set_nombre("Libreria del Rosario");
		anuncio4.set_descripcion("Libreria en general, papelería, material de oficina, sellos de caucho y consumibles de ofimática");
		anuncio4.set_direccion("C/ San Blas,5. 21200 Aracena (Huelva)");
		anuncio4.set_email("libroaracena@yahoo.es");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio4.set_X(6.474167);
		anuncio4.set_Y(37.893056);
		anuncio4.set_tags(new String[]{"libreria","papeleria"});
		anuncio4.set_telefonos(new String[]{"959128005"});	
		anuncio4.set_foto("http://www.infosierra.es/images/logo_sectores/17/comercial_17__logo.jpg");
		dbLocal.insertAnuncio(anuncio4);

		Anuncio anuncio5 = new Anuncio();
		anuncio5.set_nombre("Libreria Estanco Loli");
		anuncio5.set_descripcion("Libreria, papelería, encuadernaciones, fotocopias, todo tipo de materiales de oficina y escolar. Libros de Textos venta de labores de tabaco. Artículos de regalo.");
		anuncio5.set_direccion("Plaza de la Constitución, 3. 21291 Galaroza (Huelva)");
		anuncio5.set_email("libreriaestancololi@yahoo.es");
		//anuncio2.set_web("http://www.miweb.com");
		anuncio5.set_X(6.474167);
		anuncio5.set_Y(37.893056);
		anuncio5.set_tags(new String[]{"libreria", "estanco", "papeleria"});
		anuncio5.set_telefonos(new String[]{"959123200", "620547002"});	
		anuncio5.set_foto("http://www.infosierra.es/images/galerias_sectores/86/1328271749-mini.jpg");
		dbLocal.insertAnuncio(anuncio5);


		///////////////
		//MÉDICOS//
		///////////////
		Anuncio anuncio6 = new Anuncio();
		anuncio6.set_nombre("Centro de Salud de Aracena");
		//anuncio6.set_descripcion("");
		anuncio6.set_direccion("C/ Zulema S/N. 21200 Aracena (Huelva)");
		//anuncio6.set_email("libroaracena@yahoo.es");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio6.set_X(6.474167);
		anuncio6.set_Y(37.893056);
		anuncio6.set_tags(new String[]{"medico","salud"});
		anuncio6.set_telefonos(new String[]{"959129613"});	
		anuncio6.set_foto("http://www.infosierra.es/images/logo_telefonos_interes/33/1332947554_logo.jpg");
		dbLocal.insertAnuncio(anuncio6);

		Anuncio anuncio7 = new Anuncio();
		anuncio7.set_nombre("Consultorio Médico de Valdelarco");
		//anuncio7.set_descripcion("");
		anuncio7.set_direccion("C/ Atocha nº 1. 21291 Valdelarco (Huelva)");
		//anuncio7.set_email("libreriaestancololi@yahoo.es");
		//anuncio2.set_web("http://www.miweb.com");
		anuncio7.set_X(6.474167);
		anuncio7.set_Y(37.893056);
		anuncio7.set_tags(new String[]{"medico", "salud"});
		anuncio7.set_telefonos(new String[]{"959124897"});	
		anuncio7.set_foto("http://www.infosierra.es/images/logo_telefonos_interes/33/1332947554_logo.jpg");
		dbLocal.insertAnuncio(anuncio7);	
		
		///////////////
		//TALLERES//
		///////////////
		Anuncio anuncio8 = new Anuncio();
		anuncio8.set_nombre("Talleres y Recambios Fernandez");
		anuncio8.set_descripcion("Taller de reparación electromecánico de automóviles. Servicios neumáticos, alineamiento de dirección, reparación de aire acondicionado, diagnosis, electricidad y mecánica en general. Amplio horario de trabajo. Calidad y buen precio.");
		anuncio8.set_direccion("C/ Silos, 13 y 16. 21200 Aracena (Huelva)");
		anuncio8.set_email("talleres.fernandez@yahoo.es");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio8.set_X(6.474167);
		anuncio8.set_Y(37.893056);
		anuncio8.set_tags(new String[]{"taller","recambios"});
		anuncio8.set_telefonos(new String[]{"959128839"});	
		anuncio8.set_foto("http://www.infosierra.es/images/logo_sectores/125/comercial_125__logo.jpg");
		dbLocal.insertAnuncio(anuncio8);

		Anuncio anuncio9 = new Anuncio();
		anuncio9.set_nombre("Autorecambios Iglesias");
		anuncio9.set_descripcion("Recambios y accesorios del automóvil, bicicletas, maquinarias agrícolas. Motosierras Motocultores Desbrozadoras Motobombas Visítenos. Junto a la carretera Sevilla-Lisboa.");
		anuncio9.set_direccion("C/ Arrieros, 10 bajo. 21200 Aracena (Huelva)");
		anuncio9.set_email("rafael.iglesias21@hotmail.com");
		//anuncio2.set_web("http://www.miweb.com");
		anuncio9.set_X(6.474167);
		anuncio9.set_Y(37.893056);
		anuncio9.set_tags(new String[]{"taller", "recambios"});
		anuncio9.set_telefonos(new String[]{"959126797", "677848456"});	
		anuncio9.set_foto("http://www.infosierra.es/images/logo_telefonos_interes/33/1332947554_logo.jpg");
		dbLocal.insertAnuncio(anuncio9);	
		
		///////////////
		//FONTANERIA//
		///////////////
		Anuncio anuncio10 = new Anuncio();
		anuncio10.set_nombre("Construcciones y Reformas Bejarano");
		anuncio10.set_descripcion("Construcción de nuevas viviendas y reformas en general. Casas en estilo rural, moderno, ... Acabado completo con fontanería, electricidad, pintura, calefacción, carpintería, ... Profesionales con más de 20 años de experiencia en el sector. Pida presupuestos sin compromiso");
		anuncio10.set_direccion("C/ Cielo, 15. 21291 Galaroza (Huelva)");
		anuncio10.set_email("construccionesbejarano@gmail.com");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio10.set_X(6.474167);
		anuncio10.set_Y(37.893056);
		anuncio10.set_tags(new String[]{"construccion","fontaneria", "reformas"});
		anuncio10.set_telefonos(new String[]{"685981589","647699020" });	
		anuncio10.set_foto("http://www.infosierra.es/images/logo_sectores/116/comercial_116__logo.jpg");
		dbLocal.insertAnuncio(anuncio10);

		Anuncio anuncio11 = new Anuncio();
		anuncio11.set_nombre("Construcciones y Más");
		anuncio11.set_descripcion("Construcción de obras mayores y menores, pequeñas reparaciones, reparaciones de problemas eléctricos y fontanería, asi como instalaciones nuevas, reparaciones de todo tipo de electrodomésticos, termos, law, ... Venta de termos nuevos y de segunda mano, así como electrodomésticos. Pinturas de exterior e interior, puertas para chimeneas,... ¡Le resolvemos los problemas del hogar!");
		anuncio11.set_direccion("c/ El Mirador, nº 3. 21200 Aracena (Huelva)");
		anuncio11.set_email("juasima@hotmail.com");
		anuncio11.set_web("http://www.silva-marques.es/");
		anuncio11.set_X(6.474167);
		anuncio11.set_Y(37.893056);
		anuncio11.set_tags(new String[]{"construccion", "fontaneria", "reformas"});
		anuncio11.set_telefonos(new String[]{"678223693", "959127496"});	
		anuncio11.set_foto("http://www.infosierra.es/images/logo_sectores/151/comercial_151__logo.jpg");
		dbLocal.insertAnuncio(anuncio11);
		
		///////////////
		//TRANSPORTES//
		///////////////
		Anuncio anuncio12 = new Anuncio();
		anuncio12.set_nombre("Transdehesa, S.L.");
		anuncio12.set_descripcion("Transporte en general. Venta de subproductos para el ganado. Compra y venta de Leña");
		anuncio12.set_direccion("C/ Iglesia, 3. 21260 Santa Olalla (Huelva)");
		anuncio12.set_email("franciscocmcarranza@hotmail.com");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio12.set_X(6.474167);
		anuncio12.set_Y(37.893056);
		anuncio12.set_tags(new String[]{"transportes"});
		anuncio12.set_telefonos(new String[]{"954229891","686005053" });	
		anuncio12.set_foto("http://www.infosierra.es/images/logo_sectores/46/comercial_46__logo.jpg");
		dbLocal.insertAnuncio(anuncio12);

	}

}
