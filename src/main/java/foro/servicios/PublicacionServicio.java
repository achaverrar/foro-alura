package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.publicaciones.DatosResumidosPublicacion;
import foro.dto.publicaciones.DatosGuardarPublicacion;
import foro.dto.publicaciones.DatosCompletosPublicacion;
import foro.modelo.Publicacion;
import foro.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	public Page<DatosResumidosPublicacion> listarPublicaciones(Pageable paginacion) {
		return publicacionRepositorio.findAll(paginacion).map(DatosResumidosPublicacion::new);
	}

	public DatosCompletosPublicacion encontrarPublicacionPorId(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		return new DatosCompletosPublicacion(publicacion);
	}

	public DatosResumidosPublicacion crearPublicacion(DatosGuardarPublicacion datosPublicacion) {
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(datosPublicacion.titulo());
		publicacion.setMensaje(datosPublicacion.mensaje());

		publicacionRepositorio.save(publicacion);

		DatosResumidosPublicacion datosResumidosPublicacion = new DatosResumidosPublicacion(publicacion);

		return datosResumidosPublicacion;
	}

	public DatosResumidosPublicacion editarPublicacion(Long id, DatosGuardarPublicacion datosPublicacion) {
        Publicacion publicacion = publicacionRepositorio.getReferenceById(id);

        publicacion.setTitulo(datosPublicacion.titulo());
        publicacion.setMensaje(datosPublicacion.mensaje());

        return new DatosResumidosPublicacion(publicacion);
    }

	public void eliminarPublicacion(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		publicacionRepositorio.delete(publicacion);
	}
}