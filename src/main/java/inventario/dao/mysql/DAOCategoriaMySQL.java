package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import inventario.dao.DAOCategoria;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Categoria;
import inventario.modelo.error.EmptyArgumentException;

/**
 * Implementacion de la interfaz DAOCategoria para conexion con base de datos MySQL
 * @author juang
 *
 */
public class DAOCategoriaMySQL implements DAOCategoria {
	
	private static final String GET_ALL = "SELECT nombre FROM Categoria ORDER BY nombre ASC LIMIT ?,?";
	private static final String GET_ALL_R = "SELECT nombre FROM Categoria WHERE nombre REGEXP(?) ORDER BY nombre ASC LIMIT ?,?";
	private static final String INSERT = "INSERT INTO Categoria(nombre) VALUES(?)";
	private static final String DELETE = "DELETE FROM Categoria WHERE nombre = ?";
	private static final String UPDATE = "UPDATE Categoria SET nombre = ? WHERE nombre = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOCategoriaMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<Categoria> getAll(int index, int size) {
		List<Categoria> categorias = new LinkedList<>();
		if(index < 0 || size <= 0)// si algunos de los argumentos son invalidos, se devuelve una lista vacia
			return categorias;		
		try(PreparedStatement st = con.prepareStatement(GET_ALL);){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				try{categorias.add(new Categoria(rs.getString("nombre")));}catch(EmptyArgumentException ex) {ex.printStackTrace();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorias;
	}

	@Override
	public List<Categoria> getAll(int index, int size, String regex) {
		List<Categoria> categorias = new LinkedList<>();
		if(index < 0 || size <= 0)// si algunos de los argumentos son invalidos, se devuelve una lista vacia
			return categorias;		
		try(PreparedStatement st = con.prepareStatement(GET_ALL_R);){
			st.setString(1, regex);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				try{categorias.add(new Categoria(rs.getString("nombre")));}catch(EmptyArgumentException ex) {ex.printStackTrace();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorias;
	}

	@Override
	public boolean insert(Categoria nueva) throws DBConnectionException, CannotInsertException {
		assert nueva != null : STRINGS_DAO.getString("error.categoria.null");
		try(PreparedStatement st = con.prepareStatement(INSERT);){
			st.setString(1, nueva.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null && msg.matches("^Duplicate.*"))
				throw new CannotInsertException(STRINGS_DAO.getString("error.categoria.pk"), e);
			else
				throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(Categoria categoria) throws DBConnectionException {
		assert categoria != null : STRINGS_DAO.getString("error.categoria.null");
		try(PreparedStatement st = con.prepareStatement(DELETE);){
			st.setString(1, categoria.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(Categoria vieja, Categoria nueva) throws DBConnectionException, CannotUpdateException {
		assert vieja != null : STRINGS_DAO.getString("error.categoria.null");
		assert nueva != null : STRINGS_DAO.getString("error.categoria.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE);){
			st.setString(1, nueva.getNombre());
			st.setString(2, vieja.getNombre());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null && msg.matches("^Duplicate.*"))
				throw new CannotUpdateException(STRINGS_DAO.getString("error.categoria.pk"), e);
			else
				throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return false;
	}

}
