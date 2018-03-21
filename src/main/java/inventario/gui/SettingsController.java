package inventario.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;

/**
 * Manejador de eventos de la interfaz SettingsView.fxml
 * @author juang
 *
 */
public class SettingsController {
	
	@FXML private Button btnRestablecer;
	@FXML private Button btnGuardar;
	
	@FXML private CheckBox checkNombresProductos;
	@FXML private CheckBox checkMarcasProductos;
	@FXML private CheckBox checkNombresCategorias;
	@FXML private CheckBox checkNombresPresentaciones;
	
	@FXML private ChoiceBox<Idioma> choiceBoxIdioma;
	
	@FXML private CheckBox checkMostrarProductosPorVencer;
	@FXML private Spinner<Integer> spinnerTiempoVencimiento;
	@FXML private ChoiceBox<String> choiceBoxeScalaTiempo;
	
}
