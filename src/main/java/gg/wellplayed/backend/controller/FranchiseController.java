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
import gg.wellplayed.backend.model.Franchise;
import gg.wellplayed.backend.service.FranchiseService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/franchises")
public class FranchiseController {
	
		@Autowired
		FranchiseService franchiseService;
		
		/** CRUD Operations **/
		
		
		@GetMapping()
		public ApiResponse listFranchises() {
			List<Franchise> franchises = franchiseService.findAll();
			String msj = String.format("Total: %d franchises", franchises.size());

			return new ApiResponse(msj, franchises);
		}
		
		@GetMapping("/{id}")
		public ApiResponse getFranchise(@PathVariable("id") Long id) {
			return new ApiResponse(
				"",
				franchiseService.getOne(id));
		}
		
		
		@PostMapping
		public ApiResponse create(@RequestBody Franchise franchiseReq) {
			return new ApiResponse(
					"Franchise created successfully",
					franchiseService.saveUser(franchiseReq),
					HttpStatus.CREATED);
		}
		
		
		@PutMapping("/{id}")
		public ApiResponse update(@PathVariable("id") Long id, @RequestBody Franchise franchiseReq) {
			return new ApiResponse(
				"Franchise updated",
				franchiseService.update(id, franchiseReq));
		}
		
		@DeleteMapping("/{id}")
		public ApiResponse delete(@PathVariable("id") Long id) {
			return new ApiResponse(
				"Deleted franchise N° "+id.toString(),
				franchiseService.delete(id));
		}
}
