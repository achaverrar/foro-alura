package foro.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.etiquetas.categorias.DatosCompletosCategoria;
import foro.dto.etiquetas.categorias.DatosGuardarCategoria;
import foro.dto.etiquetas.categorias.DatosResumidosCategoria;
import foro.dto.etiquetas.cursos.DatosCompletosCurso;
import foro.dto.etiquetas.cursos.DatosGuardarCurso;
import foro.dto.etiquetas.cursos.DatosResumidosCurso;
import foro.dto.etiquetas.subcategorias.DatosCompletosSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosGuardarSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosListadoSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosResumidosSubcategoria;
import foro.excepciones.RecursoNoEncontradoException;
import foro.excepciones.TransaccionSobreEntidadInexistenteException;
import foro.modelo.Etiqueta;
import foro.modelo.Nivel;
import foro.repositorio.EtiquetaRepositorio;
import jakarta.validation.Valid;

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
	
	public Page<Etiqueta> listarPorNivelYEtiquetaPadreId(Nivel nivel, Long etiquetaPadreId, Pageable paginacion) {
		return etiquetaRepositorio.findAllByNivelAndEtiquetaPadreId(nivel, etiquetaPadreId, paginacion);
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

	public void eliminarCategoria(Long categoriaId) {
		if(!etiquetaRepositorio.existsByIdAndNivel(categoriaId, Nivel.CATEGORIA)) {
			throw new TransaccionSobreEntidadInexistenteException("La categoría de id " + categoriaId + " no existe");
		}

		etiquetaRepositorio.deleteById(categoriaId);
	}

	/* SUBCATEGORIAS */
	public Page<Record> listarSubcategorias(Pageable paginacion) {
		return listarEtiquetasPorNivel(Nivel.SUBCATEGORIA, paginacion).map(DatosListadoSubcategoria::new);
	}

	public Page<Record> listarSubcategoriasPorCategoriaId(Long categoriaId, Pageable paginacion) {
		if(!etiquetaRepositorio.existsByIdAndNivel(categoriaId, Nivel.CATEGORIA)) {
			throw new TransaccionSobreEntidadInexistenteException("La categoría de id " + categoriaId + " no existe");
		}

		return listarPorNivelYEtiquetaPadreId(Nivel.SUBCATEGORIA, categoriaId, paginacion).map(DatosResumidosSubcategoria::new);
	}

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

	public DatosCompletosSubcategoria encontrarSubcategoriaPorId(Long subcategoriaId) {
		Etiqueta subcategoria = etiquetaRepositorio.findByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA);

		if(subcategoria == null) {
			throw new RecursoNoEncontradoException("La subcategoría de id " + subcategoriaId + " no existe");
		}

		return new DatosCompletosSubcategoria(subcategoria);
	}

	public DatosListadoSubcategoria editarSubcategoria(
			Long subcategoriaId,
			DatosGuardarSubcategoria datosSubcategoria
		) {

		Etiqueta subcategoria = etiquetaRepositorio.findByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA);

		if(subcategoria == null) {
			throw new TransaccionSobreEntidadInexistenteException("La subcategoría de id " + subcategoriaId + " no existe");
		}

		Long categoriaId = Long.valueOf(datosSubcategoria.categoria_id());
		Etiqueta categoria = etiquetaRepositorio.findByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA);

		if(categoria == null) {
			throw new RecursoNoEncontradoException("La categoría de id " + categoriaId + " no existe");
		}

		subcategoria.setNombre(datosSubcategoria.nombre());
		subcategoria.setEtiquetaPadre(categoria);

		return new DatosListadoSubcategoria(subcategoria);
	}

	public void eliminarSubcategoria(Long subcategoriaId) {
		if(!etiquetaRepositorio.existsByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA)) {
			throw new TransaccionSobreEntidadInexistenteException("La subcategoría de id " + subcategoriaId + " no existe");
		}

		etiquetaRepositorio.deleteById(subcategoriaId);
	}

	/* CURSOS */
	public DatosCompletosCurso crearCurso(DatosGuardarCurso datosCurso) {
		Long subcategoriaId = Long.valueOf(datosCurso.subcategoria_id());
		Etiqueta subcategoria = etiquetaRepositorio.findByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA);

		if(subcategoria == null) {
			throw new TransaccionSobreEntidadInexistenteException("La subcategoría de id " + subcategoriaId + " no existe");
		}

		Etiqueta curso = crearEtiqueta(
				datosCurso.nombre(),
				Nivel.CURSO,
				subcategoria
				);

		etiquetaRepositorio.save(curso);

		return new DatosCompletosCurso(curso);
	}

	public DatosCompletosCurso encontrarCursoPorId(Long cursoId) {
		Etiqueta curso = etiquetaRepositorio.findByIdAndNivel(cursoId, Nivel.CURSO);

		if(curso == null) {
			throw new RecursoNoEncontradoException("No fue posible encontrar el curso de id: " + cursoId);
		}

		return new DatosCompletosCurso(curso);
	}

	public DatosCompletosCurso editarCurso(Long cursoId, @Valid DatosGuardarCurso datosCurso) {
		Etiqueta curso = etiquetaRepositorio.findByIdAndNivel(cursoId, Nivel.CURSO);

		if(curso == null) {
			throw new RecursoNoEncontradoException("No fue posible encontrar el curso de id: " + cursoId);
		}

		Long subcategoriaId = Long.valueOf(datosCurso.subcategoria_id());
		Etiqueta subcategoria = etiquetaRepositorio.findByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA);

		if(subcategoria == null) {
			throw new TransaccionSobreEntidadInexistenteException("La subcategoría de id " + subcategoriaId + " no existe");			
		}

		curso.setNombre(datosCurso.nombre());
		curso.setEtiquetaPadre(subcategoria);

		return new DatosCompletosCurso(curso);
	}

	public Page<Record> listarCursos(Pageable paginacion) {
		return listarEtiquetasPorNivel(Nivel.CURSO, paginacion).map(DatosCompletosCurso::new);
	}

	public Page<Record> listarCursosPorCategoriaId(Long categoriaId, Pageable paginacion) {
		Etiqueta categoria = etiquetaRepositorio.findByIdAndNivel(categoriaId, Nivel.CATEGORIA);

		if(categoria == null) {
			throw new RecursoNoEncontradoException("La categoría de id " + categoriaId + " no existe");
		}

		List<Etiqueta> cursos = new ArrayList<>();

		List<Etiqueta> subcategorias = categoria.getEtiquetasHijas();

		if(subcategorias.size() != 0) {
			subcategorias.stream().forEach(subcategoria -> cursos.addAll(subcategoria.getEtiquetasHijas()));
		}

		List<Record> datosCursos = cursos.stream().map(curso -> (Record) new DatosResumidosCurso(curso)).toList();

		Page<Record> pagina = new PageImpl<Record>(datosCursos, paginacion, datosCursos.size());

		return pagina;
	}

	public Page<Record> listarCursosPorSubcategoriaId(Long subcategoriaId, Pageable paginacion) {
		if(!etiquetaRepositorio.existsByIdAndNivel(subcategoriaId, Nivel.SUBCATEGORIA)) {
			throw new TransaccionSobreEntidadInexistenteException("La subcategoría de id " + subcategoriaId + " no existe");
		}

		return listarPorNivelYEtiquetaPadreId(Nivel.CURSO, subcategoriaId, paginacion).map(DatosResumidosCurso::new);
	}

	public void eliminarCurso(Long cursoId) {
		if(!etiquetaRepositorio.existsByIdAndNivel(cursoId, Nivel.CURSO)) {
			throw new TransaccionSobreEntidadInexistenteException("El curso de id " + cursoId + " no existe");
		}

		etiquetaRepositorio.deleteById(cursoId);
	}
}