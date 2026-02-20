package gg.wellplayed.backend.service;

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
	
	public Game saveUser(Game gameReq) {
		return gameRepo.save(gameReq);
	}
	
	public Game update(Long id, Game newGame) {
		Game s = getOne(id);
		
		return gameRepo.save(s);
	}
	
	public Game patch(Long id, Game newGame) {
		Game s = getOne(id);
		
		if( newGame.getTitle() != null) {
			s.setTitle(newGame.getTitle());
			}
			if( newGame.getSynopsis() != null) {
			s.setSynopsis(newGame.getSynopsis());
			}
			if( newGame.getReleaseDate() != null) {
			s.setReleaseDate(newGame.getReleaseDate());
			}
			if( newGame.getPortrait() != null) {
			s.setPortrait(newGame.getPortrait());
			}
			if( newGame.getBanner() != null) {
			s.setBanner(newGame.getBanner());
			}
			if( newGame.getFranchise() != null) {
			s.setFranchise(newGame.getFranchise());
			}
			if( newGame.getCumulativeRating() != null) {
			s.setCumulativeRating(newGame.getCumulativeRating());
			}
			if( newGame.getReviewCount() != null) {
			s.setReviewCount(newGame.getReviewCount());
			}
		
		return gameRepo.save(s);
	}
	
	public void deleteById(Long id) {
		gameRepo.deleteById(id);
	}
}
