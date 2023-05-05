package foro.dto.publicaciones;

import jakarta.validation.constraints.NotNull;

public record DatosEditarPublicacion(
		@NotNull(message = "El id es obligatorio") 
		Long id,

		String titulo, 
		String mensaje
		) {
}