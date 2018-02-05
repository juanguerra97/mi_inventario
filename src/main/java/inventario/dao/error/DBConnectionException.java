package inventario.dao.error;

/**
 * Clase relacionada con los errores de conexion con base de datos
 * @author juang
 *
 */
public class DBConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public DBConnectionException() {
		super();
	}

	public DBConnectionException(String m, Throwable t, boolean arg2, boolean arg3) {
		super(m, t, arg2, arg3);
	}

	public DBConnectionException(String m, Throwable t) {
		super(m, t);
	}

	public DBConnectionException(String m) {
		super(m);
	}

	public DBConnectionException(Throwable y) {
		super(y);
	}
	
}
