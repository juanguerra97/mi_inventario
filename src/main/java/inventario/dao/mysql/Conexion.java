package inventario.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static inventario.dao.mysql.Resources.STRINGS_DAO;

/**
 * Clase para manejar la conexion con la base de datos MySQL
 * @author juang
 *
 */
public class Conexion {
	
	/**
	 * String con la direccion de la base de datos
	 */
	private static final String URL = "jdbc:mysql://localhost/mi_inventario";
	
	/**
	 * Usuario para acceder a la base de datos
	 */
	private static final String USER = "root";
	
	/**
	 * Contraseña del usuario para acceder a la base de datos
	 */
	private static final String PASS = "";
	
	private static Connection conexion = null;
	
	/**
	 * Metodo para obtener una conexion a la base de datos
	 * @return objeto Connection con conexion a base de datos
	 * @throws SQLException si no se puede conectar con la base de datos
	 */
	public static Connection get() throws SQLException {
		if(conexion == null)
			try {
				conexion = DriverManager.getConnection(URL, USER, PASS);
			}catch(Exception e) {
				throw new SQLException(STRINGS_DAO.getString("error.conexion"),e);
			}
		return conexion;
	}
	
	/**
	 * Metodo para cerrar la conexion con la base de datos
	 */
	public static void close() {
		if(conexion == null)
			return;
		try {conexion.close();} catch (SQLException e) {}
		conexion = null;
	}
	
	private Conexion() {}

}
