package foro.dto.etiquetas.cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosGuardarCurso(
		@NotBlank(message = "El nombre es obligatorio")
		String nombre,

		@NotBlank(message = "La categoría es obligatoria")
		@Pattern(regexp = "^[1-9]\\d*$", message = "El id de la categoria debe ser un número entero positivo")
		String subcategoria_id
		) {
}