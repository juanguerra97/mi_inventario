package inventario.gui;

import java.sql.SQLException;
import inventario.dao.DAOMarca;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOMarcaMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import static inventario.gui.Main.ERROR;
import static inventario.gui.Resources.STRINGS_GUI;

/**
 * Controlador de eventos de la interfaz del archivo AdminMarcas.fxml
 * @author juang
 *
 */
public class AdminMarcasController {
	
	@FXML private ListView<String> listMarcas;
	@FXML private TextField fieldNomMarca;
	@FXML private Button btnNewMarca;
	
	private DAOMarca daoMarca;
	private ObservableList<String> marcas;// lista con las marcas que se estan mostrando en el listview
	
	@FXML
	private void initialize() {
		
		marcas = FXCollections.observableArrayList();
		listMarcas.setItems(marcas);
		
		conectar();
		if(daoMarca != null)
			loadAll();
		
	}
	
	/**
	 * Metodo para activar o desactivar todos los componentes graficos
	 * @param disable true si se deben desactivar los componentes
	 */
	public void disableAll(boolean disable) {
		listMarcas.setDisable(disable);
		fieldNomMarca.setDisable(disable);
		btnNewMarca.setDisable(disable);
	}
	
	/**
	 * Metodo para realizar la conexion con la base de datos si fallo al principio
	 */
	public void conectar() {
		if(daoMarca != null)
			return;
		try {
			daoMarca = new DAOMarcaMySQL(Conexion.get());
			disableAll(false);
		} catch (DBConnectionException | SQLException e) {
			disableAll(true);
			ERROR.setHeaderText(STRINGS_GUI.getString("error.conexion.header"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		}
	}
	
	/**
	 * Metodo para cargar todas las marcas en la lista de marcas
	 */
	public void loadAll() {
		assert daoMarca != null;
		listMarcas.getSelectionModel().clearSelection();
		marcas.setAll(daoMarca.getAll(0, 40));
		boolean isEmpty = marcas.isEmpty();
		listMarcas.setDisable(isEmpty);
	}
	
}
