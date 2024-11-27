package gg.wellplayed.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import gg.wellplayed.backend.jwt.JwtAuthFilter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private final JwtAuthFilter jwtAuthFilter;
	@Autowired
	private final AuthenticationProvider authProvider;
	

	@PostConstruct
	private String getContextPath() {
		return servletContext.getContextPath();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf ->
					csrf.disable()
				)
			
				.authorizeHttpRequests(authRequest ->
					authRequest
						// Tomamos la ruta base especificada en el archivo application.properties (context-path) en lugar de hardcodearla.
						// Sólo las rutas /api/v2/auth están permitidas al público.
						.requestMatchers("/auth/**").permitAll()
						.anyRequest().authenticated()
						//.anyRequest().permitAll()
				)
				.sessionManagement(sessionMgr ->
					sessionMgr
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authenticationProvider(authProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//				.headers(headers ->
//						headers.disable())
				.build();
		
	}
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//TODO: Cambiar "*". Los orígenes admitidos deberían ser http://localhost:4200 (sólo durante desarrollo) o http://wellplayed.gg:80
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
		// Permitir headers para las pre-flight requests
		// Esto hace que no falle el post
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));


		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}

