package foro.seguridad;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import foro.modelo.Usuario;

public class UsuarioSeguridad implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final Usuario usuario;

	public UsuarioSeguridad(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.usuario.getRoles();
	}

	@Override
	public String getPassword() {
		return this.usuario.getContrasena();
	}

	@Override
	public String getUsername() {
		return this.usuario.getCorreo();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}
}