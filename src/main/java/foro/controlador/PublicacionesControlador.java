package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.DatosNuevaPublicacion;
import foro.dto.DatosRespuestaPublicacion;
import foro.modelo.Publicacion;
import foro.repositorio.PublicacionRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionesControlador {
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@PostMapping
	public ResponseEntity<DatosRespuestaPublicacion> crearPublicacion(@RequestBody @Valid DatosNuevaPublicacion datosPublicacion, UriComponentsBuilder uriComponentsBuilder) {
		Publicacion publicacion = publicacionRepositorio.save(new Publicacion(datosPublicacion));

		DatosRespuestaPublicacion datosRespuestaPublicacion = new DatosRespuestaPublicacion(
				publicacion.getId(), 
				publicacion.getTitulo(), 
				publicacion.getMensaje(), 
				publicacion.getFechaCreacion(), 
				publicacion.getEstado());

		URI url = uriComponentsBuilder.path("/publicaciones{id}").buildAndExpand(publicacion.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaPublicacion);
	}
}