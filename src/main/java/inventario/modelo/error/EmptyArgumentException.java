package inventario.modelo.error;

/**
 * Clase para representar excepciones relacionadas con argumentos
 * vacios que no son validos
 * @author juang
 *
 */
public class EmptyArgumentException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyArgumentException() {
		super();
	}

	public EmptyArgumentException(String m, Throwable t, 
			boolean arg2, boolean arg3) {
		super(m, t, arg2, arg3);
	}

	public EmptyArgumentException(String m, Throwable t) {
		super(m, t);
	}

	public EmptyArgumentException(String m) {
		super(m);
	}

	public EmptyArgumentException(Throwable t) {
		super(t);
	}

}
