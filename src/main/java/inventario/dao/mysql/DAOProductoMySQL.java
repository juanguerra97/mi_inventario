package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;
import static inventario.modelo.Resources.STRINGS_MODELO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import inventario.dao.DAOProducto;
import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Producto;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOProductoMySQL implements DAOProducto {
	
	private static final String GET = "SELECT nombre,marca FROM Producto WHERE id = ?";
	private static final String GET_II = "SELECT id FROM Producto WHERE nombre = ? AND marca = ?";
	private static final String GET_ALL = "SELECT id,nombre,marca FROM Producto "
			+ "ORDER BY id ASC,nombre,marca LIMIT ?,?";
	private static final String GET_ALL_II = "SELECT id,nombre FROM Producto "
			+ "WHERE marca = ? ORDER BY id ASC,nombre LIMIT ?,?";
	private static final String GET_ALL_III = "SELECT id,nombre,marca FROM Producto "
			+ "WHERE nombre REGEXP(?) ORDER BY id ASC,nombre,marca LIMIT ?,?";
	private static final String INSERT = "INSERT INTO Producto(id,nombre,marca) VALUES(?,?,?)";
	private static final String INSERT_II = "INSERT INTO Producto(nombre,marca) VALUES(?,?)";
	private static final String DELETE = "DELETE FROM Producto WHERE id = ?";
	private static final String UPDATE_ALL = "UPDATE Producto SET id = ?, nombre = ?, marca = ? "
			+ "WHERE id = ?";
	private static final String UPDATE = "UPDATE Producto SET nombre = ?, marca = ? WHERE id = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOProductoMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public Optional<Producto> get(long id) {
		Producto prod = null;
		try(PreparedStatement st = con.prepareStatement(GET)){
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			if(rs.next())
				prod = new Producto(id,rs.getString("nombre"),rs.getString("marca"));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(prod);
	}

	@Override
	public Optional<Producto> get(String nombre, String marca) {
		assert nombre != null : STRINGS_MODELO.getString("error.nombre_producto.null") ;
		assert marca != null : STRINGS_MODELO.getString("error.marca_producto.null") ;
		Producto prod = null;
		try(PreparedStatement st = con.prepareStatement(GET_II)){
			st.setString(1, nombre);
			st.setString(2, marca);
			ResultSet rs = st.executeQuery();
			if(rs.next())
				prod = new Producto(rs.getLong("id"),nombre,marca);
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(prod);
	}

	@Override
	public List<Producto> getAll(int start, int size) {
		List<Producto> productos = new LinkedList<>();
		if(start < 0 || size <= 0)
			return productos;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setInt(1, start);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				productos.add(new Producto(rs.getLong("id"),rs.getString("nombre"), rs.getString("marca")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return productos;
	}

	@Override
	public List<Producto> getAll(String marca, int start, int size) {
		assert marca != null : STRINGS_DAO.getString("error.producto.marca.null") ;
		List<Producto> productos = new LinkedList<>();
		if(start < 0 || size <= 0)
			return productos;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_II)){
			st.setString(1, marca);
			st.setInt(2, start);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				productos.add(new Producto(rs.getLong("id"),rs.getString("nombre"), marca));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return productos;
	}

	@Override
	public List<Producto> getAll(int start, int size, String regex) {
		assert regex != null : STRINGS_DAO.getString("error.nom_producto.regex.null") ;
		List<Producto> productos = new LinkedList<>();
		if(start < 0 || size <= 0)
			return productos;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_III)){
			st.setString(1, regex);
			st.setInt(1, start);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				productos.add(new Producto(rs.getLong("id"),rs.getString("nombre"), rs.getString("marca")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return productos;
	}

	@Override
	public boolean insert(Producto producto) throws DBConnectionException, CannotInsertException {
		assert producto != null : STRINGS_DAO.getString("error.producto.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, producto.getId());
			st.setString(2, producto.getNombre());
			st.setString(3, producto.getMarca());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.producto.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public long insertAndGet(Producto producto) throws DBConnectionException, CannotInsertException {
		assert producto != null : STRINGS_DAO.getString("error.producto.null");
		long id = -1;
		try(PreparedStatement st = con.prepareStatement(INSERT_II, Statement.NO_GENERATED_KEYS)){
			st.setString(1, producto.getNombre());
			st.setString(2, producto.getMarca());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next())
				id = rs.getLong(1);
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.producto.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return id;
	}

	@Override
	public boolean delete(long idProducto) throws DBConnectionException, CannotDeleteException {
		if(idProducto <= 0)
			return false;
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, idProducto);
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^NO se puede eliminar.*"))
					throw new CannotDeleteException(STRINGS_DAO.getString("error.del_producto.existencias"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(Producto viejo, Producto nuevo) throws DBConnectionException, CannotUpdateException {
		assert viejo != null : STRINGS_DAO.getString("error.producto.null");
		assert nuevo != null : STRINGS_DAO.getString("error.producto.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE_ALL)){
			st.setLong(1, nuevo.getId());
			st.setString(2, nuevo.getNombre());
			st.setString(3, nuevo.getMarca());
			st.setLong(4, viejo.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.producto.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(long idProducto, Producto nuevo) throws DBConnectionException, CannotUpdateException {
		assert nuevo != null : STRINGS_DAO.getString("error.producto.null");
		if(idProducto <= 0)
			return false;
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			st.setString(1, nuevo.getNombre());
			st.setString(2, nuevo.getMarca());
			st.setLong(3, idProducto);
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.producto.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
