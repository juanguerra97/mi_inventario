package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

import inventario.dao.DAOItemVenta;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.ItemVenta;
import inventario.modelo.Transaccion;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOItemVentaMySQL implements DAOItemVenta {
	
	private static final String GET_ALL = "SELECT nom_producto,marca_producto,nom_presentacion,"
			+ "lote_producto,precio_unidad,cantidad,subtotal,dpi_cliente,nombre_cliente FROM ItemVenta WHERE "
			+ "num_venta = ? ORDER BY marca_producto,nom_producto,nom_presentacion,lote_producto";
	private static final String INSERT = "INSERT INTO ItemVenta(num_venta,nom_producto,marca_producto,"
			+ "nom_presentacion,lote_producto,precio_unidad,cantidad,subtotal,dpi_cliente,nombre_cliente) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM ItemVenta WHERE num_venta = ? AND "
			+ "nom_producto = ? AND marca_producto = ? AND nom_presentacion = ? AND lote_producto = ? ";
	private static final String UPDATE = "UPDATE ItemVenta SET marca_producto = ?,nom_presentacion = ?,"
			+ "lote_producto = ?,precio_unidad = ?,cantidad = ?,subtotal = ?,dpi_cliente =  ?,nombre_cliente WHERE "
			+ "num_venta = ? AND nom_producto = ? AND marca_producto = ? AND nom_presentacion = ? AND lote_producto = ? ";

	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOItemVentaMySQL(Connection conexionMySQL) throws DBConnectionException {
		assert conexionMySQL != null : STRINGS_DAO.getString("error.conexion.null");
		boolean isClosed = true;
		try {
			isClosed = conexionMySQL.isClosed();
		} catch (SQLException e) {
			isClosed = true;
		}
		if(isClosed)
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion.closed"));
		con = conexionMySQL;
	}

	
	@Override
	public List<ItemVenta> getAll(Transaccion venta) {
		assert venta != null : STRINGS_DAO.getString("error.transaccion.null");
		List<ItemVenta> items = new LinkedList<>();
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setLong(1, venta.getNumero());
			ResultSet rs = st.executeQuery();
			while(rs.next())
				items.add(new ItemVenta(venta.getNumero(), rs.getString("nom_producto"), rs.getString("marca_producto"), 
						rs.getString("nom_presentacion"), rs.getLong("lote_producto"), rs.getBigDecimal("precio_unidad"), 
						rs.getInt("cantidad"), rs.getString("dpi_cliente"),rs.getString("nombre_cliente")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException | DataFormatException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public boolean insert(ItemVenta item) throws DBConnectionException, CannotInsertException {
		assert item != null : STRINGS_DAO.getString("error.item.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, item.getNumeroTransaccion());
			st.setString(2, item.getNomProducto());
			st.setString(3, item.getMarcaProducto());
			st.setString(4, item.getPresentacionProducto());
			st.setLong(5, item.getLoteProducto());
			st.setBigDecimal(6, item.getValorUnitario());
			st.setInt(7, item.getCantidad());
			st.setBigDecimal(8, item.getSubtotal());
			st.setString(9,item.getDpiCliente());
			st.setString(10,item.getNombreCliente());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.venta.item.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(ItemVenta item) throws DBConnectionException {
		assert item != null : STRINGS_DAO.getString("error.item.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, item.getNumeroTransaccion());
			st.setString(2, item.getNomProducto());
			st.setString(3, item.getMarcaProducto());
			st.setString(4, item.getPresentacionProducto());
			st.setLong(5, item.getLoteProducto());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(ItemVenta actual, ItemVenta nuevo) throws DBConnectionException, CannotUpdateException {
		assert actual != null : STRINGS_DAO.getString("error.item.null");
		assert nuevo != null : STRINGS_DAO.getString("error.item.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			
			st.setString(1, nuevo.getMarcaProducto());
			st.setString(2, nuevo.getPresentacionProducto());
			st.setLong(3, nuevo.getLoteProducto());
			st.setBigDecimal(4, nuevo.getValorUnitario());
			st.setInt(5, nuevo.getCantidad());
			st.setBigDecimal(6, nuevo.getSubtotal());
			st.setString(7,nuevo.getDpiCliente());
			st.setString(8, nuevo.getNombreCliente());
			
			st.setLong(9, actual.getNumeroTransaccion());
			st.setString(10, actual.getNomProducto());
			st.setString(11, actual.getMarcaProducto());
			st.setString(12, actual.getPresentacionProducto());
			st.setLong(13, actual.getLoteProducto());
			
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.venta.item.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
