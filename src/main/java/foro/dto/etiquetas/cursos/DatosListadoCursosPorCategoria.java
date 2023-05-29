package foro.dto.etiquetas.cursos;

import foro.dto.etiquetas.subcategorias.DatosResumidosSubcategoria;
import foro.modelo.Etiqueta;

public record DatosListadoCursosPorCategoria(
		Long id,
		String nombre,
		DatosResumidosSubcategoria subcategoria
	) {

	public DatosListadoCursosPorCategoria(Etiqueta curso) {
		this(
				curso.getId(),
				curso.getNombre(),
				new DatosResumidosSubcategoria(curso.getEtiquetaPadre())
			);
	}
}