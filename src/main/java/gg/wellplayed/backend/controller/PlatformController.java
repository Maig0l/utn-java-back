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
import gg.wellplayed.backend.model.Platform;
import gg.wellplayed.backend.service.PlatformService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/platforms")

public class PlatformController {
	@Autowired
	PlatformService platformService;

	/*  CRUD operations  */
	
	@GetMapping
	public ApiResponse listPlatforms() {
		List<Platform> platforms = platformService.findAll();
		String msj = String.format("Total = %d platforms", platforms.size());
		
		return new ApiResponse(msj, platforms);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getPlatform(@PathVariable("id") Long id) {
		return new ApiResponse(	"Tuki toma plataforma",	platformService.getOne(id));
	}
	
	@PostMapping
	public ApiResponse create(@RequestBody Platform platformReq) {
		//Platform platform = platformReq.parseToPlatformEntity();
		return new ApiResponse("Platform created successfully",	platformService.saveUser(platformReq), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody Platform platformReq) {
		return new ApiResponse("Platform updated", platformService.update(id, platformReq));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse delete (@PathVariable("id") Long id) {
		return new ApiResponse("Deleted plaftorm N° "+id.toString(), platformService.delete(id));
	}
}
