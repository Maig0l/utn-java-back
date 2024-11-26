package gg.wellplayed.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.model.Playlist;
import gg.wellplayed.backend.service.PlaylistService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/playlists")
public class PlaylistController {
	@Autowired
	PlaylistService playlistService;
	
	/*  CRUD operations  */
	
	@GetMapping
	public ApiResponse listPlaylists() {
		List<Playlist> platforms = playlistService.findAll();
		String msj = String.format("Total = %d playlists", platforms.size());
		
		return new ApiResponse(msj, platforms);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getPlaylist(@PathVariable("id") Long id) {
		return new ApiResponse(	"Tuki toma playlist",	playlistService.getOne(id));
	}
	
	@PostMapping
	public ApiResponse create(@RequestBody Playlist platformReq) {
		//Platform platform = platformReq.parseToPlatformEntity();
		return new ApiResponse("Playlist created successfully",	playlistService.savePlaylist(platformReq), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody Playlist platformReq) {
		return new ApiResponse("Playlist updated", playlistService.update(id, platformReq));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse delete (@PathVariable("id") Long id) {
		return new ApiResponse("Deleted Playlist N° "+id.toString(), playlistService.delete(id));
	}

}
