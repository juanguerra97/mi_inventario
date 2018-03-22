package inventario.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class HomeGUIController {
	
	@FXML private TabPane tabPane;
	@FXML private Tab tabInventario;
	@FXML private Tab tabConfig;
	
	private Parent adminInvGUI;
	private Parent settingsView;
	
	@FXML
	private void initialize() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/inventario/gui/AdminInventario.fxml"),
					Resources.STRINGS_GUI);
			adminInvGUI = loader.load();
			tabInventario.setContent(adminInvGUI);
			
			FXMLLoader loaderSettings = new FXMLLoader(
					getClass().getResource("/inventario/gui/SettingsView.fxml"),
					Resources.STRINGS_SETTINGS);
			settingsView = loaderSettings.load();
			tabConfig.setContent(settingsView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
