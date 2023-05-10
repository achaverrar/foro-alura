package foro.dto.excepciones;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import foro.excepciones.PertenenciaInvalidaExcepcion;

public record DatosErrorPertenenciaInvalida(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje) {

	public DatosErrorPertenenciaInvalida(PertenenciaInvalidaExcepcion excepcion) {
		this(400, HttpStatus.BAD_REQUEST, LocalDateTime.now(), excepcion.getMessage());
	}
}