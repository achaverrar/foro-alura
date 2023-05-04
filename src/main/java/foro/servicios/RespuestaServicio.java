package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foro.dto.respuestas.DatosNuevaRespuesta;
import foro.dto.respuestas.DatosRespuesta;
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

	public DatosRespuesta crearRespuesta(DatosNuevaRespuesta datosRespuesta) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(datosRespuesta.publicacion_id());
		Respuesta respuesta = respuestaRepositorio.save(new Respuesta(datosRespuesta.mensaje(), publicacion));
		return new DatosRespuesta(respuesta);
	}

}