package inventario.gui;

import static inventario.gui.Main.ERROR;
import static inventario.gui.Resources.STRINGS_GUI;

import java.sql.SQLException;

import org.controlsfx.control.textfield.CustomTextField;

import inventario.dao.DAOPresentacion;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOPresentacionMySQL;
import inventario.modelo.Presentacion;
import inventario.modelo.Producto;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewPresentacionController {
	
	@FXML private CustomTextField fieldNombre;
	@FXML private Button btnGuardar;
	
	private Stage stage = null;
	private DAOPresentacion daoPresentacion = null;
	private Producto producto = null;// producto al que se agregara la nueva presentacion
	
	@FXML
	private void initialize() {
		
		try {
			daoPresentacion = new DAOPresentacionMySQL(Conexion.get());
		} catch (DBConnectionException | SQLException e) {
			e.printStackTrace();
		}
		
		Fields.setupClearButtonField(fieldNombre);
		fieldNombre.setOnAction(e -> onGuardar(e));
		
	}
	
	public void reset() {
		fieldNombre.clear();
		fieldNombre.requestFocus();
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
		String nombre = fieldNombre.getText().trim();
		nombre = nombre.toUpperCase();
		try {
			Presentacion nueva = new Presentacion(producto.getId(), nombre);
			daoPresentacion.insert(nueva);
			stage.close();
			Main.mostrarMensaje(STRINGS_GUI.getString("msg.insert.presentacion"));
		} catch (ModelConstraintViolationException | EmptyArgumentException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.datos"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
			fieldNombre.selectAll();
			fieldNombre.requestFocus();
		} catch (DBConnectionException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.conexion"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		} catch (CannotInsertException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.consulta"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
			fieldNombre.selectAll();
			fieldNombre.requestFocus();
		}
	}

}
