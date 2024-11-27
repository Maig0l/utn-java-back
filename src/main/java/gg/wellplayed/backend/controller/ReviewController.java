package gg.wellplayed.backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.review.ReviewCreateDTO;
import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.ReviewService;
import gg.wellplayed.backend.service.UserService;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
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
	 public ApiResponse makeReview(@RequestBody ReviewCreateDTO reviewReq) {
	  Review review = reviewReq.parseToReview(gameService, userService); 
	  Review saved = reviewService.saveReview(review); 
	   
	  ApiResponse response = new ApiResponse("Review created successfully", saved, HttpStatus.CREATED); 
	  return response; 
	}


	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody Review reviewReq) {
		return new ApiResponse(
			"Review updated",
			reviewService.update(id, reviewReq));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable("id") Long id) {
		return new ApiResponse(
			"Deleted review N° "+id.toString(),
			reviewService.delete(id));
	}
}

