package gg.wellplayed.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.user.UserRegistrationRequest;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;


	/* CRUD Operations
	 */

	@PostMapping
	public ApiResponse register(@RequestBody UserRegistrationRequest request) {
		User newUser = request.parseToUser();
		newUser = userService.save(newUser);
		
		return new ApiResponse(
			"Usuario creado",
			newUser,
			HttpStatus.CREATED);
	}
}
