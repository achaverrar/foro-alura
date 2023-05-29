package foro.dto.etiquetas.subcategorias;

import foro.dto.etiquetas.categorias.DatosResumidosCategoria;
import foro.modelo.Etiqueta;

public record DatosCompletosSubcategoria (
		Long id,
		String nombre,
		DatosResumidosCategoria categoria
		) {

	public DatosCompletosSubcategoria(Etiqueta subcategoria) {
		this(
				subcategoria.getId(),
				subcategoria.getNombre(),
				new DatosResumidosCategoria(subcategoria.getEtiquetaPadre())
			);
	}
}