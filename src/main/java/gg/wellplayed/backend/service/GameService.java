package gg.wellplayed.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Tag;
import gg.wellplayed.backend.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	GameRepository gameRepo;
	
	public Game getOne(Long id) {
		return gameRepo.findById(id).get();
	}
	
	public List<Game> findAll() {
		return gameRepo.findAll();
	}

	public List<Game> findByTitle(String title) {
		return gameRepo.findByTitleContainingIgnoreCase(title);
	}

	public List<Game> filterGames(
			List<Long> tagIds,
			List<Long> platformIds,
			List<Long> studioIds,
			List<Long> franchiseIds,
			LocalDate startDate,
			LocalDate endDate,
			Float minStars,
			Float maxStars) {
		return findAll().stream()
			.filter(game -> tagIds == null || tagIds.isEmpty()
				|| (game.getTags() != null && game.getTags().stream().anyMatch(t -> tagIds.contains(t.getId()))))
			.filter(game -> platformIds == null || platformIds.isEmpty()
				|| (game.getPlatforms() != null && game.getPlatforms().stream().anyMatch(p -> platformIds.contains(p.getId()))))
			.filter(game -> studioIds == null || studioIds.isEmpty()
				|| (game.getStudios() != null && game.getStudios().stream().anyMatch(s -> studioIds.contains(s.getId()))))
			.filter(game -> franchiseIds == null || franchiseIds.isEmpty()
				|| (game.getFranchise() != null && franchiseIds.contains(game.getFranchise().getId())))
			.filter(game -> startDate == null || game.getReleaseDate() == null
				|| !game.getReleaseDate().isBefore(startDate))
			.filter(game -> endDate == null || game.getReleaseDate() == null
				|| !game.getReleaseDate().isAfter(endDate))
			.filter(game -> minStars == null || game.getCumulativeRating() >= minStars)
			.filter(game -> maxStars == null || game.getCumulativeRating() <= maxStars)
			.collect(java.util.stream.Collectors.toList());
	}
	
	public Game saveUser(Game gameReq) {
		return gameRepo.save(gameReq);
	}
	
	public Game update(Long id, Game newGame) {
		Game s = getOne(id);
		
		return gameRepo.save(s);
	}
	
	public void deleteById(Long id) {
		gameRepo.deleteById(id);
	}
}
