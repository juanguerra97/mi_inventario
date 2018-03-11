package inventario.gui;

import static inventario.gui.Main.ERROR;
import static inventario.gui.Resources.STRINGS_GUI;

import java.sql.SQLException;

import org.controlsfx.control.textfield.CustomTextField;

import inventario.dao.DAOCategoria;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOCategoriaMySQL;
import inventario.modelo.Categoria;
import inventario.modelo.error.EmptyArgumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewCategoriaController {
	
	@FXML CustomTextField fieldNombre;
	@FXML Button btnGuardar;
	
	private Stage stage = null;
	
	private DAOCategoria daoCats = null;
	
	@FXML
	private void initialize() {
		
		try {
			daoCats = new DAOCategoriaMySQL(Conexion.get());
		} catch (DBConnectionException | SQLException e) {
			e.printStackTrace();
		}
		
		Fields.setupClearButtonField(fieldNombre);
		
		fieldNombre.setOnAction((event)->onGuardar(event));
		
		reset();
		
	}
	
	@FXML
	private void onGuardar(ActionEvent event) {
		String nombre = fieldNombre.getText().trim();
		nombre = nombre.toUpperCase();
		try {
			Categoria nueva = new Categoria(nombre);
			daoCats.insert(nueva);
			stage.close();
			Main.mostrarMensaje(STRINGS_GUI.getString("msg.insert.categoria"));
		} catch (EmptyArgumentException e) {
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
	
	public void reset() {
		fieldNombre.clear();
		fieldNombre.requestFocus();
	}
	
	public void setStage(Stage stg) {
		if(this.stage == null)
			this.stage = stg;
	}

}
