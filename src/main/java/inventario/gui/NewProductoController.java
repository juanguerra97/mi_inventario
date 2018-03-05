package inventario.gui;

import org.controlsfx.control.CheckListView;

import inventario.modelo.Categoria;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewProductoController {
	
	@FXML private Button btnGuardar;
	
	@FXML private TextField fieldId;
	@FXML private TextField fieldNombre;
	@FXML private TextField fieldMarca;
	
	@FXML private Label lblCategorias;
	@FXML private CheckListView<Categoria> listCategorias;
	
	@FXML
	private void initialize() {
		
	}

}
