package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foro.dto.cursos.DatosGuardarCurso;
import foro.dto.cursos.DatosResumidosCurso;
import foro.modelo.Curso;
import foro.repositorio.CursoRepositorio;

@Service
public class CursoServicio {

	@Autowired
	private CursoRepositorio cursoRepositorio;

	public DatosResumidosCurso crearCurso(DatosGuardarCurso datosCurso) {
		Curso curso = new Curso();
		curso.setNombre(datosCurso.nombre());
		curso.setCategoria(datosCurso.categoria());

		cursoRepositorio.save(curso);

		return new DatosResumidosCurso(curso);
	}

	public DatosResumidosCurso editarCurso(Long cursoId, DatosGuardarCurso datosCurso) {
		Curso curso = cursoRepositorio.getReferenceById(cursoId);

		curso.setNombre(datosCurso.nombre());
		curso.setCategoria(datosCurso.categoria());

		return new DatosResumidosCurso(curso);
	}

	public Page<DatosResumidosCurso> listarCursos(Pageable paginacion) {
		return cursoRepositorio.findAll(paginacion).map(DatosResumidosCurso::new);
	}

	public DatosResumidosCurso encontrarCursoPorId(Long cursoId) {
		Curso curso = cursoRepositorio.getReferenceById(cursoId);
		return new DatosResumidosCurso(curso);
	}

	public void eliminarPublicacion(Long cursoId) {
		Curso curso = cursoRepositorio.getReferenceById(cursoId);
		cursoRepositorio.delete(curso);		
	}
}