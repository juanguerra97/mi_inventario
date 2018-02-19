package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;
import static inventario.modelo.Resources.STRINGS_MODELO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import inventario.dao.DAOCliente;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Cliente;
import inventario.modelo.Direccion;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOClienteMySQL implements DAOCliente {
	
	private static final String GET = "SELECT nombre, apellido, telefono, email, departamento, ciudad, zona "
			+ "FROM Cliente WHERE dpi = ?";
	private static final String GET_ALL = "SELECT dpi,nombre, apellido, telefono, email, departamento, ciudad, zona "
			+ "FROM Cliente ORDER BY dpi,nombre,apellido LIMIT ?,?";
	private static final String GET_ALL_ACT = "SELECT dpi,nombre, apellido, telefono, email, departamento, ciudad, zona "
			+ "FROM Cliente WHERE activo = TRUE ORDER BY dpi,nombre,apellido LIMIT ?,?";
	private static final String GET_ALL_N_ACT = "SELECT dpi,nombre, apellido, telefono, email, departamento, ciudad, zona "
			+ "FROM Cliente WHERE activo = FALSE ORDER BY dpi,nombre,apellido LIMIT ?,?";
	private static final String INSERT = "INSERT INTO Cliente(dpi,nombre,apellido,telefono,email,departamento,ciudad,zona) "
			+ "VALUES(?,?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM Cliente WHERE dpi = ?";
	private static final String UPDATE = "UPDATE Cliente SET nombre = ?, apellido = ?, telefono = ?, email = ?, "
			+ "departamento = ?, ciudad = ?, zona = ? WHERE dpi = ?";
	private static final String ACTIVATE = "UPDATE Cliente SET activo = TRUE WHERE dpi = ?";
	private static final String DEACTIVATE = "UPDATE Cliente SET activo = FALSE WHERE dpi = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOClienteMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public Optional<Cliente> get(String dpi) {
		assert dpi != null : STRINGS_MODELO.getString("error.dpi_cliente.null");
		Cliente cli = null;
		try(PreparedStatement st = con.prepareStatement(GET)){
			st.setString(1, dpi);
			ResultSet rs = st.executeQuery();
			if(rs.next())
				cli = new Cliente(dpi, rs.getString("nombre"), rs.getString("apellido"), 
						rs.getString("telefono"), rs.getString("email"), 
						new Direccion(rs.getString("departamento"), rs.getString("ciudad"), rs.getByte("zona")));
		} catch (SQLException | DataFormatException | EmptyArgumentException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(cli);
	}

	@Override
	public List<Cliente> getAll(int index, int size) {
		List<Cliente> clientes = new LinkedList<>();
		if(index < 0 || size <= 0)
			return clientes;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				clientes.add(new Cliente(rs.getString("dpi"), rs.getString("nombre"), rs.getString("apellido"), 
						rs.getString("telefono"), rs.getString("email"), 
						new Direccion(rs.getString("departamento"), rs.getString("ciudad"), rs.getByte("zona"))));
		} catch (SQLException | DataFormatException | EmptyArgumentException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public List<Cliente> getAllActive(int index, int size) {
		List<Cliente> clientes = new LinkedList<>();
		if(index < 0 || size <= 0)
			return clientes;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_ACT)){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				clientes.add(new Cliente(rs.getString("dpi"), rs.getString("nombre"), rs.getString("apellido"), 
						rs.getString("telefono"), rs.getString("email"), 
						new Direccion(rs.getString("departamento"), rs.getString("ciudad"), rs.getByte("zona"))));
		} catch (SQLException | DataFormatException | EmptyArgumentException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public List<Cliente> getAllNotActive(int index, int size) {
		List<Cliente> clientes = new LinkedList<>();
		if(index < 0 || size <= 0)
			return clientes;
		try(PreparedStatement st = con.prepareStatement(GET_ALL_N_ACT)){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				clientes.add(new Cliente(rs.getString("dpi"), rs.getString("nombre"), rs.getString("apellido"), 
						rs.getString("telefono"), rs.getString("email"), 
						new Direccion(rs.getString("departamento"), rs.getString("ciudad"), rs.getByte("zona"))));
		} catch (SQLException | DataFormatException | EmptyArgumentException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public boolean insert(Cliente nuevo) throws DBConnectionException, CannotInsertException {
		assert nuevo != null : STRINGS_DAO.getString("error.cliente.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setString(1, nuevo.getDpi());
			st.setString(2, nuevo.getNombre());
			st.setString(3, nuevo.getApellido());
			st.setString(4, nuevo.getTelefono());
			st.setString(5, nuevo.getEmail());
			
			Direccion dir = nuevo.getDireccion();
			
			st.setString(6, dir.getDepartamento());
			st.setString(7, dir.getCiudad());
			st.setByte(8, dir.getZona());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.cliente.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"),e);
		}
		return true;
	}

	@Override
	public boolean delete(Cliente cliente) throws DBConnectionException {
		assert cliente != null : STRINGS_DAO.getString("error.cliente.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setString(1, cliente.getDpi());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"),e);
		}
		return true;
	}

	@Override
	public boolean update(Cliente viejo, Cliente nuevo) throws DBConnectionException {
		assert viejo != null && nuevo != null: STRINGS_DAO.getString("error.cliente.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			
			st.setString(1, nuevo.getNombre());
			st.setString(2, nuevo.getApellido());
			st.setString(3, nuevo.getTelefono());
			st.setString(4, nuevo.getEmail());
			
			Direccion dir = nuevo.getDireccion();
			
			st.setString(5, dir.getDepartamento());
			st.setString(6, dir.getCiudad());
			st.setByte(7, dir.getZona());
			
			st.setString(8, viejo.getDpi());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"),e);
		}
		return true;
	}

	@Override
	public boolean darDeBaja(String dpiCliente) throws DBConnectionException {
		assert dpiCliente != null : STRINGS_MODELO.getString("error.dpi_cliente.null");
		try(PreparedStatement st = con.prepareStatement(DEACTIVATE)){
			
			st.setString(1, dpiCliente);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"),e);
		}
		return true;
	}

	@Override
	public boolean darDeAlta(String dpiCliente) throws DBConnectionException {
		assert dpiCliente != null : STRINGS_MODELO.getString("error.dpi_cliente.null");
		try(PreparedStatement st = con.prepareStatement(ACTIVATE)){
			
			st.setString(1, dpiCliente);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"),e);
		}
		return true;
	}

}
