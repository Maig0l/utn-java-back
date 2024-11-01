package gg.wellplayed.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	GameRepository gameRepo;
	
	public List<Game> findAll() {
		return gameRepo.findAll();
	}
	
	public Game save(Game gameReq) {
		return gameRepo.save(gameReq);
	}
	
	public void deleteById(Long id) {
		gameRepo.deleteById(id);
	}
}
