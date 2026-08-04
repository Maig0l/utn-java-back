package gg.wellplayed.backend.dataTransfer.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewPostDTO(
	String title,
	String body,
	@Min(1)
	@Max(5)
	int score
) {
}
