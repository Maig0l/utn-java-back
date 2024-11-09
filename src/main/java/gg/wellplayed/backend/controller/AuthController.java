package gg.wellplayed.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.auth.UserLoginRequest;
import gg.wellplayed.backend.dataTransfer.user.UserRegistrationRequest;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UserService userService;
	
	@GetMapping
	public ApiResponse index() {
		return new ApiResponse(
			"/auth Index");
	}
	
	@PostMapping("/register")
	public ApiResponse register(@RequestBody UserRegistrationRequest request) {
		User newUser = request.parseToUser();
		userService.save(newUser);
		return new ApiResponse(
			"Welcome to WellPlayed.gg",
			newUser,
			HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ApiResponse login(@RequestBody UserLoginRequest request) {
		// buscar usuario por nick
		User user = userService.findByNick(request.nick());

		// del usuario saco la hashed password y la comparo con lo que viene en la request (bcrypt se encarga de el resto)
		boolean isCorrect = BCrypt.checkpw(request.password(), user.getHashedPassword());
		if (!isCorrect)
			return new ApiResponse(
				"Wrong nick/password, buddy.",
				HttpStatus.FORBIDDEN);
		
		// TODO: Generar y devolver JWT en lugar del objeto del usuario
		return new ApiResponse(
			"Welcome back",
			user);
	}
}
