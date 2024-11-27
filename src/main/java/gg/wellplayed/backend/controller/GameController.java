package gg.wellplayed.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.web.bind.annotation.CrossOrigin;


import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.game.GameCreateDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkPlatformDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkPlaylistDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkShopDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkStudioDTO;
import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Platform;
import gg.wellplayed.backend.model.Playlist;
import gg.wellplayed.backend.model.Shop;
import gg.wellplayed.backend.model.Studio;
import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.ShopService;
import gg.wellplayed.backend.service.StudioService;
import gg.wellplayed.backend.service.PlatformService;
import gg.wellplayed.backend.service.PlaylistService;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	GameService gameService;
	@Autowired
	ShopService shopService;
	@Autowired
	StudioService studioService;
	@Autowired
	PlatformService platformService;
	@Autowired
	PlaylistService playlistService;
	

	/*  CRUD operations  */
	
	@GetMapping
	public ApiResponse listGames() {
		List<Game> games = gameService.findAll();
		String msj = String.format("Total: %d games", games.size());

		return new ApiResponse(msj, games);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getGame(@PathVariable("id") Long id) {
		
		Game game = gameService.getOne(id);
		String title = game.getTitle();
		System.out.println(game);
		return new ApiResponse(
			title,
			game, 
			HttpStatus.OK);
	}

	@PostMapping 
	 public ApiResponse makeGame(@RequestBody GameCreateDTO gameReq) { 
	  Game game = gameReq.parseToGameEntity(); 
	  Game saved = gameService.saveUser(game); 
	   
	  ApiResponse response = new ApiResponse("Game created successfully", saved, HttpStatus.CREATED); 
	  return response; 
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse deleteGame(@PathVariable("id") Long id) {
		gameService.deleteById(id);
		return new ApiResponse("Game  deleted");
	}
	
	

	@PatchMapping("/{id}")
	public ApiResponse patch(@PathVariable("id") Long id, @RequestBody  JsonPatch gameReq) {
		try {Game s = gameService.getOne(id);
		Game patch = applyPatchToGame(gameReq, s);
		return new ApiResponse(
			"Game updated",
			gameService.saveUser(patch));
		}
		catch (JsonPatchException | JsonProcessingException e) {
	        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR);
	     
	    }
	}
	
	/*  Relationship opeartions	 */
	
	private Game applyPatchToGame(
		JsonPatch patch, Game s) throws JsonPatchException, JsonProcessingException {
		ObjectMapper mapeador= new ObjectMapper();
		    JsonNode patched = patch.apply(mapeador.convertValue(s, JsonNode.class));
		    return mapeador.treeToValue(patched, Game.class);
		
	}


	@PostMapping("/{id}/shops")
	public ApiResponse linkShop(@PathVariable("id") Long gameId, @RequestBody(required = true) LinkShopDTO linkShopReq) {
		Game game = gameService.getOne(gameId);
		Shop shop = shopService.getOne(linkShopReq.shopId());
		game.linkShop(shop);
		gameService.saveUser(game);
		return new ApiResponse("Dang, they really sell this there? (Game associated to shop and updated)");
	}
	
	@PostMapping("/{id}/studios")
	public ApiResponse linkStudio(@PathVariable("id") Long gameId, @RequestBody(required = true) LinkStudioDTO linkStudioReq) {
		Game game = gameService.getOne(gameId);
		Studio studio = studioService.getOne(linkStudioReq.studioId());
		game.linkStudio(studio);
		gameService.saveUser(game);
		return new ApiResponse("Studio linked correctly");
	}
	
	@PostMapping("/{id}/platforms")
	public ApiResponse linkPlatform(@PathVariable("id") Long gameId, @RequestBody(required = true) LinkPlatformDTO linkPlatformReq) {
		Game game = gameService.getOne(gameId);
		Platform platform = platformService.getOne(linkPlatformReq.platformId());
		game.linkPlatform(platform);
		gameService.saveUser(game);
		return new ApiResponse("Platform linked correctly");
	}
	
	@PostMapping("/{id}/playlists")
	public ApiResponse linkPlaylist(@PathVariable("id") Long gameId, @RequestBody(required = true) LinkPlaylistDTO linkPlaylistReq) {
		Game game = gameService.getOne(gameId);
		Playlist playlist = playlistService.getOne(linkPlaylistReq.playlistId());
		game.linkPlaylist(playlist);
		gameService.saveUser(game);
		return new ApiResponse("Playlist linked correctly");
	}
}
