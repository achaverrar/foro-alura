package foro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import foro.modelo.Curso;

public interface CursoRepositorio extends JpaRepository<Curso, Long> {
}