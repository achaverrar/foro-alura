package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.respuestas.DatosGuardarRespuesta;
import foro.dto.respuestas.DatosCompletosRespuesta;
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

		// TODO: validar que la publicación exista
		Publicacion publicacion = publicacionRepositorio.getReferenceById(publicacionId);

		// TODO: validar que la respuesta exista
		Respuesta respuesta = respuestaRepositorio.getReferenceById(respuestaId);

		// TODO: validar que la respuesta corresponda a la publicación señalada
		respuesta.setMensaje(datosRespuesta.mensaje());

		return new DatosCompletosRespuesta(respuesta);
	}
}