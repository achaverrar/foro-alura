package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.respuestas.DatosCrearRespuesta;
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

	public DatosCompletosRespuesta crearRespuesta(DatosCrearRespuesta datosRespuesta) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(datosRespuesta.publicacion_id());
		Respuesta respuesta = respuestaRepositorio.save(new Respuesta(datosRespuesta.mensaje(), publicacion));
		return new DatosCompletosRespuesta(respuesta);
	}

	public Page<DatosCompletosRespuesta> listarRespuestasPorPublicacionId(Long id, Pageable paginacion) {
		return respuestaRepositorio.findAllByPublicacionId(id, paginacion).map(DatosCompletosRespuesta::new);
	}
}