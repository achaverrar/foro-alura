package foro.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionPublicacion(
		@NotNull(message = "El id es obligatorio") 
		Long id,

		String titulo, 
		String mensaje
		) {
}