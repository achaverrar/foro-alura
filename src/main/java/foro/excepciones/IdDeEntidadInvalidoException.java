package foro.excepciones;

public class IdDeEntidadInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IdDeEntidadInvalidoException(String mensaje) {
		super(mensaje);
	}
}