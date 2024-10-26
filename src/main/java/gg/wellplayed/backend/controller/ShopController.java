package gg.wellplayed.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.model.Shop;
import gg.wellplayed.backend.service.ShopService;

// TODO: Cuando el DAO levanta una excepción, el servidor retorna un error 500
// Esto debería recuperarse con un try/catch, devolviendo 404 o lo que corresponda

/** TODO: Los datos devueltos deberían ser envueltos en la forma que están las respuestas
  * del servidor en Express.
  * Por ejemplo: Java devuelve: {name: "Steam"...}
  * Mientras que Express devuelve: {message: "", data: {name: "Steam"}}
  */


// Indicamos que esta clase es un CONTROLADOR tipo REST (o sea que recibe Requests)
// Indicamos que todas las REQUESTS a /shops se mapean a este controlador/a esta clase

@RestController
@RequestMapping("/shops")
public class ShopController {
	// Autowired se encarga de la inyección de dependencias
	// El SERVICIO de tiendas (ShopService) actúa como el DAO
	@Autowired
	ShopService shopService;
	
	// GetMapping indica que este método se mapea a las request tipo GET /shop
	@GetMapping
	public List<Shop> listShops() {
		return shopService.findAll();
	}
	
	// PostMapping indica que este método se mapea a las request tipo POST /shop
	@PostMapping
	public Shop create(@RequestBody Shop shopReq) {
		return shopService.saveUser(shopReq);
	}
	
	// No vamos a trabajar con PATCH porque eso añade comprobaciones
	
	// PathVariable permite a la función tener conciencia del parámetro "id" que viene en la URL
	// Debe coincidir con el nombre de la variable en la firma de la func.
	@PutMapping("/{id}")
	public Shop update(@PathVariable("id") Long id, @RequestBody Shop shopReq) {
		return shopService.update(id, shopReq);
	}
	
	@DeleteMapping("/{id}")
	public Shop delete(@PathVariable("id") Long id) {
		return shopService.delete(id);
	}
}