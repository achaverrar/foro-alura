package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.publicaciones.DatosResumidosPublicacion;
import foro.excepciones.RecursoNoEncontradoException;
import foro.excepciones.TransaccionSobreEntidadInexistenteException;
import foro.dto.publicaciones.DatosGuardarPublicacion;
import foro.dto.publicaciones.DatosCompletosPublicacion;
import foro.modelo.Curso;
import foro.modelo.Publicacion;
import foro.repositorio.CursoRepositorio;
import foro.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Autowired
	private CursoRepositorio cursoRepositorio;

	public Page<DatosResumidosPublicacion> listarPublicaciones(Pageable paginacion) {
		return publicacionRepositorio.findAll(paginacion).map(DatosResumidosPublicacion::new);
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

		if(!cursoRepositorio.existsById(cursoId)) {
			throw new TransaccionSobreEntidadInexistenteException("El curso de id " + cursoId + " no existe");
		}

		Curso curso = cursoRepositorio.getReferenceById(cursoId);	

		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());
		publicacion.setCurso(curso);

		publicacionRepositorio.save(publicacion);

		DatosResumidosPublicacion datosResumidosPublicacion = new DatosResumidosPublicacion(publicacion);

		return datosResumidosPublicacion;
	}

	public DatosResumidosPublicacion editarPublicacion(Long publicacionId, DatosGuardarPublicacion datosPublicacion) {
		Long cursoId = datosPublicacion.cursoId();

		if(!cursoRepositorio.existsById(cursoId)) {
			throw new TransaccionSobreEntidadInexistenteException("El curso de id " + cursoId + " no existe");
		}

		Curso curso = cursoRepositorio.getReferenceById(cursoId);

		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new TransaccionSobreEntidadInexistenteException("La publicación de id " + publicacionId + " no existe");
		}

		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		// TODO: verificar que la publicación pertenezca al curso indicado
//		if(!publicacion.getCurso().getId().equals(cursoId)) {
//			
//		}

		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());
		publicacion.setCurso(curso);

		return new DatosResumidosPublicacion(publicacion);
	}

	public void eliminarPublicacion(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		publicacionRepositorio.delete(publicacion);
	}

}