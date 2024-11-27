package gg.wellplayed.backend.dataTransfer.game;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.service.FranchiseService;
import lombok.AllArgsConstructor;
import lombok.Data;

public record GameCreateDTO(
	String title,
	String synopsis,
	String portrait,
	String banner,
	String pictures,
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate releaseDate
	) {
	
	
	public Game parseToGameEntity() {
		Game g  =new Game();
		g.setTitle(this.title);
		g.setSynopsis(this.synopsis);
		g.setBanner(this.banner);
		g.setPortrait(this.portrait);
		g.setPictures(this.pictures);
		g.setReleaseDate(this.releaseDate());
		return g;
	}
}
