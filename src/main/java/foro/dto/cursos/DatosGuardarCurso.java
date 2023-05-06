package foro.dto.cursos;

import foro.modelo.Curso;
import jakarta.validation.constraints.NotBlank;

public record DatosGuardarCurso(
		@NotBlank(message = "El nombre es obligatorio")
		String nombre,

		@NotBlank(message = "La categoría es obligatoria")
		String categoria
		) {

	public DatosGuardarCurso(Curso curso) {
		this(curso.getNombre(), curso.getCategoria());
	}
}