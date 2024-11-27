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
import gg.wellplayed.backend.service.ReviewService;



// TODO: Cuando el DAO levanta una excepción, el servidor retorna un error 500
// Esto debería recuperarse con un try/catch, devolviendo 404 o lo que corresponda

/** TODO: Los datos devueltos deberían ser envueltos en la forma que están las respuestas
  * del servidor en Express.
  * Por ejemplo: Java devuelve: {name: "Steam"...}
  * Mientras que Express devuelve: {message: "", data: {name: "Steam"}}
  */


// Indicamos que esta clase es un CONTROLADOR tipo REST (o sea que recibe Requests)
// Indicamos que todas las REQUESTS a /reviews se mapean a este controlador/a esta clase
@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/reviews")
public class ReviewController {
	// Autowired se encarga de la inyección de dependencias
	// El SERVICIO de tiendas (ReviewService) actúa como el DAO
	@Autowired
	ReviewService reviewService;
	
	/** CRUD Operations **/
	
	// GetMapping indica que este método se mapea a las request tipo GET /review
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
	
	// PostMapping indica que este método se mapea a las request tipo POST /review
	@PostMapping 
	 public ApiResponse makeGame(@RequestBody ReviewCreateDTO reviewReq) { 
	  Review review = reviewReq.parseToReview(); 
	  Review saved = reviewService.saveUser(review); 
	   
	  ApiResponse response = new ApiResponse("Review created successfully", saved, HttpStatus.CREATED); 
	  return response; 
	}
	// No vamos a trabajar con PATCH porque eso añade comprobaciones
	
	// PathVariable permite a la función tener conciencia del parámetro "id" que viene en la URL
	// Debe coincidir con el nombre de la variable en la firma de la func.
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

