package foro.excepciones;

public class PertenenciaInvalidaExcepcion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PertenenciaInvalidaExcepcion(String mensaje) {
		super(mensaje);
	}
}