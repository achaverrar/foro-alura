package foro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import foro.modelo.Respuesta;

public interface RespuestaRepositorio extends JpaRepository<Respuesta, Long> {
}