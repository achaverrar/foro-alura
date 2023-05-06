package foro.dto.cursos;

import foro.modelo.Curso;

public record DatosResumidosCurso(Long id, String nombre, String categoria) {
	public DatosResumidosCurso(Curso curso) {
		this(curso.getId(), curso.getNombre(), curso.getCategoria());
	}
}