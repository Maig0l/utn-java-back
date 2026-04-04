package gg.wellplayed.backend.dataTransfer.playlist;

import java.util.List;

public record PlaylistPatchDTO(
	String name,
	String description,
	Boolean isPrivate,
	List<Long> games
) {
}

