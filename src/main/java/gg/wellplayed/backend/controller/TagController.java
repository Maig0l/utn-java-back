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
import gg.wellplayed.backend.model.Tag;
import gg.wellplayed.backend.service.TagService;



// TODO: Cuando el DAO levanta una excepción, el servidor retorna un error 500
// Esto debería recuperarse con un try/catch, devolviendo 404 o lo que corresponda

/** TODO: Los datos devueltos deberían ser envueltos en la forma que están las respuestas
  * del servidor en Express.
  * Por ejemplo: Java devuelve: {name: "Steam"...}
  * Mientras que Express devuelve: {message: "", data: {name: "Steam"}}
  */


// Indicamos que esta clase es un CONTROLADOR tipo REST (o sea que recibe Requests)
// Indicamos que todas las REQUESTS a /tags se mapean a este controlador/a esta clase
@RestController
@RequestMapping("/tags")
public class TagController {
	// Autowired se encarga de la inyección de dependencias
	// El SERVICIO de tiendas (TagService) actúa como el DAO
	@Autowired
	TagService tagService;
	
	/** CRUD Operations **/
	
	// GetMapping indica que este método se mapea a las request tipo GET /tag
	@GetMapping()
	public ApiResponse listTags() {
		List<Tag> tags = tagService.findAll();
		String msj = String.format("Total: %d tags", tags.size());

		return new ApiResponse( tags);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getTag(@PathVariable("id") Long id) {
		
		Tag tag = tagService.getOne(id);
		String[] data = {tag.getName(), tag.getDescription()};
		String name = tag.getName();
		System.out.println(tag);
		return new ApiResponse(
			name,
			tag, 
			HttpStatus.OK);
	}
	
	// PostMapping indica que este método se mapea a las request tipo POST /tag
	@PostMapping
	public ApiResponse create(@RequestBody Tag tagReq) {
		return new ApiResponse(
				"Tag created successfully",
				tagService.saveUser(tagReq),
				HttpStatus.CREATED);
	}
	
	// No vamos a trabajar con PATCH porque eso añade comprobaciones
	
	// PathVariable permite a la función tener conciencia del parámetro "id" que viene en la URL
	// Debe coincidir con el nombre de la variable en la firma de la func.
	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody Tag tagReq) {
		return new ApiResponse(
			"Tag updated",
			tagService.update(id, tagReq));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable("id") Long id) {
		return new ApiResponse(
			"Deleted tag N° "+id.toString(),
			tagService.delete(id));
	}
}
