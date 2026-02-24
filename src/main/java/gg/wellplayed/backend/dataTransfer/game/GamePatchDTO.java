package gg.wellplayed.backend.dataTransfer.game;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

public record GamePatchDTO(
	String title,
	String synopsis,
	String portrait,
	String banner,
	String pictures,
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate releaseDate,
	List<Long> tags,
	List<Long> studios,
	List<Long> shops,
	List<Long> platforms
) {
}

