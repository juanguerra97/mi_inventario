package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOItemCompra;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.ItemCompra;
import inventario.modelo.Transaccion;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOItemCompraMySQL implements DAOItemCompra {
	
	private static final String GET_ALL = "SELECT nom_producto,marca_producto,nom_presentacion,"
			+ "lote_producto,costo_unidad,cantidad,subtotal,nom_proveedor FROM ItemCompra WHERE "
			+ "num_compra = ? ORDER BY marca_producto,nom_producto,nom_presentacion,lote_producto";
	private static final String INSERT = "INSERT INTO ItemCompra(num_compra,nom_producto,marca_producto,"
			+ "nom_presentacion,lote_producto,costo_unidad,cantidad,subtotal,nom_proveedor) "
			+ "VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM ItemCompra WHERE num_compra = ? AND "
			+ "nom_producto = ? AND marca_producto = ? AND nom_presentacion = ? AND lote_producto = ? "
			+ "AND nom_proveedor = ?";
	private static final String UPDATE = "UPDATE ItemCompra SET marca_producto = ?,nom_presentacion = ?,"
			+ "lote_producto = ?,costo_unidad = ?,cantidad = ?,subtotal = ?,nom_proveedor =  ? WHERE "
			+ "num_compra = ? AND nom_producto = ? AND marca_producto = ? AND nom_presentacion = ? AND lote_producto = ? "
			+ "AND nom_proveedor = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOItemCompraMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<ItemCompra> getAll(Transaccion compra) {
		assert compra != null : STRINGS_DAO.getString("error.transaccion.null");
		List<ItemCompra> items = new LinkedList<>();
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setLong(1, compra.getNumero());
			ResultSet rs = st.executeQuery();
			while(rs.next())
				items.add(new ItemCompra(compra.getNumero(), rs.getString("nom_producto"), rs.getString("marca_producto"), 
						rs.getString("nom_presentacion"), rs.getLong("lote_producto"), rs.getBigDecimal("costo_unidad"), 
						rs.getInt("cantidad"), rs.getString("nom_proveedor")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public boolean insert(ItemCompra item) throws DBConnectionException, CannotInsertException {
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
			st.setString(9,item.getProveedor());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.compra.item.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(ItemCompra item) throws DBConnectionException {
		assert item != null : STRINGS_DAO.getString("error.item.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, item.getNumeroTransaccion());
			st.setString(2, item.getNomProducto());
			st.setString(3, item.getMarcaProducto());
			st.setString(4, item.getPresentacionProducto());
			st.setLong(5, item.getLoteProducto());
			st.setString(6,item.getProveedor());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(ItemCompra actual, ItemCompra nuevo) throws DBConnectionException, 
	CannotUpdateException {
		assert actual != null : STRINGS_DAO.getString("error.item.null");
		assert nuevo != null : STRINGS_DAO.getString("error.item.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			
			st.setString(1, nuevo.getMarcaProducto());
			st.setString(2, nuevo.getPresentacionProducto());
			st.setLong(3, nuevo.getLoteProducto());
			st.setBigDecimal(4, nuevo.getValorUnitario());
			st.setInt(5, nuevo.getCantidad());
			st.setBigDecimal(6, nuevo.getSubtotal());
			st.setString(7,nuevo.getProveedor());
			
			st.setLong(8, actual.getNumeroTransaccion());
			st.setString(9, actual.getNomProducto());
			st.setString(10, actual.getMarcaProducto());
			st.setString(11, actual.getPresentacionProducto());
			st.setLong(12, actual.getLoteProducto());
			st.setString(13,actual.getProveedor());
			
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.compra.item.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
