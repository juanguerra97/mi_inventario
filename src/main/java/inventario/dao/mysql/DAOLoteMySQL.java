package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import inventario.dao.DAOLote;
import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Lote;

public class DAOLoteMySQL implements DAOLote {

	private static final String INSERT = "INSERT INTO Lote(id_producto,numero,fecha_vencimiento) "
			+ "VALUES(?,?,?)";
	private static final String UPDATE = "UPDATE Lote SET numero = ?, fecha_vencimiento = ? "
			+ "WHERE id_producto = ? AND numero = ?";
	private static final String DELETE = "DELETE FROM Lote WHERE id_producto = ? AND numero = ?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOLoteMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public boolean insert(Lote lote) throws DBConnectionException, CannotInsertException {
		assert lote != null : STRINGS_DAO.getString("error.lote.null");
		try(PreparedStatement st = con.prepareStatement(INSERT)){
			st.setLong(1, lote.getIdProducto());
			st.setLong(2, lote.getNumero());
			LocalDate ldat = lote.getFechaVencimiento();
			Date dat = (ldat == null ? null : Date.valueOf(ldat));
			st.setDate(3, dat);
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotInsertException(STRINGS_DAO.getString("error.lote.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean update(Lote viejo, Lote nuevo) throws DBConnectionException, CannotUpdateException {
		assert viejo != null : STRINGS_DAO.getString("error.lote.null");
		assert nuevo != null : STRINGS_DAO.getString("error.lote.null");
		try(PreparedStatement st = con.prepareStatement(UPDATE)){
			st.setLong(1, nuevo.getNumero());
			LocalDate ldat = nuevo.getFechaVencimiento();
			Date dat = (ldat == null ? null : Date.valueOf(ldat));
			st.setDate(2, dat);
			st.setLong(3, viejo.getIdProducto());
			st.setLong(4, viejo.getNumero());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				if(msg.matches("^Duplicate.*"))
					throw new CannotUpdateException(STRINGS_DAO.getString("error.lote.pk"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

	@Override
	public boolean delete(Lote lote) throws DBConnectionException, CannotDeleteException {
		assert lote != null : STRINGS_DAO.getString("error.lote.null");
		try(PreparedStatement st = con.prepareStatement(DELETE)){
			st.setLong(1, lote.getIdProducto());
			st.setLong(2, lote.getNumero());
			st.executeUpdate();
		} catch (SQLException e) {
			String msg = e.getMessage();
			if(msg != null) {
				throw new CannotDeleteException(STRINGS_DAO.getString("error.lote.existencias"), e);
			}
			throw new DBConnectionException(STRINGS_DAO.getString("error.conexion"), e);
		}
		return true;
	}

}
