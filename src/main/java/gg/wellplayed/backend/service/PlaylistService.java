package gg.wellplayed.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.dataTransfer.playlist.PlaylistCreateDTO;
import gg.wellplayed.backend.dataTransfer.playlist.PlaylistPatchDTO;
import gg.wellplayed.backend.model.Playlist;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.repository.GameRepository;
import gg.wellplayed.backend.repository.PlaylistRepository;

@Service
public class PlaylistService {
	@Autowired
	PlaylistRepository playlistRepository;
	@Autowired
	GameRepository gameRepository;
	@Autowired
	UserService userService;

	public Playlist savePlaylist(Playlist playlist) {
		return playlistRepository.save(playlist);
	}

	public Playlist createFromDTO(PlaylistCreateDTO dto) {
		User owner = userService.getOne(dto.owner());

		Playlist playlist = new Playlist();
		playlist.setName(dto.name());
		playlist.setDescription(dto.description());
		playlist.setIsPrivate(dto.isPrivate() != null ? dto.isPrivate() : false);
		playlist.setAuthor(owner);
		if (dto.games() != null) {
			playlist.setGames(dto.games().stream()
				.map(gameId -> gameRepository.findById(gameId).orElseThrow())
				.collect(java.util.stream.Collectors.toList()));
		}

		return playlistRepository.save(playlist);
	}
	
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	public List<Playlist> findByOwner(Long ownerId) {
		return playlistRepository.findByAuthor_Id(ownerId);
	}

	public Long resolveOwnerId(String nick) {
		return userService.findByNick(nick).map(User::getId).orElse(null);
	}
	
	public Playlist getOne(Long id) {
		return playlistRepository.findById(id).orElseThrow();
	}
	
	public Playlist update(Long id, Playlist newPlaylist) {
		Playlist p = getOne(id);
		p.setName(newPlaylist.getName());
		p.setDescription(newPlaylist.getDescription());
		p.setIsPrivate(newPlaylist.getIsPrivate());
		if (newPlaylist.getGames() != null) {
			p.setGames(newPlaylist.getGames());
		}

		return playlistRepository.save(p);
	}

	public Playlist patch(Long id, PlaylistPatchDTO playlistPatch) {
		Playlist p = getOne(id);

		if (playlistPatch.name() != null) {
			p.setName(playlistPatch.name());
		}
		if (playlistPatch.description() != null) {
			p.setDescription(playlistPatch.description());
		}
		if (playlistPatch.isPrivate() != null) {
			p.setIsPrivate(playlistPatch.isPrivate());
		}

		if (playlistPatch.games() != null) {
			p.setGames(playlistPatch.games().stream()
				.map(gameId -> gameRepository.findById(gameId).orElseThrow())
				.collect(java.util.stream.Collectors.toList()));
		}

		return playlistRepository.save(p);
	}

	public Playlist delete(Long id) {
		Playlist p = getOne(id);
		Playlist ret = new Playlist();
		ret.setId(p.getId());
		ret.setName(p.getName());
		ret.setDescription(p.getDescription());
		ret.setIsPrivate(p.getIsPrivate());
		ret.setGames(p.getGames());
		ret.setAuthor(p.getAuthor());
		playlistRepository.delete(p);
		return ret;
	}

}
