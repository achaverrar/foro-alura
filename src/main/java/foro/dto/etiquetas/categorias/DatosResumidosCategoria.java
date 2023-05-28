package foro.dto.etiquetas.categorias;

import foro.modelo.Etiqueta;

public record DatosResumidosCategoria(Long id, String nombre) {

	public DatosResumidosCategoria(Etiqueta categoria) {
		this(categoria.getId(), categoria.getNombre());
	}
}