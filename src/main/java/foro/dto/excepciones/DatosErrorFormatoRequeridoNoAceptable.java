package foro.dto.excepciones;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import jakarta.servlet.http.HttpServletRequest;

public record DatosErrorFormatoRequeridoNoAceptable(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje) {

	public DatosErrorFormatoRequeridoNoAceptable(
			HttpMediaTypeNotAcceptableException excepcion, 
			HttpServletRequest peticion) {
		this(
				406,
				HttpStatus.NOT_ACCEPTABLE,
				LocalDateTime.now(),
				generarMensajeDeError(excepcion, peticion)
				);
	}

	private static String generarMensajeDeError(
			HttpMediaTypeNotAcceptableException excepcion,
			HttpServletRequest peticion) {

		List<MediaType> formatosAceptados = excepcion.getSupportedMediaTypes();

		StringBuilder builder = new StringBuilder();
		builder.append("El formato de documento pedido: ");
		builder.append(peticion.getHeader("accept"));
		builder.append(" no es aceptado. Los formatos aceptados son:");

		formatosAceptados.forEach(formato -> {
			builder.append(" " + formato);
		});

		return builder.toString();
	}
}