package gg.wellplayed.backend.dataTransfer.review;

public record ReviewPatchDTO(
	Float score,
	String title,
	String body
) {
}

