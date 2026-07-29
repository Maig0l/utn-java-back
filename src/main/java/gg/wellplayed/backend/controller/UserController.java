package gg.wellplayed.backend.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.auth.LoginRequest;
import gg.wellplayed.backend.dataTransfer.auth.LoginResponse;
import gg.wellplayed.backend.dataTransfer.auth.RegistrationRequest;
import gg.wellplayed.backend.dataTransfer.user.UserPatchDTO;
import gg.wellplayed.backend.dataTransfer.user.UserPutDTO;
import gg.wellplayed.backend.dataTransfer.user.UserResponseDTO;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.AuthService;
import gg.wellplayed.backend.service.FileStorageService;
import gg.wellplayed.backend.service.ReviewService;
import gg.wellplayed.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	FileStorageService fileStorageService;


	/* CRUD Operations
	 */

	@PostMapping
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
	}

	@GetMapping("/by-nick/{nick}")
	public ApiResponse getUserByNick(@PathVariable("nick") String nick) {
		try {
			UserResponseDTO user = UserResponseDTO.fromEntity(userService.findByNick(nick).orElseThrow());
			return new ApiResponse("User fetched", user);
		}
		catch (NoSuchElementException e) {
			return new ApiResponse("User not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{nick}/reviews")
	public ApiResponse getUserReviews(@PathVariable("nick") String nick) {
		try {
			userService.findByNick(nick).orElseThrow();
			return new ApiResponse(reviewService.findByAuthorNick(nick));
		}
		catch (NoSuchElementException e) {
			return new ApiResponse("User not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getUser(@PathVariable("id") Long id, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			UserResponseDTO user = userService.getOwnedProfile(id, authentication.getName());
			return new ApiResponse("User fetched", user);
		}
		catch (NoSuchElementException e) {
			return new ApiResponse("User not found", HttpStatus.NOT_FOUND);
		}
		catch (AccessDeniedException e) {
			return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping("/{id}")
	public ApiResponse updateUser(@PathVariable("id") Long id, @RequestBody UserPutDTO request, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			UserResponseDTO user = userService.putOwnedProfile(id, request, authentication.getName());
			return new ApiResponse("User updated", user);
		}
		catch (NoSuchElementException e) {
			return new ApiResponse("User not found", HttpStatus.NOT_FOUND);
		}
		catch (AccessDeniedException e) {
			return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
		}
	}

	@PatchMapping("/{id}")
	public ApiResponse patchUser(@PathVariable("id") Long id, @RequestBody UserPatchDTO request, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			UserResponseDTO user = userService.patchOwnedProfile(id, request, authentication.getName());
			return new ApiResponse("User updated partially", user);
		}
		catch (NoSuchElementException e) {
			return new ApiResponse("User not found", HttpStatus.NOT_FOUND);
		}
		catch (AccessDeniedException e) {
			return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
		}
	}

	@PatchMapping("/me/profile_img")
	public ApiResponse uploadProfileImg(@RequestParam("profile_img") MultipartFile file, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		User user = userService.findByNick(authentication.getName()).orElseThrow();
		String filename = fileStorageService.store(file);
		user.setProfileImg(filename);
		UserResponseDTO updated = UserResponseDTO.fromEntity(userService.save(user));
		return new ApiResponse("Foto de perfil actualizada", updated);
	}

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null
			&& authentication.isAuthenticated()
			&& !"anonymousUser".equals(authentication.getName());
	}
}
