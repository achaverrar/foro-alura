package foro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import foro.modelo.Etiqueta;

public interface EtiquetaRepositorio extends JpaRepository<Etiqueta, Long>  {	
}