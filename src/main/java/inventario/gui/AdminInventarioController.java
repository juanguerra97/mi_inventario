package inventario.gui;

import static inventario.gui.Resources.STRINGS_GUI;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.table.TableRowExpanderColumn;
import org.controlsfx.control.textfield.TextFields;

import inventario.dao.DAOCategoria;
import inventario.dao.DAOCategoriaProducto;
import inventario.dao.DAOMarca;
import inventario.dao.DAOPresentacion;
import inventario.dao.DAOPresentacionProducto;
import inventario.dao.DAOProducto;
import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.dao.mysql.Conexion;
import inventario.dao.mysql.DAOCategoriaMySQL;
import inventario.dao.mysql.DAOCategoriaProductoMySQL;
import inventario.dao.mysql.DAOMarcaMySQL;
import inventario.dao.mysql.DAOPresentacionMySQL;
import inventario.dao.mysql.DAOPresentacionProductoMySQL;
import inventario.dao.mysql.DAOProductoMySQL;
import inventario.modelo.Categoria;
import inventario.modelo.Lote;
import inventario.modelo.Presentacion;
import inventario.modelo.PresentacionLote;
import inventario.modelo.Producto;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AdminInventarioController {
	
	private static final int MAX = 500, STEP = 10;
	
	@FXML private ListView<String> listMarcas;
	@FXML private MenuItem menuItemDesMarca;
	
	@FXML private ListView<String> listCategorias;
	@FXML private MenuItem menuItemDesCategoria;
	@FXML private MenuItem menuItemElCategoria;
	
	@FXML private TableView<Producto> tblProds;
	@FXML private TableColumn<Producto,Long> colIdProd;
	@FXML private TableColumn<Producto,String> colNomProd;
	@FXML private TableColumn<Producto,String> colMarcaProd;
	
	@FXML private MenuItem menuItemDesProd;
	@FXML private MenuItem menuItemElProd;
	@FXML private SeparatorMenuItem separatorMItCat;
	@FXML private MenuItem menuItemAnadirCat;
	@FXML private MenuItem menuItemQuitCategoria;
	@FXML private CheckMenuItem checkFiltrarProdsMarca;
	@FXML private CheckMenuItem checkFiltrarProdsCategoria;
	
	@FXML private TableView<Presentacion> tblPres;
	@FXML private TableColumn<Presentacion,String> colNomPres;
	@FXML private MenuItem menuItemNewPresentacion;
	@FXML private MenuItem menuItemDesPresentacion;
	@FXML private MenuItem menuItemElPresentacion;
	
	@FXML private TableView<Lote> tblLotes;
	@FXML private TableColumn<Lote,Long> colNumLote;
	@FXML private TableColumn<Lote,LocalDate> colFechaLote;
	@FXML private MenuItem menuItemNewLote;
	@FXML private MenuItem menuItemDesLote;
	@FXML private MenuItem menuItemElLote;
	
	@FXML private TableView<PresentacionLote> tblInv;
	@FXML private TableColumn<PresentacionLote,Long> colLotInv;
	@FXML private TableColumn<PresentacionLote,String> colPresInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colPrecInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colCostInv;
	@FXML private TableColumn<PresentacionLote,Integer> colCantInv;
	@FXML private MenuItem menuItemNewInv;
	@FXML private MenuItem menuItemDesInv;
	@FXML private MenuItem menuItemElInv;
	
	private Stage vtnNewProd;
	private NotificationPane paneNewProd;
	private NewProductoController newProdCtrl;
	
	private Stage vtnNewCat;
	private NotificationPane paneNewCat;
	private NewCategoriaController newCatCtrl;
	
	private Stage vtnNewPres;
	private NotificationPane paneNewPres;
	private NewPresentacionController newPresCtrl;
	
	private DAOProducto daoProds = null;
	private DAOCategoriaProducto daoCatsProd = null;
	private DAOMarca daoMarcas = null;
	private DAOCategoria daoCategorias = null;
	private DAOPresentacion daoPresentaciones = null;
	private DAOPresentacionProducto daoPresProd = null;
	
	FiltroProd filtroProductos = FiltroProd.NONE;
	
	private static ButtonType BTN_ELIMINAR = new ButtonType(STRINGS_GUI.getString("eliminar"));
	private static ButtonType BTN_CANCEL = new ButtonType(STRINGS_GUI.getString("cancelar"));
	private static final Alert CONFIRM = new Alert(AlertType.CONFIRMATION,
			STRINGS_GUI.getString("producto.delete.confirm"), BTN_ELIMINAR,BTN_CANCEL);
	
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {
		
		try {
			daoProds = new DAOProductoMySQL(Conexion.get());
			daoCatsProd = new DAOCategoriaProductoMySQL(Conexion.get());
			daoMarcas = new DAOMarcaMySQL(Conexion.get());
			daoCategorias = new DAOCategoriaMySQL(Conexion.get());
			daoPresentaciones = new DAOPresentacionMySQL(Conexion.get());
			daoPresProd = new DAOPresentacionProductoMySQL(Conexion.get());
		} catch (DBConnectionException | SQLException ex) {
			ex.printStackTrace();
		}
		
		CONFIRM.setTitle(STRINGS_GUI.getString("confirm"));
		CONFIRM.setHeaderText(STRINGS_GUI.getString("producto.delete.warning"));
		
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
		
		TableRowExpanderColumn<Producto> expandColProductos = new TableRowExpanderColumn<>(this::createEditor);
		expandColProductos.setMinWidth(25);
		expandColProductos.setMaxWidth(25);
		tblProds.getColumns().setAll(expandColProductos,colIdProd,colNomProd,colMarcaProd);
	
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
		
		tblProds.getSelectionModel().selectedItemProperty().addListener((o,oldValue,newValue)->{
			boolean newIsNull = newValue == null;
			tblPres.setDisable(newIsNull);
			tblLotes.setDisable(newIsNull);
			tblInv.setDisable(newIsNull);
			if(newIsNull) {
				tblPres.getItems().clear();
			}else {
				cargarPresentaciones(newValue);
			}
		});
		
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
		
		vtnNewCat = new Stage();
		vtnNewCat.setMinWidth(380);
		vtnNewCat.setMaxWidth(800);
		vtnNewCat.setMinHeight(70);
		vtnNewCat.setMaxHeight(120);
		vtnNewCat.initModality(Modality.APPLICATION_MODAL);
		vtnNewCat.setTitle(STRINGS_GUI.getString("title.new.categoria"));
		paneNewCat = new NotificationPane();
		
		vtnNewPres = new Stage();
		vtnNewPres.setMinWidth(380);
		vtnNewPres.setMaxWidth(800);
		vtnNewPres.setMinHeight(70);
		vtnNewPres.setMaxHeight(120);
		vtnNewPres.initModality(Modality.APPLICATION_MODAL);
		vtnNewPres.setTitle(STRINGS_GUI.getString("title.new.presentacion"));
		paneNewPres = new NotificationPane();
		
		FXMLLoader loaderNewProd = new FXMLLoader(getClass().getResource("/inventario/gui/NewProducto.fxml"),STRINGS_GUI);
		FXMLLoader loaderNewCat = new FXMLLoader(getClass().getResource("/inventario/gui/NewCategoria.fxml"),STRINGS_GUI);
		FXMLLoader loaderNewPres = new FXMLLoader(getClass().getResource("/inventario/gui/NewPresentacion.fxml"),STRINGS_GUI);
		try {
			paneNewProd.setContent(loaderNewProd.load());
			newProdCtrl = loaderNewProd.getController();
			vtnNewProd.setScene(new Scene(paneNewProd,300,300));
			newProdCtrl.setStage(vtnNewProd);
			
			paneNewCat.setContent(loaderNewCat.load());
			newCatCtrl = loaderNewCat.getController();
			vtnNewCat.setScene(new Scene(paneNewCat,380,85));
			newCatCtrl.setStage(vtnNewCat);
			
			paneNewPres.setContent(loaderNewPres.load());
			newPresCtrl = loaderNewPres.getController();
			vtnNewPres.setScene(new Scene(paneNewPres,380,85));
			newPresCtrl.setStage(vtnNewPres);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cargarMarcas();
		cargarCategorias();
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
		if(items.contains(p))
			tblProds.getSelectionModel().select(p);
	}
	
	private void cargarMarcas() {
		String m = listMarcas.getSelectionModel().getSelectedItem();
		listMarcas.getSelectionModel().clearSelection();
		listMarcas.getItems().setAll(daoMarcas.getAll(0, 50));
		listMarcas.getSelectionModel().select(m);
		listMarcas.setDisable(listMarcas.getItems().isEmpty());
	}
	
	private void cargarCategorias() {
		String c = listCategorias.getSelectionModel().getSelectedItem();
		listCategorias.getSelectionModel().clearSelection();
		listCategorias.getItems().setAll(
				daoCategorias.getAll(0, 50)
				.stream().map(cat->cat.toString()).collect(Collectors.toList()));
		listCategorias.getSelectionModel().select(c);
	}
	
	private void cargarPresentaciones(Producto prod) {
		assert prod != null;
		Presentacion p = tblPres.getSelectionModel().getSelectedItem();
		tblPres.getSelectionModel().clearSelection();
		tblPres.getItems().setAll(
				daoPresProd.getAll(prod.getId(), 0, 50));
		tblPres.getSelectionModel().select(p);
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
	private void onNuevaCategoria(ActionEvent event) {
		newCatCtrl.reset();
		vtnNewCat.centerOnScreen();
		vtnNewCat.showAndWait();
		cargarCategorias();
	}
	
	@FXML
	private void onNuevaPresentacion(ActionEvent event) {
		Producto prod = tblProds.getSelectionModel().getSelectedItem();
		newPresCtrl.reset();
		newPresCtrl.setProducto(prod);
		vtnNewPres.centerOnScreen();
		vtnNewPres.showAndWait();
		cargarPresentaciones(prod);
	}
	
	@FXML
	private void onEliminarProducto(ActionEvent event) {
		Producto prod = tblProds.getSelectionModel().getSelectedItem();
		CONFIRM.showAndWait().filter(response -> response.equals(BTN_ELIMINAR)).ifPresent(response->{
			Connection con = null;
			boolean eliminado = false;
			try {
				con = Conexion.get();
				con.setAutoCommit(false);
				daoProds.delete(prod.getId());
				eliminado = true;
				String msgExito = STRINGS_GUI.getString("msg.delete.producto");
				Main.mostrarMensaje(msgExito.replaceAll("\\{\\{P\\}\\}", "\"" + prod.getNombre() + "\""));
				cargarMarcas();
				tblProds.getSelectionModel().clearSelection();
				tblProds.getItems().remove(prod);
			} catch (DBConnectionException | SQLException e) {
				Main.mostrarMensaje(e.getMessage());
			} catch (CannotDeleteException e) {
				Main.mostrarMensaje(e.getMessage());
			}finally {
				if(con != null) {
					try {
						if(eliminado)
							con.commit();
						else
							con.rollback();
					} catch (SQLException e) {}
				}
			}
		});
	}
	
	@FXML
	private void onEliminarPresentacion(ActionEvent event) {
		Presentacion presentacion = tblPres.getSelectionModel().getSelectedItem();
		CONFIRM.showAndWait().filter(response -> response.equals(BTN_ELIMINAR)).ifPresent(response->{
			try {
				Producto prod = tblProds.getSelectionModel().getSelectedItem();
				daoPresentaciones.delete(presentacion);
				String msgExito = STRINGS_GUI.getString("msg.delete.presentacion");
				Main.mostrarMensaje(msgExito.replaceAll("\\{\\{Pr\\}\\}", "\"" + presentacion.getNombre() + "\"")
						.replaceAll("\\{\\{P\\}\\}", "\"" + prod.getNombre()  + "\""));
				tblPres.getSelectionModel().clearSelection();
				tblPres.getItems().remove(presentacion);
			} catch (DBConnectionException e) {
				Main.mostrarMensaje(e.getMessage());
			} catch (CannotDeleteException e) {
				Main.mostrarMensaje(e.getMessage());
			}
		});
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
		Producto prod = tblProds.getSelectionModel().getSelectedItem();
		boolean haySeleccion = prod != null;
		
		menuItemDesProd.setDisable(!haySeleccion);
		menuItemElProd.setDisable(!haySeleccion);
		
		checkFiltrarProdsMarca.setDisable(listMarcas.getItems().isEmpty());
		checkFiltrarProdsCategoria.setDisable(listCategorias.getItems().isEmpty());
		
		String categoria = listCategorias.getSelectionModel().getSelectedItem();
		if(categoria == null) {
			menuItemAnadirCat.setVisible(false);
			menuItemQuitCategoria.setVisible(false);
			return;
		}
		
		boolean anadirVisible = false;
		boolean quitarVisible = false;
		try {
			
			Categoria cSelected = new Categoria(categoria);
			if (prod != null) {
				boolean hasCat = daoCatsProd.getAll(prod.getId(), 0, 500).stream().anyMatch(c -> c.equals(cSelected));
				if(hasCat) {
					menuItemQuitCategoria.setText(STRINGS_GUI.getString("quitarde") + "\"" + cSelected + "\"");
					quitarVisible = true;
				} else {
					menuItemAnadirCat.setText(STRINGS_GUI.getString("anadira") + " \"" + cSelected + "\"");
					anadirVisible = true;
				}
			}
		} catch (EmptyArgumentException e1) {
			e1.printStackTrace();
		}
		
		separatorMItCat.setVisible(anadirVisible || quitarVisible);
		menuItemAnadirCat.setVisible(anadirVisible);
		menuItemAnadirCat.setDisable(!anadirVisible);
		menuItemQuitCategoria.setVisible(quitarVisible);
		menuItemQuitCategoria.setDisable(!quitarVisible);
		
	}
	
	@FXML
	private void onMarcasContextMenuShown(WindowEvent event) {
		menuItemDesMarca.setDisable(listMarcas.getSelectionModel().getSelectedItem() == null);
	}
	
	@FXML
	private void onCategoriasContextMenuShown(WindowEvent event) {
		boolean catIsNull = listCategorias.getSelectionModel().getSelectedItem() == null;
		menuItemDesCategoria.setDisable(catIsNull);
		menuItemElCategoria.setDisable(catIsNull);
	}
	
	@FXML
	private void onPresentacionesContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblPres.getSelectionModel().getSelectedItem() != null;
		menuItemNewPresentacion.setDisable(tblProds.getSelectionModel().getSelectedItem() == null);
		menuItemDesPresentacion.setDisable(!haySeleccion);
		menuItemElPresentacion.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onLotesContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblLotes.getSelectionModel().getSelectedItem() != null;
		menuItemNewLote.setDisable(tblProds.getSelectionModel().getSelectedItem() == null);
		menuItemDesLote.setDisable(!haySeleccion);
		menuItemElLote.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onInventarioContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblInv.getSelectionModel().getSelectedItem() != null;
		menuItemNewInv.setDisable(tblPres.getSelectionModel().getSelectedItem() == null || 
				tblLotes.getSelectionModel().getSelectedItem() == null);
		menuItemDesInv.setDisable(!haySeleccion);
		menuItemElInv.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onDeselectMarca(ActionEvent event) {
		listMarcas.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void onDeselectCategoria(ActionEvent event) {
		listCategorias.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void onDeselectProducto(ActionEvent event) {
		tblProds.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void onDeselectPresentacion(ActionEvent event) {
		tblPres.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void onDeselectLote(ActionEvent event) {
		tblLotes.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void onDeselectInventario(ActionEvent event) {
		tblInv.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void onAnadirCategoria(ActionEvent event) {
		Producto prod = tblProds.getSelectionModel().getSelectedItem();
		String cat = listCategorias.getSelectionModel().getSelectedItem();
		try {
			daoCatsProd.insert(prod.getId(), cat);
			String msgExito = STRINGS_GUI.getString("msg.insert.cat_prod");
			msgExito = msgExito.replaceAll("\\{\\{P\\}\\}", "\"" + prod.getNombre() + "\"");
			msgExito = msgExito.replaceAll("\\{\\{C\\}\\}", "\"" + cat + "\"");
			Main.mostrarMensaje(msgExito);
		} catch (DBConnectionException e) {
			Main.mostrarMensaje(e.getMessage());
		} catch (CannotInsertException e) {
			Main.mostrarMensaje(e.getMessage());
		}
	}
	
	@FXML
	private void onQuitarCategoria(ActionEvent event) {
		Producto prod = tblProds.getSelectionModel().getSelectedItem();
		String cat = listCategorias.getSelectionModel().getSelectedItem();
		try {
			daoCatsProd.delete(prod.getId(), cat);
			String msgExito = STRINGS_GUI.getString("msg.quitar.cat_prod");
			msgExito = msgExito.replaceAll("\\{\\{P\\}\\}", "\"" + prod.getNombre() + "\"");
			msgExito = msgExito.replaceAll("\\{\\{C\\}\\}", "\"" + cat + "\"");
			if(filtroProductos == FiltroProd.CATEGORIA || filtroProductos == FiltroProd.MARCA_CATEGORIA) {
				tblProds.getItems().remove(prod);
			}
			Main.mostrarMensaje(msgExito);
		} catch (DBConnectionException e) {
			Main.mostrarMensaje(e.getMessage());
		}
	}
	
	@FXML
	private void onEliminarCategoria(ActionEvent event) {
		Connection con = null;
		boolean eliminados = true;
		try {
			con = Conexion.get();
			con.setAutoCommit(false);
			Categoria categoria = new Categoria(listCategorias.getSelectionModel().getSelectedItem());
			daoCategorias.delete(categoria);
			String msgExito = STRINGS_GUI.getString("msg.delete.categoria");
			listCategorias.getItems().remove(categoria.toString());
			listCategorias.getSelectionModel().clearSelection();
			Main.mostrarMensaje(msgExito.replaceAll("\\{\\{C\\}\\}", "\"" + categoria + "\""));
		} catch (EmptyArgumentException e) {} 
		catch (DBConnectionException | SQLException e) {
			Main.mostrarMensaje(e.getMessage());
		} finally {
			if(con != null) {
				try {
					if(eliminados)
						con.commit();
					else
						con.rollback();
				} catch (SQLException e) {}
			}
		}
	}
	
	private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Producto> param) {
		
        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        Producto producto = param.getValue();
        tblProds.getSelectionModel().select(producto);

        TextField fieldID = TextFields.createClearableTextField();
        TextField fieldNombre = TextFields.createClearableTextField();
        TextField fieldMarca = TextFields.createClearableTextField();
        
        fieldID.setText(""+producto.getId());
        fieldNombre.setText(producto.getNombre());
        fieldMarca.setText(producto.getMarca());
        
		// evento de autocompletado para el campo de marca
		TextFields.bindAutoCompletion(fieldMarca,
				suggestionRequest -> daoMarcas.getAll(0, 3, "^" + suggestionRequest.getUserText().trim() + ".*"));

        editor.addRow(0, new Label(STRINGS_GUI.getString("id")),fieldID);
        editor.addRow(1, new Label(STRINGS_GUI.getString("nombre")), fieldNombre);
        editor.addRow(2, new Label(STRINGS_GUI.getString("marca")), fieldMarca);

        Button btnGuardar = new Button(STRINGS_GUI.getString("guardar"));
        btnGuardar.setOnAction(event -> {
        	String newNombre = fieldNombre.getText().trim();
        	newNombre = newNombre.toUpperCase();
        	String newMarca = fieldMarca.getText().trim();
        	newMarca = newMarca.toUpperCase();
        	try {
				Producto actualizado = new Producto(Long.parseLong(fieldID.getText().trim()),newNombre,newMarca);
				if(producto.getId() != actualizado.getId() || 
						!producto.getNombre().equals(actualizado.getNombre()) ||
						!producto.getMarca().equals(actualizado.getMarca())) {
					daoProds.update(producto, actualizado);
					if((filtroProductos == FiltroProd.MARCA || filtroProductos == FiltroProd.MARCA_CATEGORIA) &&
							!producto.getMarca().equals(actualizado.getMarca())) {
						tblProds.getSelectionModel().clearSelection();
						tblProds.getItems().remove(producto);
						cargarMarcas();
					}
					producto.setId(actualizado.getId());
					producto.setNombre(actualizado.getNombre());
					producto.setMarca(actualizado.getMarca());
					Main.mostrarMensaje(STRINGS_GUI.getString("msg.update.producto"));
				}
				param.toggleExpanded();
			} catch (NumberFormatException e) {
				Main.mostrarMensaje(STRINGS_GUI.getString("error.numberformat").replaceAll("\\{\\{C\\}\\}", "ID"));
				fieldID.selectAll();
				fieldID.requestFocus();
			} catch (ModelConstraintViolationException | EmptyArgumentException e) {
				Main.mostrarMensaje(e.getMessage());
			} catch (DBConnectionException | CannotUpdateException e) {
				Main.mostrarMensaje(e.getMessage());
			}
        });

        Button btnCancelar = new Button(STRINGS_GUI.getString("cancelar"));
        btnCancelar.setOnAction(event -> {
        	param.toggleExpanded();
        });

        editor.addRow(3, btnGuardar, btnCancelar);

        return editor;
    }
	
}

enum FiltroProd {
	NONE,
	MARCA,
	CATEGORIA,
	MARCA_CATEGORIA
}
