package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOMarca;
import inventario.dao.error.DBConnectionException;

public class DAOMarcaMySQL implements DAOMarca {
	
	private static final String GET_ALL = "SELECT DISTINCT(marca) FROM Producto "
			+ "ORDER BY marca ASC LIMIT ?,?";
	private static final String GET_ALL_R = "SELECT DISTINCT(marca) FROM Producto "
			+ "WHERE marca REGEXP(?) ORDER BY marca ASC LIMIT ?,?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOMarcaMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<String> getAll(int index, int size) {
		List<String> marcas = new LinkedList<>();
		if(index < 0 || size <= 0)
			return marcas;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				marcas.add(rs.getString("marca"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marcas;
	}

	@Override
	public List<String> getAll(int index, int size, String regex) {
		assert regex != null : STRINGS_DAO.getString("error.regex.null");
		List<String> marcas = new LinkedList<>();
		if(index < 0 || size <= 0)
			return marcas;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_R)){
			st.setString(1, regex);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				marcas.add(rs.getString("marca"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marcas;
	}

}
