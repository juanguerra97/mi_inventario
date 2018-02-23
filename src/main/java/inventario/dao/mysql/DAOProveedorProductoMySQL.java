package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOProveedorProducto;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Producto;
import inventario.modelo.Proveedor;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOProveedorProductoMySQL implements DAOProveedorProducto {

	private static final String GET_ALL_PRODS = "SELECT id,nombre,marca FROM Producto AS P "
			+ "INNER JOIN ProveedorProducto AS PP ON P.id = PP.id_producto "
			+ "WHERE PP.nom_proveedor = ?";
	private static final String GET_ALL_PROVS = "SELECT nombre FROM Proveedor AS P "
			+ "INNER JOIN ProveedorProducto AS PP ON P.nombre = PP.nom_proveedor "
			+ "WHERE PP.id_producto = ?";
	private static final String INSERT = "INSERT INTO ProveedorProducto(id_producto,nom_proveedor) "
			+ "VALUES(?,?)";
	private static final String DELETE = "DELETE FROM ProveedorProducto WHERE id_producto = ? "
			+ "AND nom_proveedor = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOProveedorProductoMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<Producto> getAll(Proveedor proveedor, int index, int size) {
		assert proveedor != null : STRINGS_DAO.getString("error.proveedor.null");
		List<Producto> prods = new LinkedList<>();
		if(index < 0 || size <= 0)
			return prods;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_PRODS)){
			st.setString(1, proveedor.getNombre());
			ResultSet rs = st.executeQuery();
			while(rs.next())
				prods.add(new Producto(rs.getLong("id"), rs.getString("nombre"), rs.getString("marca")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return prods;
	}

	@Override
	public List<Proveedor> getAll(long idProducto, int index, int sizze) {
		List<Proveedor> provs = new LinkedList<>();
		if(index < 0 || sizze <= 0)
			return provs;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_PROVS)){
			st.setLong(1, idProducto);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				provs.add(new Proveedor(rs.getString("nombre")));
		} catch (SQLException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return provs;
	}

	@Override
	public boolean insert(long idProducto, Proveedor proveedor) throws DBConnectionException, CannotInsertException {
		assert proveedor != null : STRINGS_DAO.getString("error.proveedor.null");
		if(idProducto <= 0)
			return false;
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, idProducto);
			st.setString(2, proveedor.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.prov_prod.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(long idProducto, Proveedor proveedor) throws DBConnectionException {
		assert proveedor != null : STRINGS_DAO.getString("error.proveedor.null");
		if(idProducto <= 0)
			return false;
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, idProducto);
			st.setString(2, proveedor.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
