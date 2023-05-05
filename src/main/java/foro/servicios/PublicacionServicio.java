package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.publicaciones.DatosActualizacionPublicacion;
import foro.dto.publicaciones.DatosResumidosPublicacion;
import foro.dto.publicaciones.DatosCrearPublicacion;
import foro.dto.publicaciones.DatosRespuestaPublicacion;
import foro.modelo.Publicacion;
import foro.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	public Page<DatosResumidosPublicacion> listarPublicaciones(Pageable paginacion) {
		return publicacionRepositorio.findAll(paginacion).map(DatosResumidosPublicacion::new);
	}

	public DatosResumidosPublicacion encontrarPublicacionPorId(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		return new DatosResumidosPublicacion(publicacion);
	}

	public DatosResumidosPublicacion crearPublicacion(DatosCrearPublicacion datosPublicacion) {
		Publicacion publicacion = publicacionRepositorio.save(new Publicacion(datosPublicacion));

		DatosResumidosPublicacion datosResumidosPublicacion = new DatosResumidosPublicacion(
				publicacion.getId(), 
				publicacion.getTitulo(), 
				publicacion.getMensaje(), 
				publicacion.getFechaCreacion(), 
				publicacion.getEstado(),
				publicacion.calcularTotalRespuestas());

		return datosResumidosPublicacion;
	}

	public DatosRespuestaPublicacion editarPublicacion(DatosActualizacionPublicacion datosPublicacion) {
        Publicacion publicacion = publicacionRepositorio.getReferenceById(datosPublicacion.id());
        publicacion.editarPublicacion(datosPublicacion);
        return new DatosRespuestaPublicacion(publicacion);
    }

	public void eliminarPublicacion(Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		publicacionRepositorio.delete(publicacion);
	}
}