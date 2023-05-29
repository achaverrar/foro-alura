package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import foro.dto.publicaciones.DatosCompletosPublicacion;
import foro.dto.publicaciones.DatosGuardarPublicacion;
import foro.dto.publicaciones.DatosListadoPublicacionPorCurso;
import foro.dto.publicaciones.DatosResumidosPublicacion;
import foro.excepciones.PertenenciaInvalidaExcepcion;
import foro.excepciones.RecursoNoEncontradoException;
import foro.excepciones.TransaccionSobreEntidadInexistenteException;
import foro.modelo.Etiqueta;
import foro.modelo.Nivel;
import foro.modelo.Publicacion;
import foro.modelo.Usuario;
import foro.repositorio.EtiquetaRepositorio;
import foro.repositorio.PublicacionRepositorio;
import foro.seguridad.SeguridadUtilidades;

@Service
public class PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Autowired
	private EtiquetaRepositorio etiquetaRepositorio;

	public Page<Record> listarPublicaciones(Pageable paginacion) {
		return publicacionRepositorio.findAll(paginacion).map(DatosResumidosPublicacion::new);
	}

	public Page<Record> listarPublicacionesPorCursoId(Long cursoId, Pageable paginacion) {
		return publicacionRepositorio.findAllByCursoId(cursoId, paginacion).map(DatosListadoPublicacionPorCurso::new);
	}

	public DatosCompletosPublicacion encontrarPublicacionPorId(Long publicacionId) {
		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new RecursoNoEncontradoException("No fue posible encontrar la publicación de id: " + publicacionId);
		}

		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);
		return new DatosCompletosPublicacion(publicacion);
	}

	public DatosResumidosPublicacion crearPublicacion(DatosGuardarPublicacion datosPublicacion) {
		Long cursoId = datosPublicacion.cursoId();

		Etiqueta curso = etiquetaRepositorio.findByIdAndNivel(cursoId, Nivel.CURSO);

		if(curso == null) {
			throw new TransaccionSobreEntidadInexistenteException("El curso de id " + cursoId + " no existe");
		}

		Usuario usuario = SeguridadUtilidades.getUsuarioAutenticado();

		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());
		publicacion.setCurso(curso);
		publicacion.setAutor(usuario);

		publicacionRepositorio.save(publicacion);

		DatosResumidosPublicacion datosResumidosPublicacion = new DatosResumidosPublicacion(publicacion);

		return datosResumidosPublicacion;
	}

	@PreAuthorize("@seguridadUtilidades.esAutor(#publicacionId, 'Publicacion')")
	public DatosResumidosPublicacion editarPublicacion(Long publicacionId, DatosGuardarPublicacion datosPublicacion) {
		Long cursoId = datosPublicacion.cursoId();

		Etiqueta curso = etiquetaRepositorio.findByIdAndNivel(cursoId, Nivel.CURSO);

		if(curso == null) {
			throw new TransaccionSobreEntidadInexistenteException("El curso de id " + cursoId + " no existe");
		}

		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new TransaccionSobreEntidadInexistenteException("La publicación de id " + publicacionId + " no existe");
		}

		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		if(!publicacion.getCurso().getId().equals(cursoId)) {
			throw new PertenenciaInvalidaExcepcion("La publicación de id " + publicacionId 
					+ " no pertenece al curso de id " + cursoId);
		}

		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());
		publicacion.setCurso(curso);

		return new DatosResumidosPublicacion(publicacion);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	public void eliminarPublicacion(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		publicacionRepositorio.delete(publicacion);
	}

}