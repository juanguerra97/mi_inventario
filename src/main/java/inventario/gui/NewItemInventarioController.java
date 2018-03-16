package inventario.gui;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NewItemInventarioController {
	
	@FXML private CustomTextField fieldPrecio;
	@FXML private CustomTextField fieldCosto;
	@FXML private CustomTextField fieldCantidad;
	
	@FXML private Button btnGuardar;
	
	@FXML
	private void onGuardar(ActionEvent event) {
		
	}

}
