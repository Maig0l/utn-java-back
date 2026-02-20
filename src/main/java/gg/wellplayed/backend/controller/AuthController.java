package gg.wellplayed.backend.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.auth.LoginRequest;
import gg.wellplayed.backend.dataTransfer.auth.LoginResponse;
import gg.wellplayed.backend.dataTransfer.auth.RegistrationRequest;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.AuthService;
import gg.wellplayed.backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	
	@GetMapping
	public ApiResponse index() {
		return new ApiResponse(
			"/auth Index");
	}
	
	// Nos mudamos a UserController.java
	/*
	@PostMapping("/register")
	public ApiResponse register(@RequestBody RegistrationRequest request) {
		String token = authService.register(request);
		return new ApiResponse(
				"Welcome aboard!",
				new LoginResponse(token),
				HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ApiResponse login(@RequestBody LoginRequest request) {
		String token = authService.login(request);

		return new ApiResponse(
				"Welcome back!",
				new LoginResponse(token));
//		return CoolerApiResponse.builder()
//				.message("Welcome back!")
//				.data(new LoginResponse(token))
//				.build();
	}
	*/
}
