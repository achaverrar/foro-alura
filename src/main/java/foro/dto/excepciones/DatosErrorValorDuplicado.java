package foro.dto.excepciones;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import foro.excepciones.InfoExcepcionesPersonalizadas;

public record DatosErrorValorDuplicado(
		int codigoDeError,
		HttpStatus status,
		LocalDateTime fechaYHora,
		String mensaje) {

	public DatosErrorValorDuplicado(DataIntegrityViolationException excepcion) {
		this(400, HttpStatus.BAD_REQUEST, LocalDateTime.now(), generarMensajeDeError(excepcion));
	}

	private static String generarMensajeDeError(DataIntegrityViolationException excepcion) {
		ConstraintViolationException violaciónDeRestricción = (ConstraintViolationException) excepcion.getCause();

		if (violaciónDeRestricción == null) {
			return null;			
		}

		String nombreDeRestricción = violaciónDeRestricción.getConstraintName();

		for (Map.Entry<String, String> excepcionPropia : InfoExcepcionesPersonalizadas.MENSAJES.entrySet()) {
			String identificador = excepcionPropia.getKey();

			if (nombreDeRestricción.contains(identificador)) {
				return excepcionPropia.getValue();
			}
		}

		return null;
	}
}