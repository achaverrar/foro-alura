package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.etiquetas.categorias.DatosGuardarCategoria;
import foro.dto.etiquetas.categorias.DatosResumidosCategoria;
import foro.modelo.Etiqueta;
import foro.modelo.Nivel;
import foro.repositorio.EtiquetaRepositorio;

@Service
public class EtiquetaServicio {

	@Autowired
	EtiquetaRepositorio etiquetaRepositorio;

	public Etiqueta crearEtiqueta(String nombre, Nivel nivel, Etiqueta etiquetaPadre) {
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setNombre(nombre);
		etiqueta.setNivel(nivel);
		etiqueta.setEtiquetaPadre(etiquetaPadre);

		etiquetaRepositorio.save(etiqueta);
		return etiqueta;
	}

	public Page<Etiqueta> listarEtiquetasPorNivel(Nivel nivel, Pageable paginacion) {
		return etiquetaRepositorio.findAllByNivel(nivel, paginacion);
	}

	/* CATEGORIAS */
	public DatosResumidosCategoria crearCategoria(DatosGuardarCategoria datosCategoria) {
		Etiqueta categoria = crearEtiqueta(datosCategoria.nombre(), Nivel.CATEGORIA, null);
		return new DatosResumidosCategoria(categoria);
	}

	public Page<DatosResumidosCategoria> listarCategorias(Pageable paginacion) {
		return listarEtiquetasPorNivel(Nivel.CATEGORIA, paginacion).map(DatosResumidosCategoria::new);
	}
}