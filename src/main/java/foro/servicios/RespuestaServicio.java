package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import foro.dto.respuestas.DatosGuardarRespuesta;
import foro.excepciones.CambioDeEstadoInvalidoException;
import foro.excepciones.PertenenciaInvalidaExcepcion;
import foro.excepciones.RecursoNoEncontradoException;
import foro.excepciones.TransaccionSobreEntidadInexistenteException;
import foro.dto.respuestas.DatosCompletosRespuesta;
import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;
import foro.modelo.Respuesta;
import foro.modelo.Usuario;
import foro.repositorio.PublicacionRepositorio;
import foro.repositorio.RespuestaRepositorio;
import foro.seguridad.SeguridadUtilidades;

@Service
public class RespuestaServicio {

	@Autowired
	private RespuestaRepositorio respuestaRepositorio;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	public DatosCompletosRespuesta crearRespuesta(Long publicacionId, DatosGuardarRespuesta datosRespuesta) {
		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new TransaccionSobreEntidadInexistenteException("La publicación de id " + publicacionId + " no existe");
		}

		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		if(publicacion.getEstado().equals(EstadoPublicacion.SOLUCIONADO)) {
			throw new CambioDeEstadoInvalidoException("No se puede crear esta respuesta, "
					+ "pues la publicación de id " + publicacionId + " ya fue solucionada");
		}

		if(publicacion.getEstado().equals(EstadoPublicacion.NO_RESPONDIDO)) {
			publicacion.setEstado(EstadoPublicacion.NO_SOLUCIONADO);
		}

		Usuario usuario = SeguridadUtilidades.getUsuarioAutenticado();

		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(datosRespuesta.mensaje());
		respuesta.setPublicacion(publicacion);
		respuesta.setAutor(usuario);

		respuestaRepositorio.save(respuesta);

		return new DatosCompletosRespuesta(respuesta);
	}

	public Page<DatosCompletosRespuesta> listarRespuestasPorPublicacionId(Long publicacionId, Pageable paginacion) {
		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new RecursoNoEncontradoException("No fue posible encontrar la publicación de id: " + publicacionId);
		}

		return respuestaRepositorio.findAllByPublicacionId(publicacionId, paginacion).map(DatosCompletosRespuesta::new);
	}

	@PreAuthorize("@seguridadUtilidades.esAutor(#respuestaId, 'Respuesta')")
	public DatosCompletosRespuesta editarRespuesta(
			Long publicacionId, 
			Long respuestaId,
			DatosGuardarRespuesta datosRespuesta) {

		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new TransaccionSobreEntidadInexistenteException("La publicación de id " + publicacionId + " no existe");
		}

		if(!respuestaRepositorio.existsById(respuestaId)) {
			throw new TransaccionSobreEntidadInexistenteException("La respuesta de id " + respuestaId + " no existe");
		}

		Respuesta respuesta = respuestaRepositorio.getReferenceById(respuestaId);

		if(!respuesta.getPublicacion().getId().equals(publicacionId)) {
			throw new PertenenciaInvalidaExcepcion("La respuesta de id " + respuestaId 
					+ " no pertenece a la publicación de id " + publicacionId);
		}

		respuesta.setMensaje(datosRespuesta.mensaje());

		return new DatosCompletosRespuesta(respuesta);
	}

	@PreAuthorize("@seguridadUtilidades.esAutor(#publicacionId, 'Publicacion')")
	public void escogerRespuestaComoSolucion(Long publicacionId, Long respuestaId) {
		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new TransaccionSobreEntidadInexistenteException("La publicación de id " + publicacionId + " no existe");
		}

		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		if(publicacion.getEstado().equals(EstadoPublicacion.SOLUCIONADO)) {
			throw new CambioDeEstadoInvalidoException("No se puede crear esta respuesta, "
					+ "pues la publicación de id " + publicacionId + " ya fue solucionada");
		}

		if(!respuestaRepositorio.existsById(respuestaId)) {
			throw new TransaccionSobreEntidadInexistenteException("La respuesta de id " + respuestaId + " no existe");
		}

		Respuesta respuesta = respuestaRepositorio.getReferenceById(respuestaId);

		if(!respuesta.getPublicacion().getId().equals(publicacionId)) {
			throw new PertenenciaInvalidaExcepcion("La respuesta de id " + respuestaId 
					+ " no pertenece a la publicación de id " + publicacionId);
		}

		publicacion.setEstado(EstadoPublicacion.SOLUCIONADO);
		respuesta.setSolucion(true);
	}

}