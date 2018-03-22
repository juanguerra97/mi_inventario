package inventario.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import static inventario.gui.Main.SETTINGS;

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
	@FXML private ChoiceBox<String> choiceBoxScalaTiempo;
	
	@FXML
	private void initialize() {
		choiceBoxIdioma.getItems().setAll(Configuracion.IDIOMAS);
		choiceBoxScalaTiempo.getItems().setAll(Configuracion.ESCALAS_TIEMPO);
		cargar();
	}
	
	/**
	 * Metodo para cargar la configuracion en la interfaz
	 */
	public void cargar() {
		
		checkNombresProductos.setSelected(SETTINGS.getCapitalizarNombresProductos());
		checkMarcasProductos.setSelected(SETTINGS.getCapitalizarMarcasProductos());
		checkNombresCategorias.setSelected(SETTINGS.getCapitalizarNombresCategorias());
		checkNombresPresentaciones.setSelected(SETTINGS.getCapitalizarNombresPresentaciones());
		
		choiceBoxIdioma.getSelectionModel().select(SETTINGS.getIdioma());
		
		checkMostrarProductosPorVencer.setSelected(SETTINGS.getMostrarProductosPorVencer());
		choiceBoxScalaTiempo.getSelectionModel().select(SETTINGS.getEscalaTiempo());
		spinnerTiempoVencimiento.getValueFactory().setValue(SETTINGS.getTiempo());
		
	}
	
}
