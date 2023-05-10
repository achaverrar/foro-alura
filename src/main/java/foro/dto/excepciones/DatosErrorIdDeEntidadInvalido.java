package foro.dto.excepciones;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import foro.excepciones.TransaccionSobreEntidadInexistenteException;

public record DatosErrorIdDeEntidadInvalido(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje
		) {

	public DatosErrorIdDeEntidadInvalido(TransaccionSobreEntidadInexistenteException excepcion) {
		this(400, HttpStatus.BAD_REQUEST, LocalDateTime.now(), excepcion.getMessage());		
	}
}