package gg.wellplayed.backend.dataTransfer.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewPatchDTO(
	@Min(1)
	@Max(5)
	Integer score,
	String title,
	String body
) {
}

