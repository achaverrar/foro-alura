package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.respuestas.DatosGuardarRespuesta;
import foro.excepciones.IdDeEntidadInvalidoException;
import foro.dto.respuestas.DatosCompletosRespuesta;
import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;
import foro.modelo.Respuesta;
import foro.repositorio.PublicacionRepositorio;
import foro.repositorio.RespuestaRepositorio;

@Service
public class RespuestaServicio {

	@Autowired
	private RespuestaRepositorio respuestaRepositorio;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	public DatosCompletosRespuesta crearRespuesta(Long publicacionId, DatosGuardarRespuesta datosRespuesta) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		if(publicacion.getEstado().equals(EstadoPublicacion.NO_RESPONDIDO)) {
			publicacion.setEstado(EstadoPublicacion.NO_SOLUCIONADO);
		}

		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(datosRespuesta.mensaje());
		respuesta.setPublicacion(publicacion);

		respuestaRepositorio.save(respuesta);

		return new DatosCompletosRespuesta(respuesta);
	}

	public Page<DatosCompletosRespuesta> listarRespuestasPorPublicacionId(Long id, Pageable paginacion) {
		return respuestaRepositorio.findAllByPublicacionId(id, paginacion).map(DatosCompletosRespuesta::new);
	}

	public DatosCompletosRespuesta editarRespuesta(
			Long publicacionId, 
			Long respuestaId,
			DatosGuardarRespuesta datosRespuesta) {

		if(!publicacionRepositorio.existsById(publicacionId)) {
			throw new IdDeEntidadInvalidoException("La publicación de id " + publicacionId + " no existe");
		}

		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		if(!respuestaRepositorio.existsById(respuestaId)) {
			throw new IdDeEntidadInvalidoException("La respuesta de id " + respuestaId + " no existe");
		}

		Respuesta respuesta = respuestaRepositorio.getReferenceById(respuestaId);

		// TODO: validar que la respuesta corresponda a la publicación señalada
		respuesta.setMensaje(datosRespuesta.mensaje());

		return new DatosCompletosRespuesta(respuesta);
	}

	public void escogerRespuestaComoSolucion(Long publicacionId, Long respuestaId) {
		// TODO: validar que la publicación exista
		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		// TODO: validar que la respuesta exista
		Respuesta respuesta = respuestaRepositorio.getReferenceById(respuestaId);

		// TODO: validar que la respuesta corresponda a la publicación señalada
		publicacion.setEstado(EstadoPublicacion.SOLUCIONADO);
		respuesta.setSolucion(true);
	}

}