package gg.wellplayed.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.franchise.LinkGameDTO;
import gg.wellplayed.backend.model.Franchise;
import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.service.FranchiseService;
import gg.wellplayed.backend.service.GameService;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

		@Autowired
		FranchiseService franchiseService;
		@Autowired
		GameService gameService;

		/** CRUD Operations **/
		
		
		@GetMapping()
		public ApiResponse listFranchises() {
			List<Franchise> franchises = franchiseService.findAll();
			String msj = String.format("Total: %d franchises", franchises.size());

			return new ApiResponse(msj, franchises);
		}
		
		@GetMapping("/{id}")
		public ApiResponse getFranchise(@PathVariable("id") Long id) {
			return new ApiResponse(
				"",
				franchiseService.getOne(id));
		}

		@GetMapping("/search")
		public ApiResponse search(@RequestParam("name") String name) {
			return new ApiResponse(franchiseService.findByName(name));
		}

		
		@PostMapping
		public ApiResponse create(@RequestBody Franchise franchiseReq) {
			return new ApiResponse(
					"Franchise created successfully",
					franchiseService.saveUser(franchiseReq),
					HttpStatus.CREATED);
		}
		
		
		@PutMapping("/{id}")
		public ApiResponse update(@PathVariable("id") Long id, @RequestBody Franchise franchiseReq) {
			return new ApiResponse(
				"Franchise updated",
				franchiseService.update(id, franchiseReq));
		}

		@PatchMapping("/{id}")
		public ApiResponse patch(@PathVariable("id") Long id, @RequestBody Franchise franchiseReq) {
			return new ApiResponse(
				"Franchise updated partially",
				franchiseService.patch(id, franchiseReq));
		}

		@DeleteMapping("/{id}")
		public ApiResponse delete(@PathVariable("id") Long id) {
			return new ApiResponse(
				"Deleted franchise N° "+id.toString(),
				franchiseService.delete(id));
		}

		/** Relationship operations **/

		@PostMapping("/{id}/games")
		public ApiResponse linkGame(@PathVariable("id") Long franchiseId, @RequestBody LinkGameDTO linkGameReq) {
			Franchise franchise = franchiseService.getOne(franchiseId);
			Game game = gameService.getOne(linkGameReq.gameId());
			game.setFranchise(franchise);
			gameService.saveUser(game);
			return new ApiResponse("Game linked to franchise correctly");
		}

		@DeleteMapping("/{id}/games/{gameId}")
		public ApiResponse unlinkGame(@PathVariable("id") Long franchiseId, @PathVariable("gameId") Long gameId) {
			Game game = gameService.getOne(gameId);
			if (game.getFranchise() == null || !game.getFranchise().getId().equals(franchiseId)) {
				return new ApiResponse("Game is not linked to this franchise", HttpStatus.BAD_REQUEST);
			}
			game.setFranchise(null);
			gameService.saveUser(game);
			return new ApiResponse("Game unlinked from franchise correctly");
		}
}
