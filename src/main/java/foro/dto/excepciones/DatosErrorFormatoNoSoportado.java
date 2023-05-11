package foro.dto.excepciones;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;

public record DatosErrorFormatoNoSoportado(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje) {

	public DatosErrorFormatoNoSoportado(HttpMediaTypeNotSupportedException excepcion) {
		this(
				415,
				HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				LocalDateTime.now(),
				generarMensajeDeError(excepcion)
				);
	}

	private static String generarMensajeDeError(
			HttpMediaTypeNotSupportedException excepcion) {

		List<MediaType> formatosAceptados = excepcion.getSupportedMediaTypes();

		StringBuilder builder = new StringBuilder();
		builder.append("El formato de documento: ");
		builder.append(excepcion.getContentType());
		builder.append(" no es aceptado. Los formatos aceptados son:");

		formatosAceptados.forEach(formato -> {
			builder.append(" " + formato);
		});

		return builder.toString();
	}
}