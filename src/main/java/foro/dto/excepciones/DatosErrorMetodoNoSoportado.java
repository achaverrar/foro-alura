package foro.dto.excepciones;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import jakarta.servlet.http.HttpServletRequest;

public record DatosErrorMetodoNoSoportado(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje,
		String path) {

	public DatosErrorMetodoNoSoportado(
			HttpRequestMethodNotSupportedException excepcion, 
			HttpServletRequest request) {
		this(
				405,
				HttpStatus.METHOD_NOT_ALLOWED,
				LocalDateTime.now(),
				generarMensajeDeError(excepcion),
				request.getRequestURI()
			);
	}

	private static String generarMensajeDeError(HttpRequestMethodNotSupportedException excepcion) {
		Set<HttpMethod> conjuntoMetodosPermitidos = excepcion.getSupportedHttpMethods();

		StringBuilder builder = new StringBuilder();
		builder.append("El método usado: ");
	    builder.append(excepcion.getMethod());
	    builder.append(" no está permitido.");

	    if(conjuntoMetodosPermitidos != null && conjuntoMetodosPermitidos.size() > 0) {
			builder.append(" Los métodos permitidos son:");
			conjuntoMetodosPermitidos.forEach(metodo -> builder.append(" " + metodo));
		}

	    return builder.toString();
	}
}