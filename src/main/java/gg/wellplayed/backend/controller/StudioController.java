package gg.wellplayed.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.model.Studio;
import gg.wellplayed.backend.service.StudioService;

@RestController
@RequestMapping("/studios")

public class StudioController {
	@Autowired
	StudioService studioService;
	
	@GetMapping
	public ApiResponse listStudios() {
		List<Studio> studios = studioService.findAll();
		String msj = String.format("Total = %d studios", studios.size());
		
		return new ApiResponse(msj, studios);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getStudio(@PathVariable("id") Long id) {
		return new ApiResponse(
			"Tuki",
			studioService.getOne(id));
	}
	
	@PostMapping
	public ApiResponse create(@RequestBody Studio studioReq) {
		return new ApiResponse(
				"Studio created successfully",
				studioService.saveUser(studioReq),
				HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody Studio studioReq) {
		return new ApiResponse(
				"Studio updated",
				studioService.update(id, studioReq));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse delete (@PathVariable("id") Long id) {
		return new ApiResponse(
				"Deleted studio N° "+id.toString(),
				studioService.delete(id));
	}
}
