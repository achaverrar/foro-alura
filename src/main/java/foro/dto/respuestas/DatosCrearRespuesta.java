package foro.dto.respuestas;

import foro.modelo.Respuesta;
import jakarta.validation.constraints.NotBlank;

public record DatosCrearRespuesta(
		@NotBlank(message = "El mensaje es obligatorio")
		String mensaje
		) {

	public DatosCrearRespuesta(Respuesta respuesta) {
		this(respuesta.getMensaje());
	}

}