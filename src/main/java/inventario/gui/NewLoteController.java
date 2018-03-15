package inventario.gui;

import static inventario.gui.Main.ERROR;
import static inventario.gui.Resources.STRINGS_GUI;

import java.sql.SQLException;

import org.controlsfx.control.textfield.CustomTextField;

import inventario.dao.DAOLote;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOLoteMySQL;
import inventario.modelo.Lote;
import inventario.modelo.Producto;
import inventario.modelo.error.ModelConstraintViolationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class NewLoteController {
	
	@FXML private CustomTextField fieldNumero;
	@FXML private DatePicker datePicker;
	@FXML private Button btnGuardar;
	
	private Stage stage = null;
	private Producto producto = null;
	private DAOLote daoLotes = null;
	
	@FXML
	private void initialize() {
		
		try {
			daoLotes = new DAOLoteMySQL(Conexion.get());
		} catch (DBConnectionException | SQLException e) {
			e.printStackTrace();
		}
		
		Fields.setupClearButtonField(fieldNumero);
		fieldNumero.setOnAction(e -> datePicker.requestFocus());	
		datePicker.setOnAction(e -> onGuardar(e));
		
	}
	
	public void reset() {
		datePicker.setValue(null);
		fieldNumero.clear();
		fieldNumero.requestFocus();
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public void setStage(Stage stg) {
		if(this.stage == null)
			this.stage = stg;
	}
	
	@FXML
	private void onGuardar(ActionEvent event) {
		try {
			Lote nuevo = new Lote(producto.getId(),
					Long.parseLong(fieldNumero.getText().trim()),datePicker.getValue());
			daoLotes.insert(nuevo);
			stage.close();
			Main.mostrarMensaje(STRINGS_GUI.getString("msg.insert.lote"));
		} catch (NumberFormatException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.datos"));
			ERROR.setContentText(STRINGS_GUI.getString("error.numberformat")
					.replaceAll("\\{\\{C\\}\\}", "\"" + STRINGS_GUI.getString("numero") + "\""));
			ERROR.showAndWait();
			fieldNumero.selectAll();
			fieldNumero.requestFocus();
		} catch (ModelConstraintViolationException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.datos"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
			fieldNumero.selectAll();
			fieldNumero.requestFocus();
		} catch (DBConnectionException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.conexion"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		} catch (CannotInsertException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.consulta"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
			fieldNumero.selectAll();
			fieldNumero.requestFocus();
		}
	}

}
