package gg.wellplayed.backend.dataTransfer.game;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.User;

public record GameCreateDTO(
	String name,
	String summary,
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate releasedAt
	) {
	
	public Game parseToGameEntity() {
		Game g  =new Game();
		g.setName(this.name());
		g.setSummary(this.summary);
		g.setReleasedAt(releasedAt);
		return g;
	}
}
