package foro.dto.excepciones;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import foro.excepciones.IdDeEntidadInvalidoException;

public record DatosErrorIdDeEntidadInvalido(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje
		) {

	public DatosErrorIdDeEntidadInvalido(IdDeEntidadInvalidoException excepcion) {
		this(400, HttpStatus.BAD_REQUEST, LocalDateTime.now(), excepcion.getMessage());		
	}
}