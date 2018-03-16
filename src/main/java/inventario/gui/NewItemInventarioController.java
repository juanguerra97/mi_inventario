package inventario.gui;

import static inventario.gui.Main.ERROR;
import static inventario.gui.Resources.STRINGS_GUI;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.controlsfx.control.textfield.CustomTextField;

import inventario.dao.DAOPresentacionLote;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOPresentacionLoteMySQL;
import inventario.modelo.Lote;
import inventario.modelo.Presentacion;
import inventario.modelo.PresentacionLote;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewItemInventarioController {
	
	@FXML private CustomTextField fieldPrecio;
	@FXML private CustomTextField fieldCosto;
	@FXML private CustomTextField fieldCantidad;
	
	@FXML private Button btnGuardar;
	
	private Stage stage = null;
	private Presentacion presentacion;
	private Lote lote;
	
	private DAOPresentacionLote daoInventario = null;
	
	@FXML
	private void initialize() {
		
		try {
			daoInventario = new DAOPresentacionLoteMySQL(Conexion.get());
		} catch (DBConnectionException | SQLException e) {
			e.printStackTrace();
		}
		
		Fields.setupClearButtonField(fieldPrecio);
		Fields.setupClearButtonField(fieldCosto);
		Fields.setupClearButtonField(fieldCantidad);
		
		fieldPrecio.setOnAction( e -> {
			fieldCosto.requestFocus();
			fieldCosto.selectAll();
		});
		
		fieldCosto.setOnAction(e -> {
			fieldCantidad.requestFocus();
			fieldCantidad.selectAll();
		});
		
		fieldCantidad.setOnAction(e -> onGuardar(e));
		
	}
	
	public void reset() {
		fieldCantidad.clear();
		fieldCosto.clear();
		fieldPrecio.clear();
		fieldPrecio.requestFocus();
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setPresentacionLote(Presentacion p, Lote l) {
		assert p != null;
		assert l != null;
		if(p.getIdProducto() != l.getIdProducto())
			throw new IllegalArgumentException(STRINGS_GUI.getString("error.producto.presentacion_lote"));
		this.presentacion = p;
		this.lote = l;
	}
	
	@FXML
	private void onGuardar(ActionEvent event) {
		try {
			PresentacionLote nuevo = new PresentacionLote(
					lote.getIdProducto(), presentacion.getNombre(), lote.getNumero(), 
					Integer.parseInt(fieldCantidad.getText().trim()), 
					new BigDecimal(fieldCosto.getText().trim()), 
					new BigDecimal(fieldPrecio.getText().trim()));
			daoInventario.insert(nuevo);
			stage.close();
			Main.mostrarMensaje(STRINGS_GUI.getString("msg.insert.stock"));
		} catch (NumberFormatException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.datos"));
			ERROR.setContentText("");
			ERROR.showAndWait();
			fieldPrecio.selectAll();
			fieldPrecio.requestFocus();
		} catch (ModelConstraintViolationException | EmptyArgumentException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.datos"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
			fieldPrecio.selectAll();
			fieldPrecio.requestFocus();
		} catch (DBConnectionException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.conexion"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		} catch (CannotInsertException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.consulta"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
			fieldPrecio.selectAll();
			fieldPrecio.requestFocus();
		}
	}

}
