package foro.dto.etiquetas.cursos;

import foro.modelo.Etiqueta;

public record DatosResumidosCurso (Long id, String nombre) {

	public DatosResumidosCurso(Etiqueta curso) {
		this(curso.getId(), curso.getNombre());
	}
}