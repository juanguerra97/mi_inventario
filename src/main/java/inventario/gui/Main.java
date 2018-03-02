package inventario.gui;

import static inventario.gui.Resources.STRINGS_GUI;

import org.controlsfx.control.NotificationPane;

import inventario.dao.mysql.Conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Clase con metodo principal para iniciar la aplicacion
 * @author juang
 *
 */
public class Main extends Application {
	
	/**
	 * Ventana para mostrar mensajes de error
	 */
	public static Alert ERROR = new Alert(AlertType.ERROR,"",new ButtonType(STRINGS_GUI.getString("entendido")));
	
	/**
	 * Ventana para mostrar mensajes con informacion
	 */
	public static Alert INFO = new Alert(AlertType.INFORMATION,"",new ButtonType(STRINGS_GUI.getString("entendido")));
	
	private static NotificationPane root = new NotificationPane();
	
	@Override
	public void start(Stage stg) throws Exception {
		root.setContent(FXMLLoader.load(
				getClass().getResource("/inventario/gui/HomeGUI.fxml"), 
				Resources.STRINGS_GUI));
		Scene s = new Scene(root,750,450);
		stg.setScene(s);
		stg.setMinWidth(750);
		stg.setMinHeight(450);
		stg.setOnCloseRequest(w->{
			Conexion.close();
		});
		stg.show();
	}

	public static void main(String[] args) {
		ERROR.setTitle(STRINGS_GUI.getString("error"));
		INFO.setTitle(STRINGS_GUI.getString("info"));
		launch(args);
	}

}
