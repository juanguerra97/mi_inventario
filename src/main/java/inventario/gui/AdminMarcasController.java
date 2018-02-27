package inventario.gui;

import inventario.modelo.Producto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controlador de eventos de la interfaz del archivo AdminMarcas.fxml
 * @author juang
 *
 */
public class AdminMarcasController {
	
	/**
	 * ListView donde se muestran las marcas
	 */
	@FXML public ListView<String> listMarcas;
	
	/**
	 * Campo de texto para el nombre de una nueva marca
	 */
	@FXML public TextField fieldNomMarca;
	
	/**
	 * Boton para guardar una nueva marca con el nombre del campo de texto
	 */
	@FXML public Button btnNewMarca;

}
