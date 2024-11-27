package gg.wellplayed.backend.dataTransfer.review;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.UserService;



public record ReviewCreateDTO(
	String title,
	String body,
	float score,
	Long game,
	Long author
	
	
	) {
	
	public Review parseToReview(GameService gameService, UserService userService) {
		Review review = new Review();
		review.setTitle(this.title);
		review.setBody(this.body);
		review.setAuthor(userService.getOne(this.author));
		review.setScore(this.score);
		//Game game2 = (gameService.getOne(this.game));
		review.setGame(gameService.getOne(this.game));
		return review;
	}
	

}


