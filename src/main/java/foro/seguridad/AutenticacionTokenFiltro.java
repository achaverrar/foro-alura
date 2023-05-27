package foro.seguridad;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import foro.repositorio.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AutenticacionTokenFiltro extends OncePerRequestFilter {

	@Autowired
	private TokenServicio tokenServicio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if(shouldNotFilter(request)) {
			filterChain.doFilter(request, response);
		}

		String jwToken = recuperarToken(request);

		if (jwToken != null) {
			String correo = tokenServicio.recuperarCorreoDeToken(jwToken);

			UsuarioSeguridad usuario = new UsuarioSeguridad(usuarioRepositorio.findByCorreo(correo).get());

			var autenticacion = new UsernamePasswordAuthenticationToken(
					usuario,
					null,
					usuario.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(autenticacion);

		}
		filterChain.doFilter(request, response);
	}

	public String recuperarToken(HttpServletRequest peticion) {
		String autorizacionHeader = peticion.getHeader("Authorization");
		if (autorizacionHeader != null) {
			return autorizacionHeader.replace("Bearer ", "");
		}
		return null;
	}

	@Override
    protected boolean shouldNotFilter(HttpServletRequest peticion) throws ServletException {
        String path = peticion.getRequestURI();
        return path.equals("/usuarios/token/refresh");
    }
}