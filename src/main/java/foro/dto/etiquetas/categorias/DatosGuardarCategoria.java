package foro.dto.etiquetas.categorias;

import jakarta.validation.constraints.NotBlank;

public record DatosGuardarCategoria(

		@NotBlank(message = "El nombre es obligatorio")
		String nombre
		) {
}