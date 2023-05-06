package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.publicaciones.DatosResumidosPublicacion;
import foro.dto.publicaciones.DatosGuardarPublicacion;
import foro.dto.publicaciones.DatosCompletosPublicacion;
import foro.modelo.Curso;
import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;
import foro.modelo.Respuesta;
import foro.repositorio.CursoRepositorio;
import foro.repositorio.PublicacionRepositorio;
import foro.repositorio.RespuestaRepositorio;

@Service
public class PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Autowired
	private RespuestaRepositorio respuestaRepositorio;

	@Autowired
	private CursoRepositorio cursoRepositorio;

	public Page<DatosResumidosPublicacion> listarPublicaciones(Pageable paginacion) {
		return publicacionRepositorio.findAll(paginacion).map(DatosResumidosPublicacion::new);
	}

	public DatosCompletosPublicacion encontrarPublicacionPorId(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		return new DatosCompletosPublicacion(publicacion);
	}

	public DatosResumidosPublicacion crearPublicacion(DatosGuardarPublicacion datosPublicacion) {
		Curso curso = cursoRepositorio.getReferenceById(datosPublicacion.cursoId());	

		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());
		publicacion.setCurso(curso);

		publicacionRepositorio.save(publicacion);

		DatosResumidosPublicacion datosResumidosPublicacion = new DatosResumidosPublicacion(publicacion);

		return datosResumidosPublicacion;
	}

	public DatosResumidosPublicacion editarPublicacion(Long id, DatosGuardarPublicacion datosPublicacion) {
		Curso curso = cursoRepositorio.getReferenceById(datosPublicacion.cursoId());

		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);

		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());
		publicacion.setCurso(curso);

		return new DatosResumidosPublicacion(publicacion);
	}

	public void eliminarPublicacion(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		publicacionRepositorio.delete(publicacion);
	}

	public void solucionarPublicacion(Long publicacionId, Long respuestaId) {
		// TODO: validar que la publicación exista
		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		// TODO: validar que la respuesta exista
		Respuesta respuesta = respuestaRepositorio.getReferenceById(respuestaId);

		// TODO: validar que la respuesta corresponda a la publicación señalada
		publicacion.setEstado(EstadoPublicacion.SOLUCIONADO);
		respuesta.setSolucion(true);
	}
}