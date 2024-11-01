package gg.wellplayed.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	GameService gameService;
	
	@GetMapping
	public List<Game> listGames() {
		return gameService.findAll();
	}
	
	@PostMapping
	public Game makeGame(@RequestBody Game gameReq) {
		return gameService.save(gameReq);
	}
	
	@DeleteMapping("/{id}")
	public String deleteGame(@PathVariable("id") Long id) {
		gameService.deleteById(id);
		return "Shop deleted!";
	}
}
