package gg.wellplayed.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.auth.LoginRequest;
import gg.wellplayed.backend.dataTransfer.auth.LoginResponse;
import gg.wellplayed.backend.dataTransfer.auth.RegistrationRequest;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.model.UserRole;
import gg.wellplayed.backend.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtService jwtService;
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public String register(RegistrationRequest request) {
		User user = User.builder()
				.nick(request.getNick())
				.email(request.getEmail())
				.hashedPassword(passwordEncoder.encode(request.getPassword()))
				.role(UserRole.USER)
				.build();

		userRepository.save(user);

		return jwtService.getToken(user);
	}

	public String login(LoginRequest request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNick(), request.getPassword()));
		UserDetails user = userRepository.findByNick(request.getNick()).orElseThrow();
		String token = jwtService.getToken(user);
		
		return token;
	}
}
