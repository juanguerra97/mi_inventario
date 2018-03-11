package inventario.gui;

import static inventario.gui.Resources.STRINGS_GUI;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.controlsfx.control.NotificationPane;

import inventario.dao.DAOCategoriaProducto;
import inventario.dao.DAOMarca;
import inventario.dao.DAOProducto;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOCategoriaProductoMySQL;
import inventario.dao.mysql.DAOMarcaMySQL;
import inventario.dao.mysql.DAOProductoMySQL;
import inventario.modelo.Lote;
import inventario.modelo.Presentacion;
import inventario.modelo.PresentacionLote;
import inventario.modelo.Producto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AdminInventarioController {
	
	private static final int MAX = 500, STEP = 10;
	
	@FXML private ListView<String> listMarcas;
	@FXML private MenuItem menuItemDesMarca;
	
	@FXML private ListView<String> listCategorias;
	@FXML private MenuItem menuItemDesCategoria;
	
	@FXML private TableView<Producto> tblProds;
	@FXML private TableColumn<Producto,Long> colIdProd;
	@FXML private TableColumn<Producto,String> colNomProd;
	@FXML private TableColumn<Producto,String> colMarcaProd;
	
	@FXML private MenuItem menuItemDesProd;
	@FXML private MenuItem menuItemElProd;
	@FXML private CheckMenuItem checkFiltrarProdsMarca;
	@FXML private CheckMenuItem checkFiltrarProdsCategoria;
	
	@FXML private TableView<Presentacion> tblPres;
	@FXML private TableColumn<Presentacion,String> colNomPres;
	@FXML private MenuItem menuItemDesPresentacion;
	@FXML private MenuItem menuItemElPresentacion;
	
	@FXML private TableView<Lote> tblLotes;
	@FXML private TableColumn<Lote,Long> colNumLote;
	@FXML private TableColumn<Lote,LocalDate> colFechaLote;
	@FXML private MenuItem menuItemDesLote;
	@FXML private MenuItem menuItemElLote;
	
	@FXML private TableView<PresentacionLote> tblInv;
	@FXML private TableColumn<PresentacionLote,Long> colLotInv;
	@FXML private TableColumn<PresentacionLote,String> colPresInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colPrecInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colCostInv;
	@FXML private TableColumn<PresentacionLote,Integer> colCantInv;
	@FXML private MenuItem menuItemDesInv;
	@FXML private MenuItem menuItemElInv;
	
	private Stage vtnNewProd;
	private NotificationPane paneNewProd;
	private NewProductoController newProdCtrl;
	
	private DAOProducto daoProds = null;
	private DAOCategoriaProducto daoCatsProd = null;
	private DAOMarca daoMarcas = null;
	
	FiltroProd filtroProductos = FiltroProd.NONE;
	
	@FXML
	private void initialize() {
		
		try {
			daoProds = new DAOProductoMySQL(Conexion.get());
			daoCatsProd = new DAOCategoriaProductoMySQL(Conexion.get());
			daoMarcas = new DAOMarcaMySQL(Conexion.get());
		} catch (DBConnectionException | SQLException ex) {
			ex.printStackTrace();
		}
		
		colIdProd.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNomProd.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colMarcaProd.setCellValueFactory(new PropertyValueFactory<>("marca"));
		
		colNomPres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		colNumLote.setCellValueFactory(new PropertyValueFactory<>("numero"));
		colFechaLote.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
		
		colLotInv.setCellValueFactory(new PropertyValueFactory<>("numeroLote"));
		colPresInv.setCellValueFactory(new PropertyValueFactory<>("nombrePresentacion"));
		colPrecInv.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colCostInv.setCellValueFactory(new PropertyValueFactory<>("costo"));
		colCantInv.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
	
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(()->{
			// evento para cargar datos al hacer scroll 
			// se hace en otro hilo porque la tabla tiene que estar renderizada para poder
			// obtener su ScrollBar
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LoadData.<Producto>loadOnScroll(tblProds, 
					()->getProdDataScroll());
			LoadData.<String>loadOnScroll(listMarcas, 
					()->daoMarcas.getAll(listMarcas.getItems().size(), 
				    STEP));
		});
		executor.shutdown();
		
		listMarcas.getSelectionModel().selectedItemProperty().addListener((o,oldValue,newValue)->{
			if(checkFiltrarProdsMarca.isSelected())
				cargarProductos();
		});
		
		listCategorias.getSelectionModel().selectedItemProperty().addListener((o,oldValue,newValue)->{
			if(checkFiltrarProdsCategoria.isSelected())
				cargarProductos();
		});
		
		vtnNewProd = new Stage();
		vtnNewProd.setMinWidth(300);
		vtnNewProd.setMinHeight(300);
		vtnNewProd.initModality(Modality.APPLICATION_MODAL);
		vtnNewProd.setTitle(STRINGS_GUI.getString("title.new.producto"));
		paneNewProd = new NotificationPane();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventario/gui/NewProducto.fxml"),STRINGS_GUI);
		try {
			paneNewProd.setContent(loader.load());
			newProdCtrl = loader.getController();
			vtnNewProd.setScene(new Scene(paneNewProd,300,300));
			newProdCtrl.setStage(vtnNewProd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cargarMarcas();
		cargarProductos();
		
	}
	
	private void cargarProductos() {
		boolean mar = checkFiltrarProdsMarca.isSelected();
		boolean cat = checkFiltrarProdsCategoria.isSelected();
		Producto p = tblProds.getSelectionModel().getSelectedItem();
		tblProds.getSelectionModel().clearSelection();
		ObservableList<Producto> items = tblProds.getItems();
		if(mar && cat) {
			boolean marcaIsNull = listMarcas.getSelectionModel().getSelectedItem() == null;
			boolean categIsNull = listCategorias.getSelectionModel().getSelectedItem() == null;
			if( marcaIsNull && categIsNull)
				items.clear();
			else if(marcaIsNull)
				items.setAll(
						daoCatsProd.getAll(listCategorias.getSelectionModel().getSelectedItem(), 0, 30));
			else if(categIsNull)
				items.setAll(daoProds.getAll(listMarcas.getSelectionModel().getSelectedItem(), 0, 30));
			else
				items.setAll(daoProds.getAll(
						listMarcas.getSelectionModel().getSelectedItem(),
						listCategorias.getSelectionModel().getSelectedItem(), 
						0, 30));
		} else if(mar) {
			items.setAll(daoProds.getAll(
						listMarcas.getSelectionModel().getSelectedItem(), 
						0, 30));
		} else if(cat) {
			items.setAll(daoCatsProd.getAll(
						listCategorias.getSelectionModel().getSelectedItem(), 
						0, 30));
		} else {
			items.setAll(daoProds.getAll(0, 50));
		}
		tblProds.getSelectionModel().select(p);
	}
	
	private void cargarMarcas() {
		String m = listMarcas.getSelectionModel().getSelectedItem();
		listMarcas.getSelectionModel().clearSelection();
		listMarcas.getItems().setAll(daoMarcas.getAll(0, 50));
		listMarcas.getSelectionModel().select(m);
	}
	
	private List<Producto> getProdDataScroll() {
		List<Producto> prods = new LinkedList<>();
		List<Producto> items = tblProds.getItems();
		if(items.size() <= (MAX - STEP) && daoProds != null) {
			int index = items.size();
			switch(filtroProductos) {
			case MARCA:
				prods = daoProds.getAll(
						listMarcas.getSelectionModel().getSelectedItem(), 
						index, STEP);
				break;
			case CATEGORIA:
				prods = daoCatsProd.getAll(
						listCategorias.getSelectionModel().getSelectedItem(), 
						index, STEP);
				break;
			case MARCA_CATEGORIA:
				boolean marcaIsNull = listMarcas.getSelectionModel().getSelectedItem() == null;
				boolean categIsNull = listCategorias.getSelectionModel().getSelectedItem() == null;
				if( !marcaIsNull || !categIsNull) {
					if(marcaIsNull)
						prods = daoCatsProd.getAll(
								listCategorias.getSelectionModel().getSelectedItem(), 
								index, STEP);
					else if(categIsNull)
						prods = daoProds.getAll(
								listMarcas.getSelectionModel().getSelectedItem(), 
								index, STEP);
					else
						prods = daoProds.getAll(
								listMarcas.getSelectionModel().getSelectedItem(),
								listCategorias.getSelectionModel().getSelectedItem(), 
								index, STEP);
				}
				break;
			case NONE:
			default:
				prods = daoProds.getAll(index, STEP);
				break;
			}
		}
		return prods;
	}
	
	
	@FXML
	private void onNuevoProducto(ActionEvent event) {
		newProdCtrl.reset();
		vtnNewProd.centerOnScreen();
		vtnNewProd.showAndWait();
		cargarMarcas();
		cargarProductos();
	}
	
	@FXML
	private void onFiltrar(ActionEvent event) {
		Object source = event.getSource();
		if(source.equals(checkFiltrarProdsMarca) || source.equals(checkFiltrarProdsCategoria)) {
			boolean mar = checkFiltrarProdsMarca.isSelected();
			boolean cat = checkFiltrarProdsCategoria.isSelected();
			if(mar && cat)
				filtroProductos = FiltroProd.MARCA_CATEGORIA;
			else if(mar) 
				filtroProductos = FiltroProd.MARCA;
			else if(cat)
				filtroProductos = FiltroProd.CATEGORIA;
			else 
				filtroProductos = 	FiltroProd.NONE;
			cargarProductos();
		}
	}
	
	@FXML
	private void onProductosContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblProds.getSelectionModel().getSelectedItem() != null;
		menuItemDesProd.setDisable(!haySeleccion);
		menuItemElProd.setDisable(!haySeleccion);
		checkFiltrarProdsMarca.setDisable(listMarcas.getItems().isEmpty());
		checkFiltrarProdsCategoria.setDisable(listCategorias.getItems().isEmpty());
	}
	
	@FXML
	private void onMarcasContextMenuShown(WindowEvent event) {
		menuItemDesMarca.setDisable(listMarcas.getSelectionModel().getSelectedItem() == null);
	}
	
	@FXML
	private void onCategoriasContextMenuShown(WindowEvent event) {
		menuItemDesCategoria.setDisable(listCategorias.getSelectionModel().getSelectedItem() == null);
	}
	
	@FXML
	private void onPresentacionesContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblPres.getSelectionModel().getSelectedItem() != null;
		menuItemDesPresentacion.setDisable(!haySeleccion);
		menuItemElPresentacion.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onLotesContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblLotes.getSelectionModel().getSelectedItem() != null;
		menuItemDesLote.setDisable(!haySeleccion);
		menuItemElLote.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onInventarioContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblInv.getSelectionModel().getSelectedItem() != null;
		menuItemDesInv.setDisable(!haySeleccion);
		menuItemElInv.setDisable(!haySeleccion);
	}

}

enum FiltroProd {
	NONE,
	MARCA,
	CATEGORIA,
	MARCA_CATEGORIA
}
