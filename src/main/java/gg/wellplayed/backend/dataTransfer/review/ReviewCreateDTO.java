package gg.wellplayed.backend.dataTransfer.review;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.model.User;



public record ReviewCreateDTO(
	String title,
	String body,
	float score,
	Game game,
	User author
	
	
	) {
	
	public Review parseToReview() {
		
		Review review = new Review();
		review.setTitle(this.title);
		review.setBody(this.body);
		review.setAuthor(this.author);
		review.setScore(this.score);
		review.setGame(this.game);
		return review;
	}
}


