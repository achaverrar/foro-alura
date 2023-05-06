package foro.servicios;

import org.springframework.beans.factory.annotation.Autowired;
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
}