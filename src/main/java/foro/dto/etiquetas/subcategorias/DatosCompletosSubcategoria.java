package foro.dto.etiquetas.subcategorias;

import java.util.List;

import foro.dto.etiquetas.categorias.DatosResumidosCategoria;
import foro.dto.etiquetas.cursos.DatosResumidosCurso;
import foro.modelo.Etiqueta;

public record DatosCompletosSubcategoria (
		Long id,
		String nombre,
		DatosResumidosCategoria categoria,
		List<DatosResumidosCurso> cursos
		) {

	public DatosCompletosSubcategoria(Etiqueta subcategoria) {
		this(
				subcategoria.getId(),
				subcategoria.getNombre(),
				new DatosResumidosCategoria(subcategoria.getEtiquetaPadre()),
				subcategoria.getEtiquetasHijas().stream().map(DatosResumidosCurso::new).toList()
			);
	}
}