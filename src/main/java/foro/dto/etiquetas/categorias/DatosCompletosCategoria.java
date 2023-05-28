package foro.dto.etiquetas.categorias;

import java.util.List;

import foro.dto.etiquetas.subcategorias.DatosResumidosSubcategoria;
import foro.modelo.Etiqueta;

public record DatosCompletosCategoria (
		Long id,
		String nombre,
		List<DatosResumidosSubcategoria> subcategorias
	) {

	public DatosCompletosCategoria(Etiqueta categoria) {
		this(
				categoria.getId(),
				categoria.getNombre(),
				categoria.getEtiquetasHijas().stream().map(DatosResumidosSubcategoria::new).toList()
			);
	}
}