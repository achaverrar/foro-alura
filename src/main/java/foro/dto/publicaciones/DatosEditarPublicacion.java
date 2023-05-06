package foro.dto.publicaciones;

import jakarta.validation.constraints.NotBlank;

public record DatosEditarPublicacion(
		@NotBlank(message = "El título es obligatorio")
		String titulo,

		@NotBlank(message = "El mensaje es obligatorio")
		String mensaje) {
}