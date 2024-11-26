package gg.wellplayed.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf ->
					csrf
						.disable()
				)
			
				.authorizeHttpRequests(authRequest ->
					authRequest
						// Sólo las rutas /api/v2/auth están permitidas al público
					
						.requestMatchers("/api/**").permitAll()
						//.anyRequest().authenticated()
						.anyRequest().permitAll()
				)
				.formLogin(withDefaults())
				.headers(headers -> headers.disable())
				.build();
		
	}
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//TODO: Cambiar "*". Los orígenes admitidos deberían ser http://localhost:4200 (sólo durante desarrollo) o http://wellplayed.gg:80
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
