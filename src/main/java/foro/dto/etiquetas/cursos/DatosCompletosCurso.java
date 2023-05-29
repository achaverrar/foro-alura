package foro.dto.etiquetas.cursos;

import foro.dto.etiquetas.categorias.DatosResumidosCategoria;
import foro.dto.etiquetas.subcategorias.DatosResumidosSubcategoria;
import foro.modelo.Etiqueta;

public record DatosCompletosCurso (
		Long id,
		String nombre,
		DatosResumidosCategoria categoria,
		DatosResumidosSubcategoria subcategoria
	){

	public DatosCompletosCurso(Etiqueta curso) {
		this(
				curso.getId(),
				curso.getNombre(),
				new DatosResumidosCategoria(curso.getEtiquetaPadre().getEtiquetaPadre()),
				new DatosResumidosSubcategoria(curso.getEtiquetaPadre())
			);
	}
}