package es.ulpgc.IST.infosierrapp.datos.local;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import es.ulpgc.IST.infosierrapp.datos.Anuncio;


/**
 * Clase DAO (Data Access Object) (o Facade) que gestionará
 * el acceso a la base de datos: abstrae a la aplicación del
 * del manejo de la BD. Accede, busca, modifica y convierte los
 * datos en objetos java (Anuncio).
 * 
 * Una alternativa a esta clase es un ContentProvider: más nuevo,
 * más eficiente y mejor. Poco a poco.
 * 
 * @author krlo
 */
public class BD_local_Acceso {

	// Etiqueta para logs
	public static final String LOG_TAG="BD_local_Acceso";	
	// Base de datos
	private SQLiteDatabase database = null;
	// Helper
	private BD_local_SQLiteHelper dbHelper = null;

	/**
	 * Constructor
	 * Inicializa el helper 
	 * @param context
	 */
	public BD_local_Acceso(Context context) {
		dbHelper = BD_local_SQLiteHelper.getHelper(context);
	}

	/**
	 * Abre la conexión con la BD
	 * @throws SQLException
	 */
	private void open_db() throws SQLException {
		if ( dbHelper != null) {
			database = dbHelper.getWritableDatabase();
		} else {
			throw new SQLException();
		}
	}
	/**
	 * Cierra la conexión con la BD
	 */
	private void close_db() {
		if(database != null){
			Log.i(LOG_TAG, "Cerrando la base de datos [ " 
					+ dbHelper.getDatabaseName() + " ].");
			database.close();
		}
	}


	/**
	 * Devuelve todo el contenido de la BD en una lista
	 * de Anuncios.
	 * 
	 * @return la lista de Anuncios o null en caso de que la db estuviese vacía
	 * o haya algún error  
	 */
	public List<Anuncio> get_all_list() {

		// Lista a devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();

		//Abre la conexón con la BD
		open_db();

		// Obtiene el cursor con todos los resultados de la BD
		Cursor cursor = database.query(TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, null, null, null, null, null);

		if (cursor != null) {
			// Recorre la BD mediante el cursor, convirtiendo
			// cada entrada en un Anuncio y añadiéndolo a la lista
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Anuncio anuncio = Anuncio.cursorToAnuncio(cursor);
				anuncios.add(anuncio);
				cursor.moveToNext();
			}
			// Cierra el cursor
			cursor.close();
			// Cierra la BD
			close_db();

		} else {
			anuncios = null;			
		}

		// Devuelve la lista o null en caso de que no haya
		// encontrado nada
		return anuncios;
	}

	/**
	 * Devuelve todo el contenido de la BD mediante un cursor.
	 * Importante cerrar el cursor y la base de datos una vez se
	 * terminen de usar.
	 * 
	 * @return un cursor apuntando a todos los resultados o null 
	 * si la db estaba vacía o hubo algún error 
	 */
	public Cursor get_all_cursor() {

		//Abre la conexón con la BD
		open_db();

		// Obtiene el cursor con todos los resultados de la BD
		Cursor cursor = database.query( TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, 
				null, null, null, null, null);

		// Devuelve el cursor
		return cursor;
	}


	/**
	 * Realiza una búsqueda en la DB devolviendo una lista de Anuncios
	 * 
	 * @param query_string la cadena que se quiere buscar
	 * @return lista de resultados o null si no se encuentra nada o hay error
	 */
	public List<Anuncio> buscarPorTags(String query_string ){

		// Lista a devolver
		List<Anuncio> anuncios = new ArrayList<Anuncio>();

		//Abre la conexón con la BD
		open_db();

		// Hace la búsqueda
		Cursor cursor = database.query(TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, 
				TablaResultados.COL_TAGS + " MATCH ?",
				new String[] {"*"+query_string+"*"},
				null, null, null);

		// Si hay resultados...
		if (cursor != null) {
			// Recorre los resultados mediante el cursor, convirtiendo
			// cada entrada en un Anuncio y añadiéndolo a la lista
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Anuncio anuncio = Anuncio.cursorToAnuncio(cursor);
				anuncios.add(anuncio);
				cursor.moveToNext();
			}
			// Cierra el cursor y la BD
			cursor.close();
			close_db();

		} else {
			anuncios = null;
		}

		return anuncios;		
	}


	/**
	 * Realiza una búsqueda en la DB devolviendo un Cursor
	 * con los resultados. Importante cerrar el cursor y la
	 * base de datos una vez se terminen de usar.
	 * 
	 * @param query_string la cadena que se quiere buscar
	 * @return cursor a resultados o null si no se encuentra nada o hay error
	 */
	public Cursor buscarEnTags(String query_string) {

		//Abre la conexón con la BD
		open_db();

		// Hace la búsqueda
		Cursor cursor = database.query(TablaResultados.TABLE_NAME,
				TablaResultados.ALL_COLUMNS, 
				TablaResultados.COL_TAGS + " MATCH ?",
				new String[] {"*"+query_string+"*"},
				null, null, null);

		// Devuelve el cursor
		return cursor;

	}



	/**
	 * 
	 * Vacía la tabla Resultados.
	 * 
	 */
	public void borrarResultados() {
		open_db();
		database.execSQL("DROP TABLE IF EXISTS " + TablaResultados.TABLE_NAME);
		dbHelper.onCreate(database);
		close_db();
	}


	/**
	 * Guarda un objeto Anuncio en la BD
	 * @param objeto de tipo Anuncio que se quiere insertar
	 * @return el ID de la nueva fila creada. -1 si hubo algún error.
	 */
	public long insertAnuncio(Anuncio anuncio) {

		// Crea el contenedor para meter en la BD
		ContentValues anuncioCV = new ContentValues();

		// Procesa todos los campos excepto el ID (se lo asignará automáticamente la BD)
		anuncioCV.put(TablaResultados.COL_NOMBRE,		anuncio.get_nombre() );		
		anuncioCV.put(TablaResultados.COL_DIRECCION,	anuncio.get_direccion() );
		anuncioCV.put(TablaResultados.COL_DESC,			anuncio.get_descripcion() );
		anuncioCV.put(TablaResultados.COL_EMAIL,		anuncio.get_email() );
		anuncioCV.put(TablaResultados.COL_FOTO, 		anuncio.get_foto() );
		anuncioCV.put(TablaResultados.COL_WEB,			anuncio.get_web() );
		anuncioCV.put(TablaResultados.COL_MAPX,			anuncio.get_X() );
		anuncioCV.put(TablaResultados.COL_MAPY, 		anuncio.get_Y() );		
		anuncioCV.put(TablaResultados.COL_TAGS,			anuncio.get_AllTags() );
		anuncioCV.put(TablaResultados.COL_TELEFONOS,	anuncio.get_AllTelefonos() );		


		// Abre la conexión con la BD
		open_db();

		// Realiza la inserción
		// Excepción SQLException si hay error.
		long inserted_ID = database.insert(TablaResultados.TABLE_NAME, null, anuncioCV);

		// Cierra la BD
		close_db();

		return inserted_ID;
	}





	/**
	 * TODO: borrar método y referencias cuando se 
	 * terminen las pruebas iniciales
	 * 
	 * Crea unos cuantos anuncios para pruebas.
	 * 
	 */
	public void rellenaUnPoco() {
		open_db();

		///////////////
		//RESTAURANTES//
		///////////////
		Anuncio anuncio1 = new Anuncio();
		anuncio1.set_nombre("Casa Pepa Restaurante");
		anuncio1.set_descripcion("Desde aquí quiero presentarles el el Restaurante Casa Pepa. Inaugurado en el año 2000 lleva una década ofreciendo a las personas que pasan por aquí un servicio basado en la humildad en un trato cercano y familiar. Está ubicado en el casco antiguo del pueblo en uno de esos rincones tan pintorescos de la sierra donde se respira el silencio y la tranquilidad. A pocos metros pueden visitar la Plaza que posiblemente sea la mas hermosa de todo este entorno Serrano. LA PLAZA DE LA IGLESIA. Todos los platos que pueden degustar son de elaboración casera con excelentes productos autóctonos, desde los criados en las huertas hasta los productos silvestres (Setas, Tagazninas...) aunque la base principal sea el cerdo Ibérico (como pueden imaginar) acompañado por diferentes salsas o cremas. Casi toda nuestra variedad de platos pueden ser degustados desde tapas, raciones, pudiendo adecuar el plato al gusto del cliente. Desde aquí le invitamos que pasen a visitarnos y de camino aprovechen para conocer nuestro pueblo y sus costumbres que les causará una grata impresión. Gracias por visitar nuestra página y esperamos verles pronto por nuestra casa.");
		anuncio1.set_direccion("C/ Portales, 10. 21210 Zufre (Huelva)");
		anuncio1.set_email("frutu6@hotmail.com");
		//anuncio1.set_web("http://www.miweb.com");
		anuncio1.set_X(6.474167);
		anuncio1.set_Y(37.893056);
		anuncio1.set_tags(new String[]{"bar", "restaurante", "cafeteria"});
		anuncio1.set_telefonos(new String[]{"959198128", "680588108"});	
		anuncio1.set_foto("http://www.infosierra.es/images/galerias_sectores/91/1328862476-mini.jpg");
		insertAnuncio(anuncio1);

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
		insertAnuncio(anuncio2);

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
		insertAnuncio(anuncio3);


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
		insertAnuncio(anuncio4);

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
		insertAnuncio(anuncio5);


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
		insertAnuncio(anuncio6);

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
		insertAnuncio(anuncio7);	
		
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
		insertAnuncio(anuncio8);

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
		anuncio8.set_foto("http://www.infosierra.es/images/logo_telefonos_interes/33/1332947554_logo.jpg");
		insertAnuncio(anuncio9);	

		close_db();

	}



}
