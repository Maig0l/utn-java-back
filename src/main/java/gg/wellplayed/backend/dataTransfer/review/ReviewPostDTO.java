package gg.wellplayed.backend.dataTransfer.review;

public record ReviewPostDTO(
	String title,
	String body,
	float score
) {
}
