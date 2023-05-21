package foro.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foro.dto.roles.DatosRol;
import foro.modelo.Rol;
import foro.repositorio.RolRepositorio;

@Service
public class RolServicio {

	@Autowired
	private RolRepositorio rolRepositorio;

	public void crearRol(DatosRol datosRol) {
		String nombre = datosRol.nombre().toUpperCase();
		Optional<Rol> rol = rolRepositorio.findByNombre(nombre);

		if(rol.isPresent()) {
			throw new RuntimeException("Este nombre de rol ya existe");
		}

		Rol nuevoRol = new Rol();
		nuevoRol.setNombre(nombre);

		rolRepositorio.save(nuevoRol);
	}
}