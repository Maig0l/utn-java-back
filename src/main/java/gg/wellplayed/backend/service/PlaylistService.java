package gg.wellplayed.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Playlist;
import gg.wellplayed.backend.repository.PlaylistRepository;

@Service
public class PlaylistService {
	@Autowired
	PlaylistRepository playlistRepository;
	
	
	public Playlist savePlaylist(Playlist playlist) {
		return playlistRepository.save(playlist);
	}
	
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}
	
	public Playlist getOne(Long id) {
		return playlistRepository.findById(id).get();
	}
	
	public Playlist update(Long id, Playlist newPlaylist) {
		Playlist p = getOne(id);
		p.setName(newPlaylist.getName());
		p.setDescription(newPlaylist.getDescription());
		p.setIsPrivate(newPlaylist.getIsPrivate());
		//p.setGames(newPlaylist.getGames());
		
		return playlistRepository.save(p);
	}
	
	public Playlist delete(Long id) {
		Playlist p = getOne(id);
		Playlist ret = new Playlist(p);
		playlistRepository.delete(p);
		return ret;
	}

}
