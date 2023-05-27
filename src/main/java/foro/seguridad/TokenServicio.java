package foro.seguridad;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import foro.modelo.RefreshToken;
import foro.modelo.Usuario;
import foro.repositorio.TokenRepositorio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenServicio {

	@Value("${api.jwt.key}")
	private String llave;

	@Value("${api.jwt.access.expiration.time}")
	private long tiempoExpiracionAccessToken;

	@Value("${api.jwt.refresh.expiration.time}")
	private long tiempoExpiracionRefreshToken;

	@Autowired
	private TokenRepositorio tokenRepositorio;

	public void guardarRefreshTokenEnBD(RefreshToken refreshToken, Usuario usuario) {
		tokenRepositorio.deleteByUsuario(usuario);

		tokenRepositorio.save(refreshToken);
	}

	public DatosCompletosToken generarAccessToken(Usuario usuario) {
		String correoUsuarioActual = usuario.getCorreo();

		SecretKey llaveSecreta = Keys.hmacShaKeyFor(llave.getBytes(StandardCharsets.UTF_8));

		Date fechaExpiracion = new Date(System.currentTimeMillis() + tiempoExpiracionAccessToken);

		String accessToken = Jwts.builder()
				.setIssuer("Foro Alura")
				.setSubject(correoUsuarioActual)
			    .setExpiration(fechaExpiracion)
			    .signWith(llaveSecreta)
				.compact();

		return new DatosCompletosToken(accessToken, fechaExpiracion);
	}

	public RefreshToken generarRefreshToken(Usuario usuario) {
		String token = generarTokenAleatorio(128);
		LocalDateTime fechaExpiracion = LocalDateTime.now().plus(tiempoExpiracionRefreshToken, ChronoUnit.MILLIS);
		
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(token);
		refreshToken.setUsuario(usuario);
		refreshToken.setFechaExpiracion(fechaExpiracion);

		return refreshToken;
	}
	public String recuperarCorreoDeToken(String jwToken) {
		SecretKey llaveSecreta = Keys.hmacShaKeyFor(llave.getBytes(StandardCharsets.UTF_8));

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(llaveSecreta)
				.build()
				.parseClaimsJws(jwToken)
				.getBody();

		String correo = claims.getSubject();

		return correo;
	}

	public DatosTokensIngreso generarTokensDeIngreso(Authentication autenticacion) {
		UsuarioSeguridad usuarioAutenticado = (UsuarioSeguridad) autenticacion.getPrincipal();
		Usuario usuario = usuarioAutenticado.getUsuario();

		RefreshToken refreshToken = generarRefreshToken(usuario);

		DatosCompletosToken datosAccessToken = generarAccessToken(usuario);
		DatosCompletosToken datosRefreshToken = new DatosCompletosToken(refreshToken.getToken(), refreshToken.getFechaExpiracion());

		guardarRefreshTokenEnBD(refreshToken, usuario);

		return new DatosTokensIngreso(datosAccessToken, datosRefreshToken);
	}

	// https://stackoverflow.com/a/31214709/109354
	public String generarTokenAleatorio(int longitud) {
		Random random = new SecureRandom();
		return String.format("%" + longitud + "s", new BigInteger(longitud * 5, random)
				.toString(32))
				.replace('\u0020', '0');
	}

	public void eliminarRefreshTokenDeBD() {
		Usuario usuario = SeguridadUtilidades.getUsuarioAutenticado();
		tokenRepositorio.deleteByUsuario(usuario);
	}

	public DatosCompletosToken generarNuevoAccessToken(HttpServletRequest peticion) {
		String autorizacionHeader = peticion.getHeader("Authorization");

		if (autorizacionHeader != null) {
			String tokenEnHeader = autorizacionHeader.replace("Bearer ", "");
			Optional<RefreshToken> refreshTokenEnDB = tokenRepositorio.findByToken(tokenEnHeader);

			if(refreshTokenEnDB.isPresent()) {
				Date fechaExpiracion = refreshTokenEnDB.get().getFechaExpiracion();

				if(new Date().before(fechaExpiracion)) {
					Usuario usuario = refreshTokenEnDB.get().getUsuario();
					DatosCompletosToken datosAccessToken = generarAccessToken(usuario);
					return datosAccessToken;
				} else {
					tokenRepositorio.delete(refreshTokenEnDB.get());
				}
			}

		}
		return null;
	}
}