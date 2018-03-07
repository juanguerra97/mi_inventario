package inventario.gui;

import static inventario.gui.Main.ERROR;
import static inventario.gui.Resources.STRINGS_GUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.CustomTextField;

import inventario.dao.DAOCategoria;
import inventario.dao.DAOCategoriaProducto;
import inventario.dao.DAOProducto;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOCategoriaMySQL;
import inventario.dao.mysql.DAOCategoriaProductoMySQL;
import inventario.dao.mysql.DAOProductoMySQL;
import inventario.modelo.Categoria;
import inventario.modelo.Producto;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class NewProductoController {
	
	@FXML private Button btnGuardar;
	
	@FXML private CustomTextField fieldId;
	@FXML private CustomTextField fieldNombre;
	@FXML private CustomTextField fieldMarca;
	
	@FXML private Label lblCategorias;
	@FXML private CheckListView<Categoria> listCategorias;
	
	private DAOCategoria daoCategorias = null;
	private ObservableList<Categoria> categorias;
	
	private DAOProducto daoProductos = null;
	private DAOCategoriaProducto daoCatProd = null;
	
	private Stage stg = null;
	
	@FXML
	private void initialize() {
		
		try {
			daoCategorias = new DAOCategoriaMySQL(Conexion.get());
			daoProductos = new DAOProductoMySQL(Conexion.get());
			daoCatProd = new DAOCategoriaProductoMySQL(Conexion.get());
		} catch (DBConnectionException|SQLException e) {
			e.printStackTrace();
		}
		
		categorias = listCategorias.getItems();
		
		reset();
		
	}
	
	@FXML private void onGuardar(ActionEvent event) {
		boolean guardado = false;
		Connection con = null;
		try {
			con = Conexion.get();
			con.setAutoCommit(false);
			Producto nuevo = validar();
			daoProductos.insert(nuevo);
			List<Categoria> cats = listCategorias.getCheckModel().getCheckedItems();
			cats.stream().forEach(c->{
				try {
					daoCatProd.insert(nuevo.getId(), c.getNombre());
				} catch (DBConnectionException | CannotInsertException e) {
					e.printStackTrace();
				}
			});
			guardado = true;
			stg.close();
			Main.mostrarMensaje(STRINGS_GUI.getString("msg.insert.producto"));
		} catch (ModelConstraintViolationException | EmptyArgumentException 
				| IllegalArgumentException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.datos"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		} catch (DBConnectionException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.conexion"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		} catch (CannotInsertException e) {
			ERROR.setHeaderText(STRINGS_GUI.getString("error.consulta"));
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					if (guardado)
						con.commit();
					else
						con.rollback();
				} catch (SQLException e) {e.printStackTrace();}
			}
		}
	}
	
	public void setStage(Stage stg) {
		if(this.stg == null)
			this.stg = stg;
	}
	
	private Producto validar() throws ModelConstraintViolationException, EmptyArgumentException {
		try {
			long id = Long.parseLong(fieldId.getText().trim());
			String nombre = fieldNombre.getText();
			String marca = fieldMarca.getText();
			nombre = nombre.trim().toUpperCase();
			marca = marca.trim().toUpperCase();
			return new Producto(id,nombre,marca);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException(STRINGS_GUI.getString("error.id"));
		}
	}
	
	public void reset() {
		cargarCategorias();
		boolean catEmpty = categorias.isEmpty();
		lblCategorias.setDisable(catEmpty);
		listCategorias.setDisable(catEmpty);
		resetFields();
		fieldId.requestFocus();
	}
	
	private void resetFields() {
		fieldId.clear();
		fieldNombre.clear();
		fieldMarca.clear();
	}

	private void cargarCategorias() {
		categorias.setAll(daoCategorias.getAll(0, 30));
	}
}
