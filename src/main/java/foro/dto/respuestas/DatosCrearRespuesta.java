package foro.dto.respuestas;

import foro.modelo.Respuesta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearRespuesta(
		@NotBlank(message = "El mensaje es obligatorio")
		String mensaje,

		@NotNull(message = "El id de la publicación es obligatorio")
		Long publicacion_id
		) {

	public DatosCrearRespuesta(Respuesta respuesta) {
		this(respuesta.getMensaje(), respuesta.getPublicacion().getId());
	}

}