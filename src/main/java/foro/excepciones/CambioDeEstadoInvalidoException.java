package foro.excepciones;

public class CambioDeEstadoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CambioDeEstadoInvalidoException(String mensaje) {
		super(mensaje);
	}
}