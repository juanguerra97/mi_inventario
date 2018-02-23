package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOProveedor;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Proveedor;
import inventario.modelo.error.EmptyArgumentException;

public class DAOProveedorMySQL implements DAOProveedor {
	
	private static final String GET_ALL = "SELECT nombre FROM Proveedor ORDER BY nombre LIMIT ?,?";
	private static final String INSERT = "INSERT INTO Proveedor(nombre) VALUES(?)";
	private static final String DELETE = "DELETE FROM Proveedor WHERE nombre = ?";
	private static final String UPDATE = "UPDATE Proveedor SET nombre = ? WHERE nombre = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOProveedorMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<Proveedor> getAll(int index, int size) {
		List<Proveedor> provs = new LinkedList<>();
		if(index < 0 || size <= 0)
			return provs;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				provs.add(new Proveedor(rs.getString("nombre")));
		} catch (SQLException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return provs;
	}

	@Override
	public boolean insert(Proveedor proveedor) throws DBConnectionException, CannotInsertException {
		assert proveedor != null : STRINGS_DAO.getString("error.proveedor.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setString(1, proveedor.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.proveedor.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(Proveedor proveedor) throws DBConnectionException {
		assert proveedor != null : STRINGS_DAO.getString("error.proveedor.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setString(1, proveedor.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(Proveedor viejo, Proveedor nuevo) throws DBConnectionException, CannotUpdateException {
		assert viejo != null : STRINGS_DAO.getString("error.proveedor.null");
		assert nuevo != null : STRINGS_DAO.getString("error.proveedor.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			st.setString(1, nuevo.getNombre());
			st.setString(2, viejo.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.proveedor.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
