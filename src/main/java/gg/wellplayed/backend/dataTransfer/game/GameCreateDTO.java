package gg.wellplayed.backend.dataTransfer.game;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

public record GameCreateDTO(
	String title,
	String synopsis,
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate releaseDate
	) {
	
	public Game parseToGameEntity() {
		Game g  =new Game();
		g.setTitle(this.title);
		g.setSynopsis(this.synopsis);
		return g;
	}
}
