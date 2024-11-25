package gg.wellplayed.backend.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.game.GameCreateDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkShopDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkStudioDTO;
import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Shop;
import gg.wellplayed.backend.model.Studio;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.ShopService;
import gg.wellplayed.backend.service.StudioService;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	GameService gameService;
	@Autowired
	ShopService shopService;
	@Autowired
	StudioService studioService;
	

	/*  CRUD operations 
	 */
	
	@GetMapping
	public ApiResponse listGames() {
		List<Game> games = gameService.findAll();
		String msj = String.format("Total: %d games", games.size());

		return new ApiResponse(msj, games);
	}
	
	@PostMapping
	public ApiResponse makeGame(@RequestBody GameCreateDTO gameReq) {
		Game game = gameReq.parseToGameEntity();
		Game saved = gameService.save(game);
		
		ApiResponse response = new ApiResponse("Game created successfully", saved, HttpStatus.CREATED);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse deleteGame(@PathVariable("id") Long id) {
		gameService.deleteById(id);
		return new ApiResponse("Game  deleted");
	}
	
	
	
	/*  Relationship opeartions
	 */
	
	@PostMapping("/{id}/shops")
	public ApiResponse linkShop(@PathVariable("id") Long gameId, @RequestBody(required = true) LinkShopDTO linkShopReq) {
		Game game = gameService.getOne(gameId);
		Shop shop = shopService.getOne(linkShopReq.shopId());
		game.linkShop(shop);
		gameService.save(game);
		return new ApiResponse("Dang, they really sell this there? (Game associated to shop and updated)");
	}
	
	@PostMapping("/{id}/studios")
	public ApiResponse linkStudio(@PathVariable("id") Long gameId, @RequestBody(required = true) LinkStudioDTO linkStudioReq) {
		Game game = gameService.getOne(gameId);
		Studio studio = studioService.getOne(linkStudioReq.StudioId());
		game.linkStudio(studio);
		gameService.save(game);
		return new ApiResponse("Studio linked correctly");
	}
}
