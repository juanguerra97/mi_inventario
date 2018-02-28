package inventario.gui;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import static inventario.gui.Resources.STRINGS_GUI;

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
	
	@Override
	public void start(Stage stg) throws Exception {
		
	}

	public static void main(String[] args) {
		ERROR.setTitle(STRINGS_GUI.getString("error"));
		INFO.setTitle(STRINGS_GUI.getString("info"));
		launch(args);
	}

}
