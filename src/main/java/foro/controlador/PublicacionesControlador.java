package foro.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro.dto.DatosNuevaPublicacion;
import foro.modelo.Publicacion;
import foro.repositorio.PublicacionRepositorio;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionesControlador {
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@PostMapping
	public void crearPublicacion(@RequestBody DatosNuevaPublicacion datosPublicacion) {
		publicacionRepositorio.save(new Publicacion(datosPublicacion));
	}
}