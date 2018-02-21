package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;
import static inventario.modelo.Resources.STRINGS_MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOPresentacion;
import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Presentacion;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOPresentacionMySQL implements DAOPresentacion {
	
	private static final String INSERT = "INSERT INTO Presentacion(id_producto,nombre) VALUES(?,?)";
	private static final String UPDATE = "UPDATE Presentacion SET nombre = ? WHERE id_producto = ? AND nombre = ?";
	private static final String DELETE = "DELETE FROM Presentacion WHERE nombre = ? AND id_producto = ?";
	private static final String GET_ALL = "SELECT DISTINCT(nombre) FROM Presentacion WHERE nombre REGEXP(?) LIMIT ?,?";

	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOPresentacionMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public boolean insert(Presentacion presentacion) throws DBConnectionException, CannotInsertException {
		assert presentacion != null : STRINGS_DAO.getString("error.presentacion.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, presentacion.getIdProducto());
			st.setString(2, presentacion.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.presentacion.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(Presentacion vieja, String newNombre) throws DBConnectionException, CannotUpdateException {
		assert vieja != null : STRINGS_DAO.getString("error.presentacion.null");
		assert newNombre != null : STRINGS_MODELO.getString("error.presentacion.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			st.setString(1, newNombre);
			st.setLong(2, vieja.getIdProducto());
			st.setString(3, vieja.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.presentacion.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(Presentacion presentacion) throws DBConnectionException, CannotDeleteException {
		assert presentacion != null : STRINGS_DAO.getString("error.presentacion.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setString(1, presentacion.getNombre());
			st.setLong(2, presentacion.getIdProducto());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				throw new CannotDeleteException(STRINGS_DAO.getString("error.presentacion.existencias"),e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public List<Presentacion> getAll(int index, int size, String regex) {
		assert regex != null : STRINGS_DAO.getString("error.presentacion.regex.null");
		List<Presentacion> presentaciones = new LinkedList<>();
		if(index < 0 || size <= 0)
			return presentaciones;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setString(1, regex);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				presentaciones.add(new Presentacion(1,rs.getString("nombre")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return presentaciones;
	}

}
