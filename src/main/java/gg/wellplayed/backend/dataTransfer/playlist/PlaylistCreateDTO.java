package gg.wellplayed.backend.dataTransfer.playlist;

import java.util.List;

public record PlaylistCreateDTO(
	String name,
	String description,
	Boolean isPrivate,
	Long owner,
	List<Long> games
) {
}
