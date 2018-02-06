package inventario.dao.error;

/**
 * Error cuando no se pueda insertar un registro
 * @author juang
 *
 */
public class CannotInsertException extends Exception {

	private static final long serialVersionUID = 1L;

	public CannotInsertException() {
		super();
	}

	public CannotInsertException(String m, Throwable t, boolean arg2, boolean arg3) {
		super(m, t, arg2, arg3);
	}

	public CannotInsertException(String m, Throwable t) {
		super(m, t);
	}

	public CannotInsertException(String m) {
		super(m);
	}

	public CannotInsertException(Throwable t) {
		super(t);
	}

}
