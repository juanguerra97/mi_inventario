package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOCategoriaProducto;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Categoria;
import inventario.modelo.Producto;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOCategoriaProductoMySQL implements DAOCategoriaProducto {

	private static final String GET_ALL_PRODS = "SELECT id,nombre,marca FROM Producto AS P INNER JOIN "
			+ "CategoriaProducto AS CP ON P.id = CP.id_producto WHERE CP.nom_categoria = ? LIMIT ?,?";
	private static final String GET_ALL_CATS = "SELECT nombre FROM Categoria AS C INNER JOIN "
			+ "CategoriaProducto AS CP ON C.nombre = CP.nom_categoria WHERE CP.id_producto = ? LIMIT ?,?";
	private static final String INSERT = "INSERT INTO CategoriaProducto(id_producto,nom_categoria) "
			+ "VALUES(?,?)";
	private static final String DELETE = "DELETE FROM CategoriaProducto WHERE id_producto = ? AND "
			+ "nom_categoria = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOCategoriaProductoMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<Producto> getAll(String categoria, int index, int size) {
		assert categoria != null : STRINGS_DAO.getString("error.categoria.null");
		List<Producto> prods = new LinkedList<>();
		if(index < 0 || size <= 0)
			return prods;
		try (PreparedStatement st = con.prepareStatement(GET_ALL_PRODS)) {
			st.setString(1, categoria);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				prods.add(new Producto(rs.getLong("id"),rs.getString("nombre"), rs.getString("marca")));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return prods;
	}

	@Override
	public List<Categoria> getAll(long idProducto, int index, int size) {
		List<Categoria> cats = new LinkedList<>();
		if(idProducto <= 0 || index < 0 || size <= 0)
			return cats;
		try (PreparedStatement st = con.prepareStatement(GET_ALL_CATS)) {
			st.setLong(1, idProducto);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				cats.add(new Categoria(rs.getString("nombre")));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EmptyArgumentException e) {
			e.printStackTrace();
		}
		return cats;
	}

	@Override
	public boolean insert(long idProducto, String nomCategoria) throws DBConnectionException, 
	CannotInsertException {
		assert nomCategoria != null : STRINGS_DAO.getString("error.categoria.null");
		if(idProducto <= 0 || nomCategoria.isEmpty())
			return false;
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, idProducto);
			st.setString(2, nomCategoria);
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.prod_cat.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return false;
	}

	@Override
	public boolean delete(long idProducto, String nomCategoria) throws DBConnectionException {
		assert nomCategoria != null : STRINGS_DAO.getString("error.categoria.null");
		if(idProducto <= 0)
			return false;
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, idProducto);
			st.setString(2, nomCategoria);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"),e);
		}
		return true;
	}

}
