package inventario.dao.error;

/**
 * Exception que se lanza cuando un registro no se puede actualizar
 * @author juang
 *
 */
public class CannotUpdateException extends Exception {

	private static final long serialVersionUID = 1L;

	public CannotUpdateException() {
		super();
	}

	public CannotUpdateException(String m, Throwable t, boolean arg2, boolean arg3) {
		super(m, t, arg2, arg3);
	}

	public CannotUpdateException(String m, Throwable t) {
		super(m, t);
	}

	public CannotUpdateException(String m) {
		super(m);
	}

	public CannotUpdateException(Throwable t) {
		super(t);
	}

}
