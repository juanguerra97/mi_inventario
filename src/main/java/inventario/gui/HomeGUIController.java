package inventario.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

public class HomeGUIController {
	
	@FXML private Tab tabInventario;
	
	private Parent adminInvGUI;
	
	@FXML
	private void initialize() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/inventario/gui/AdminInventario.fxml"),
					Resources.STRINGS_GUI);
			adminInvGUI = loader.load();
			
			tabInventario.setContent(adminInvGUI);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
