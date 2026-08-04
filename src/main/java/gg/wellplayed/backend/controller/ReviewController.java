package gg.wellplayed.backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.review.ReviewCreateDTO;
import gg.wellplayed.backend.dataTransfer.review.ReviewPatchDTO;
import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.ReviewService;
import gg.wellplayed.backend.service.UserService;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	ReviewService reviewService;
	@Autowired
	GameService gameService;
	@Autowired
	UserService userService;

	/* CRUD Operations */

	@GetMapping()
	public ApiResponse listReviews() {
		List<Review> reviews = reviewService.findAll();
		return new ApiResponse( reviews);
	}

	@GetMapping("/{id}")
	public ApiResponse getReview(@PathVariable("id") Long id) {

		Review review = reviewService.getOne(id);
		String title = review.getTitle();
		System.out.println(review);
		return new ApiResponse(
			title,
			review,
			HttpStatus.OK);
	}


	@PostMapping
	public ApiResponse makeReview(@Valid @RequestBody ReviewCreateDTO reviewReq, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		// El autor SIEMPRE se resuelve del JWT, nunca del body: si no, cualquiera podría
		// publicar una review "como" otro usuario mandando su id en reviewReq.author.
		Review review = Review.builder()
				.author(userService.findByNick(authentication.getName()).orElseThrow())
				.game(gameService.getOne(reviewReq.getGame()))
				.title(reviewReq.getTitle())
				.body(reviewReq.getBody())
				.score(reviewReq.getScore())
				.build();
		Review saved = reviewService.saveReview(review);

		return new ApiResponse("Review created successfully", saved, HttpStatus.CREATED);
	}


	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @Valid @RequestBody Review reviewReq, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		if (!isOwner(reviewService.getOne(id), authentication)) {
			return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
		}
		return new ApiResponse(
			"Review updated",
			reviewService.update(id, reviewReq));
	}

	@PatchMapping("/{id}")
	public ApiResponse patch(@PathVariable("id") Long id, @Valid @RequestBody ReviewPatchDTO reviewReq, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		if (!isOwner(reviewService.getOne(id), authentication)) {
			return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
		}
		return new ApiResponse(
			"Review updated partially",
			reviewService.patch(id, reviewReq));
	}

	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable("id") Long id, Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return new ApiResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		if (!isOwner(reviewService.getOne(id), authentication)) {
			return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
		}
		return new ApiResponse(
			"Deleted review N° "+id.toString(),
			reviewService.delete(id));
	}

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null
			&& authentication.isAuthenticated()
			&& !"anonymousUser".equals(authentication.getName());
	}

	private boolean isOwner(Review review, Authentication authentication) {
		return review.getAuthor() != null
			&& review.getAuthor().getNick().equals(authentication.getName());
	}
}
