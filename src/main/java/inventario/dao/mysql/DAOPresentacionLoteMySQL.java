package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOPresentacionLote;
import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.PresentacionLote;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOPresentacionLoteMySQL implements DAOPresentacionLote {

	private static final String GET_ALL = "SELECT nom_presentacion,num_lote,cantidad,costo,precio "
			+ "FROM PresentacionLote WHERE id_producto = ? ORDER BY nom_presentacion,num_lote,cantidad,costo,precio LIMIT ?,?";
	private static final String INSERT = "INSERT INTO PresentacionLote(id_producto,nom_presentacion,num_lote,cantidad,costo,precio) "
			+ "VALUES(?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE PresentacionLote SET cantidad = ?, costo = ?, precio = ? "
			+ "WHERE id_producto = ? AND nom_presentacion = ? AND num_lote = ?";
	private static final String DELETE = "DELETE FROM PresentacionLote WHERE id_producto = ? AND "
			+ "nom_presentacion = ? AND num_lote = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOPresentacionLoteMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<PresentacionLote> getAll(long idProducto, int index, int size) {
		List<PresentacionLote> prlts = new LinkedList<>();
		if(idProducto <= 0 || index < 0 || size <= 0)
			return prlts;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setLong(1, idProducto);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next())
				prlts.add(new PresentacionLote(idProducto, rs.getString("nom_presentacion"), rs.getLong("num_lote"), rs.getInt("cantidad"), rs.getBigDecimal("costo"), rs.getBigDecimal("precio")));
		} catch (SQLException | ModelConstraintViolationException | EmptyArgumentException e) {
			e.printStackTrace();
		}
		return prlts;
	}

	@Override
	public boolean insert(PresentacionLote nuevo) throws DBConnectionException, CannotInsertException {
		assert nuevo != null : STRINGS_DAO.getString("error.presentacion_lote.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, nuevo.getIdProducto());
			st.setString(2, nuevo.getNombrePresentacion());
			st.setLong(3, nuevo.getNumeroLote());
			st.setInt(4, nuevo.getCantidad());
			st.setBigDecimal(5, nuevo.getCosto());
			st.setBigDecimal(6, nuevo.getPrecio());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.presentacion_lote.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(PresentacionLote viejo, PresentacionLote nuevo) throws DBConnectionException {
		assert viejo != null : STRINGS_DAO.getString("error.presentacion_lote.null");
		assert nuevo != null : STRINGS_DAO.getString("error.presentacion_lote.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			st.setInt(1, nuevo.getCantidad());
			st.setBigDecimal(2, nuevo.getCosto());
			st.setBigDecimal(3, nuevo.getPrecio());
			st.setLong(4, viejo.getIdProducto());
			st.setString(5, viejo.getNombrePresentacion());
			st.setLong(6, viejo.getNumeroLote());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(PresentacionLote registro) throws DBConnectionException, CannotDeleteException {
		assert registro != null : STRINGS_DAO.getString("error.presentacion_lote.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, registro.getIdProducto());
			st.setString(2, registro.getNombrePresentacion());
			st.setLong(3, registro.getNumeroLote());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				throw new CannotDeleteException(STRINGS_DAO.getString("error.presentacion_lote.existencias"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
