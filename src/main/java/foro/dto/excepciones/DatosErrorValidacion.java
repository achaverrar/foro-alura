package foro.dto.excepciones;

import org.springframework.validation.FieldError;

public record DatosErrorValidacion(String campo, Object valorRechazado, String mensajeDeError) {
	public DatosErrorValidacion(FieldError error) {
		this(error.getField(), error.getRejectedValue(), error.getDefaultMessage());
	}
}