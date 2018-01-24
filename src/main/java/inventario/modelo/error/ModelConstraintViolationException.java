package inventario.modelo.error;

/**
 * Clase para crear excepciones relacionadas  con la violacion de 
 * alguna condicion en el modelo de datos
 * @author juang
 *
 */
public class ModelConstraintViolationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ModelConstraintViolationException() {
		super();
	}

	public ModelConstraintViolationException(String m, Throwable t, 
			boolean arg2, boolean arg3) {
		super(m, t, arg2, arg3);
	}

	public ModelConstraintViolationException(String m, Throwable t) {
		super(m, t);
	}

	public ModelConstraintViolationException(String m) {
		super(m);
	}

	public ModelConstraintViolationException(Throwable t) {
		super(t);
	}

}
