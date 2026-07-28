package gg.wellplayed.backend.dataTransfer.user;

import gg.wellplayed.backend.model.Tag;

public record TagSummaryDTO(
	Long id,
	String name
) {
	public static TagSummaryDTO fromEntity(Tag tag) {
		return new TagSummaryDTO(tag.getId(), tag.getName());
	}
}
