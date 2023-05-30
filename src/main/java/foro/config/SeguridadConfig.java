package foro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import foro.seguridad.AutenticacionTokenFiltro;
import foro.seguridad.SeguridadUtilidades;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SeguridadConfig {

	@Autowired
	private AutenticacionTokenFiltro autenticacionTokenFiltro;

	@Autowired
	private SeguridadUtilidades seguridadUtilidades;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// Solo para usar la consola de la base de datos H2
		// httpSecurity.headers().frameOptions().disable();

		return httpSecurity.csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests()
				.requestMatchers(HttpMethod.GET, "api/v*/**")
				.permitAll()
				.requestMatchers("api/v*/auth/**")
				.permitAll()
				.requestMatchers("api/v*/categorias/**", "api/v*/subcategorias/**", "api/v*/cursos/**", "api/v*/roles/**")
				.hasAuthority("ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.addFilterBefore(autenticacionTokenFiltro, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}