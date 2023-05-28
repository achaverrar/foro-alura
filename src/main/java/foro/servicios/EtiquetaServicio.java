package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.etiquetas.categorias.DatosCompletosCategoria;
import foro.dto.etiquetas.categorias.DatosGuardarCategoria;
import foro.dto.etiquetas.categorias.DatosResumidosCategoria;
import foro.dto.etiquetas.subcategorias.DatosGuardarSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosListadoSubcategoria;
import foro.excepciones.TransaccionSobreEntidadInexistenteException;
import foro.modelo.Etiqueta;
import foro.modelo.Nivel;
import foro.repositorio.EtiquetaRepositorio;

@Service
public class EtiquetaServicio {

	@Autowired
	EtiquetaRepositorio etiquetaRepositorio;

	public Etiqueta crearEtiqueta(String nombre, Nivel nivel, Etiqueta etiquetaPadre) {
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setNombre(nombre);
		etiqueta.setNivel(nivel);
		etiqueta.setEtiquetaPadre(etiquetaPadre);

		etiquetaRepositorio.save(etiqueta);
		return etiqueta;
	}

	public Page<Etiqueta> listarEtiquetasPorNivel(Nivel nivel, Pageable paginacion) {
		return etiquetaRepositorio.findAllByNivel(nivel, paginacion);
	}

	/* CATEGORIAS */
	public DatosResumidosCategoria crearCategoria(DatosGuardarCategoria datosCategoria) {
		Etiqueta categoria = crearEtiqueta(datosCategoria.nombre(), Nivel.CATEGORIA, null);
		return new DatosResumidosCategoria(categoria);
	}

	public Page<DatosResumidosCategoria> listarCategorias(Pageable paginacion) {
		return listarEtiquetasPorNivel(Nivel.CATEGORIA, paginacion).map(DatosResumidosCategoria::new);
	}

	public DatosCompletosCategoria encontrarCategoriaPorId(Long categoriaId) {
		etiquetaRepositorio.findByIdAndNivel(categoriaId, Nivel.CATEGORIA);
		Etiqueta categoria = etiquetaRepositorio.findByIdAndNivel(categoriaId, Nivel.CATEGORIA);

		if(categoria == null) {
			throw new RecursoNoEncontradoException("La categoría de id " + categoriaId + " no existe");
		}

		return new DatosCompletosCategoria(categoria);
	}

	public DatosResumidosCategoria editarCategoria(Long categoriaId, DatosGuardarCategoria datosCategoria) {
		if(!etiquetaRepositorio.existsByIdAndNivel(categoriaId, Nivel.CATEGORIA)) {
			throw new TransaccionSobreEntidadInexistenteException("La categoria de id " + categoriaId + " no existe");
		}

		Etiqueta categoria = etiquetaRepositorio.getReferenceById(categoriaId);

		categoria.setNombre(datosCategoria.nombre());

		return new DatosResumidosCategoria(categoria);
	}

	/* SUBCATEGORIAS */
	public DatosListadoSubcategoria crearSubcategoria(DatosGuardarSubcategoria datosSubcategoria) {
		Long categoriaId = Long.valueOf(datosSubcategoria.categoria_id());
		Etiqueta categoria = etiquetaRepositorio.findByIdAndNivel(categoriaId, Nivel.CATEGORIA);

		if(categoria == null) {
			throw new TransaccionSobreEntidadInexistenteException("La categoría de id " + categoriaId + " no existe");
		}

		Etiqueta subcategoria = crearEtiqueta(
				datosSubcategoria.nombre(),
				Nivel.SUBCATEGORIA,
				categoria);

		etiquetaRepositorio.save(subcategoria);

		return new DatosListadoSubcategoria(subcategoria);
	}

}