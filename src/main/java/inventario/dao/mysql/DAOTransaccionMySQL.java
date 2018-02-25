package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;
import static inventario.modelo.Resources.STRINGS_MODELO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import inventario.dao.DAOTransaccion;
import inventario.dao.TipoTransaccion;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Transaccion;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOTransaccionMySQL implements DAOTransaccion {
	
	private static final String GET_C = "SELECT fecha,total FROM Compra "
			+ "WHERE numero = ?";
	private static final String GET_V = "SELECT fecha,total FROM Venta "
			+ "WHERE numero = ?";
	private static final String GET_ALL_C = "SELECT numero,fecha,total FROM Compra "
			+ "ORDER BY numero,fecha LIMIT ?,?";
	private static final String GET_ALL_V = "SELECT numero,fecha,total FROM Venta "
			+ "ORDER BY numero,fecha LIMIT ?,?";
	private static final String GET_ALLFECH_C = "SELECT numero,total FROM Compra "
			+ "ORDER BY numero,fecha WHERE fecha = ? LIMIT ?,?";
	private static final String GET_ALLFECH_V = "SELECT numero,total FROM Venta "
			+ "ORDER BY numero,fecha WHERE fecha = ? LIMIT ?,?";
	private static final String INSERT_C = "INSERT INTO Compra(numero,fecha,total) "
			+ "VALUES(?,?,?)";
	private static final String INSERT_V = "INSERT INTO Venta(numero,fecha,total) "
			+ "VALUES(?,?,?)";
	private static final String INSERT_N_C = "INSERT INTO Compra(fecha,total) "
			+ "VALUES(?,?)";
	private static final String INSERT_N_V = "INSERT INTO Venta(fecha,total) "
			+ "VALUES(?,?)";
	private static final String DELETE_C = "DELETE FROM Compra WHERE numero = ?";
	private static final String DELETE_V = "DELETE FROM Venta WHERE numero = ?";
	private static final String UPDATE_C = "UPDATE Compra SET fecha = ?,total = ? WHERE numero = ?";
	private static final String UPDATE_V = "UPDATE Venta SET fecha = ?,total = ? WHERE numero = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOTransaccionMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public Optional<Transaccion> get(TipoTransaccion tipo, long numero) {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		if(numero <= 0)
			return Optional.ofNullable(null);
		Transaccion t = null;
		try(PreparedStatement st = con.prepareStatement(tipo == TipoTransaccion.COMPRA ? GET_C : GET_V)){
			st.setLong(1, numero);
			ResultSet rs = st.executeQuery();
			if(rs.next())
				t = new Transaccion(numero, rs.getDate("fecha").toLocalDate(), rs.getBigDecimal("total"));
		} catch (SQLException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(t);
	}

	@Override
	public List<Transaccion> getAll(TipoTransaccion tipo, int index, int size) {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		List<Transaccion> trans = new LinkedList<>();
		if(index < 0 || size <= 0)
			return trans;
		try(PreparedStatement st = con.prepareStatement(tipo == TipoTransaccion.COMPRA ? GET_ALL_C : GET_ALL_V)){
			st.setInt(1, index);
			st.setInt(2, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				trans.add(new Transaccion(rs.getLong("numero"), rs.getDate("fecha").toLocalDate(), rs.getBigDecimal("total")));
		} catch (SQLException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return trans;
	}

	@Override
	public List<Transaccion> getAll(TipoTransaccion tipo, LocalDate fecha, int index, int size) {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		assert fecha != null : STRINGS_MODELO.getString("error.date.null");
		List<Transaccion> trans = new LinkedList<>();
		if(index < 0 || size <= 0)
			return trans;
		try(PreparedStatement st = con.prepareStatement(tipo == TipoTransaccion.COMPRA ? GET_ALLFECH_C : GET_ALLFECH_V)){
			st.setDate(1, Date.valueOf(fecha));
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				trans.add(new Transaccion(rs.getLong("numero"), fecha, rs.getBigDecimal("total")));
		} catch (SQLException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return trans;
	}

	@Override
	public boolean insert(TipoTransaccion tipo, Transaccion transaccion)
			throws DBConnectionException, CannotInsertException {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		assert transaccion != null : STRINGS_DAO.getString("error.transaccion.null");
		try(PreparedStatement st = con.prepareStatement( tipo == TipoTransaccion.COMPRA ? INSERT_C : INSERT_V)){
			st.setLong(1, transaccion.getNumero());
			st.setDate(2, Date.valueOf(transaccion.getFecha()));
			st.setBigDecimal(3, transaccion.getValor());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.transaccion.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public long insertAndGet(TipoTransaccion tipo, Transaccion transaccion) throws DBConnectionException {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		assert transaccion != null : STRINGS_DAO.getString("error.transaccion.null");
		long numero = -1L;
		try(PreparedStatement st = con.prepareStatement( (tipo == TipoTransaccion.COMPRA ? INSERT_N_C : INSERT_N_V), 
				Statement.RETURN_GENERATED_KEYS )){
			st.setDate(1, Date.valueOf(transaccion.getFecha()));
			st.setBigDecimal(2, transaccion.getValor());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next())
				numero = rs.getLong(1);
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return numero;
	}

	@Override
	public boolean delete(TipoTransaccion tipo, long numero) throws DBConnectionException {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		if(numero <= 0)
			return false;
		try(PreparedStatement st = con.prepareStatement(tipo == TipoTransaccion.COMPRA ? DELETE_C : DELETE_V)){
			st.setLong(1, numero);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(TipoTransaccion tipo, Transaccion transaccion) throws DBConnectionException {
		assert tipo != null : STRINGS_DAO.getString("error.transaccion.tipo.null");
		assert transaccion != null : STRINGS_DAO.getString("error.transaccion.null");
		try(PreparedStatement st = con.prepareStatement(tipo == TipoTransaccion.COMPRA ? UPDATE_C : UPDATE_C)){
			st.setDate(1, Date.valueOf(transaccion.getFecha()));
			st.setBigDecimal(2, transaccion.getValor());
			st.setLong(3, transaccion.getNumero());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
