package gg.wellplayed.backend.dataTransfer.review;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class ReviewCreateDTO {
	String title;
	String body;
	float score;
	Long game;
	Long author;
}


