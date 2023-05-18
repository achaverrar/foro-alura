package foro.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import foro.modelo.Usuario;
import jakarta.transaction.Transactional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Boolean existsByCorreo(String correo);

	Optional<Usuario> findByCorreo(String correo);

	@Transactional
	@Modifying
	void deleteByCorreo(String correo);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Usuario u SET u.contrasena = ?1 WHERE u.id = ?2")
	void changePassword(String nuevaContrasena, Long usuarioId);
}