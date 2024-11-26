package gg.wellplayed.backend.dataTransfer.playlist;

import java.util.List;

import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Playlist;
import gg.wellplayed.backend.model.User;

public record PlaylistCreateDTO (String name, String description, Boolean isPrivate, List<Game> games, User author){
	
	public Playlist parseToPlatformEntity() {
		Playlist p = new Playlist();
		p.setName(this.name());
		p.setDescription(this.description());
		p.setIsPrivate(this.isPrivate());
		p.setGames(this.games);
		p.setAuthor(this.author);
		return p;
	}


}
