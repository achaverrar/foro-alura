package foro.seguridad;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenServicio {

	@Value("${api.jwt.key}")
	private String llave;

	@Value("${api.jwt.expiration.time}")
	private long tiempoExpiracion;

	public String generarToken(Authentication autenticacion) {
		String correoUsuarioActual = autenticacion.getName();

		SecretKey llaveSecreta = Keys.hmacShaKeyFor(llave.getBytes(StandardCharsets.UTF_8));

		Date fechaExpiracion = new Date(System.currentTimeMillis() + tiempoExpiracion);

		String jwt = Jwts.builder()
				.setIssuer("Foro Alura")
				.setSubject(correoUsuarioActual)
			    .setExpiration(fechaExpiracion)
			    .signWith(llaveSecreta)
				.compact();
		return jwt;
	}

}