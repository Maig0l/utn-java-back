package gg.wellplayed.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;

@RestController("/auth")
public class AuthController {
	@PostMapping("/register")
	public ApiResponse register() {
		return new ApiResponse("...");
	}

	@PostMapping("/login")
	public ApiResponse login() {
		return new ApiResponse("...");
	}
}