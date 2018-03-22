package inventario.gui;

import static inventario.gui.Main.SETTINGS;
import static inventario.gui.Resources.STRINGS_SETTINGS;
import inventario.files.ProgramFiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

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
		
		SpinnerValueFactory<Integer> spinValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 0);
		spinnerTiempoVencimiento.setValueFactory(spinValueFactory);
		
		
		checkMostrarProductosPorVencer.selectedProperty().addListener((o,oldValue,newValue)->{
			choiceBoxScalaTiempo.setDisable(!newValue);
			spinnerTiempoVencimiento.setDisable(!newValue);
			if(newValue) {
				choiceBoxScalaTiempo.getSelectionModel().select(SETTINGS.getEscalaTiempo());
				spinnerTiempoVencimiento.getValueFactory().setValue(SETTINGS.getTiempo());
			}
		});
		
		cargar();
	}
	
	@FXML
	private void onGuardar(ActionEvent event) {
		
		SETTINGS.setCapitalizarNombresProductos(checkNombresProductos.isSelected());
		SETTINGS.setCapitalizarMarcasProductos(checkMarcasProductos.isSelected());
		SETTINGS.setCapitalizarNombresCategorias(checkNombresCategorias.isSelected());
		SETTINGS.setCapitalizarNombresPresentaciones(checkNombresPresentaciones.isSelected());
		
		SETTINGS.setIdioma(choiceBoxIdioma.getValue());
		
		SETTINGS.setMostrarProductosPorVencer(checkMostrarProductosPorVencer.isSelected());
		SETTINGS.setEscalaTiempo(choiceBoxScalaTiempo.getValue());
		SETTINGS.setTiempo(spinnerTiempoVencimiento.getValueFactory().getValue());
		
		ProgramFiles.saveSettings(SETTINGS);
		
		Main.mostrarMensaje(STRINGS_SETTINGS.getString("msg.guardado"));
		
	}
	
	@FXML
	private void onRestablecerPredeterminados(ActionEvent event) {
		
		SETTINGS.reset();
		
		ProgramFiles.saveSettings(SETTINGS);
		cargar();
		
		Main.mostrarMensaje(STRINGS_SETTINGS.getString("msg.restablecido"));
		
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
