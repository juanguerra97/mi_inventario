package inventario.dao.mysql;

import static inventario.dao.mysql.Resources.STRINGS_DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import inventario.dao.DAOLoteProducto;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Lote;
import inventario.modelo.error.ModelConstraintViolationException;

public class DAOLoteProductoMySQL implements DAOLoteProducto {
	
	private static final String GET_ALL = "SELECT numero,fecha_vencimiento FROM Lote "
			+ "WHERE id_producto = ? ORDER BY numero ASC LIMIT ?,?";
	
	private Connection con;// conexion con la base de datos
	
	/**
	 * Constructor
	 * @param conexionMySQL objeto con la conexion a la base de datos MySQL, no debe ser null
	 * @throws DBConnectionException si la conexion con la base de datos esta cerrada
	 */
	public DAOLoteProductoMySQL(Connection conexionMySQL) throws DBConnectionException {
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
	public List<Lote> getAll(long idProducto, int index, int size) {
		List<Lote> lotes = new LinkedList<>();
		if(idProducto <= 0 || index < 0 || size <= 0)
			return lotes;
		try(PreparedStatement st = con.prepareStatement(GET_ALL)){
			st.setLong(1, idProducto);
			st.setInt(2, index);
			st.setInt(3, size);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Date dat = rs.getDate("fecha_vencimiento");
				lotes.add(new Lote(idProducto,rs.getLong("numero"),(dat == null ? null : dat.toLocalDate())));
			}
		} catch (SQLException | ModelConstraintViolationException e) {
			e.printStackTrace();
		}
		return lotes;
	}

}
