package gg.wellplayed.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf ->
					csrf.disable()
				)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(authRequest ->
					authRequest
						.requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/users/by-nick/**", "/users/*/reviews").permitAll()
						.requestMatchers(HttpMethod.GET, "/users/**").authenticated()
						.requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
						.requestMatchers(HttpMethod.PATCH, "/users/**").authenticated()

						// Catálogo (games/tags/studios/platforms/shops/franchises): sólo ADMIN puede mutar
						.requestMatchers(HttpMethod.POST, "/games", "/tags", "/studios", "/platforms", "/shops", "/franchises").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/games/*/shops", "/games/*/studios", "/games/*/platforms", "/games/*/pictures").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/games/*", "/tags/*", "/studios/*", "/platforms/*", "/shops/*", "/franchises/*").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/games/*", "/games/*/uploads/*", "/platforms/*", "/platforms/*/upload", "/tags/*", "/studios/*", "/shops/*", "/franchises/*").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/games/*", "/tags/*", "/studios/*", "/platforms/*", "/shops/*", "/franchises/*").hasAuthority("ADMIN")

						// Playlists y reviews: hay que estar autenticado; el ownership se valida en el controller
						.requestMatchers(HttpMethod.POST, "/playlists").authenticated()
						.requestMatchers(HttpMethod.PUT, "/playlists/*").authenticated()
						.requestMatchers(HttpMethod.PATCH, "/playlists/*").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/playlists/*").authenticated()
						.requestMatchers(HttpMethod.POST, "/reviews").authenticated()
						.requestMatchers(HttpMethod.PUT, "/reviews/*").authenticated()
						.requestMatchers(HttpMethod.PATCH, "/reviews/*").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/reviews/*").authenticated()

						.anyRequest().permitAll()
				)
				.sessionManagement(sessionMgr ->
					sessionMgr
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authenticationProvider(authProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//TODO: Cambiar "*". Los orígenes admitidos deberían ser http://localhost:4200 (sólo durante desarrollo) o http://wellplayed.gg:80
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		// Permitir headers para las pre-flight requests
		// Esto hace que no falle el post
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
