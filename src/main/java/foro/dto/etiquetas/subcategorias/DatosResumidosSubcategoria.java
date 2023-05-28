package foro.dto.etiquetas.subcategorias;

import foro.modelo.Etiqueta;

public record DatosResumidosSubcategoria(
		Long id,
		String nombre
		) {

	public DatosResumidosSubcategoria(Etiqueta subcategoria) {
		this(
				subcategoria.getId(),
				subcategoria.getNombre()
			);
	}
}