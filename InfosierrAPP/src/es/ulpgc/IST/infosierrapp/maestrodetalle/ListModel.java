package es.ulpgc.IST.infosierrapp.maestrodetalle;


/**
 * Contiene los items obtenidos tras la busqueda
 * @author jesus
 *
 */
public class ListModel {

//TODO Obtener los items de la clase BuscadorDatos
	private ItemModel[] data = new ItemModel[]{
			new ItemModel("Restaurante 1", "Calle Ficticia", "928000000", "restaurante1@hotmail.com", "blablabla", 0,0),
			new ItemModel("Tienda 1", "Calle Mentira", "928030000", "tienda1@hotmail.com", "blablabla",10,0),
			new ItemModel("Restaurante 2", "Calle Imaginaria", "928000001", "restaurante2@hotmail.com", "blablabla",0,10)

	};


	public ItemModel[] getData() {
		return data;
	}

	public void setData(int pos, ItemModel itemModel) {
		data[pos] = itemModel;
	}

	public void delData(int pos) {

		ItemModel[] newdata = new ItemModel[data.length-1];
		for (int cont=0; cont < pos; cont++) {
			newdata[cont] = data[cont];
		}
		for (int cont=pos; cont < data.length-1; cont++) {
			newdata[cont] = data[cont+1];
			newdata[cont].setPos(cont);
		}
		data = newdata;
		ItemModel.maxpos = data.length;

	}
}