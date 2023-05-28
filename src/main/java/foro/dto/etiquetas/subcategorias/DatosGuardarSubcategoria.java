package foro.dto.etiquetas.subcategorias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosGuardarSubcategoria(
		@NotNull(message = "El id de la categoria es obligatorio")
		@Pattern(
				regexp = "^[1-9]\\d*$",
				message = "El id de la categoria debe ser un número entero positivo"
				)
		String categoria_id,

		@NotBlank(message = "El nombre es obligatorio")
		String nombre) {
}