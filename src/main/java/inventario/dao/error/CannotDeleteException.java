package inventario.dao.error;

/**
 * Clase asociada con los errores relacionados con registros que no se pueden eliminar
 * @author juang
 *
 */
public class CannotDeleteException extends Exception {

	private static final long serialVersionUID = 1L;

	public CannotDeleteException() {
		super();
	}

	public CannotDeleteException(String m, Throwable t, boolean arg2, boolean arg3) {
		super(m, t, arg2, arg3);
	}

	public CannotDeleteException(String m, Throwable t) {
		super(m, t);
	}

	public CannotDeleteException(String m) {
		super(m);
	}

	public CannotDeleteException(Throwable t) {
		super(t);
	}

}
