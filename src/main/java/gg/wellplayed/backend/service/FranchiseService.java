package gg.wellplayed.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Franchise;
import gg.wellplayed.backend.repository.FranchiseRepository;

@Service
public class FranchiseService {
	// Autowired se encarga de la inyección de dependencias
	@Autowired
	FranchiseRepository franchiseRepository;
	
	public Franchise saveUser(Franchise franchise) {
		return franchiseRepository.save(franchise);
	}
	
	public List<Franchise> findAll() {
		return franchiseRepository.findAll();
	}
	
	public Franchise getOne(Long id) {
		// Uso getReferenceById porque getOne y getById están deprecados lol
		// pero la función en mi DAO puedo llamarla como yo quiera entonces lo pongo getOne
		return franchiseRepository.findById(id).get();
	}
	
	public Franchise update(Long id, Franchise newFranchise) {
		Franchise s = getOne(id);
		s.setName(newFranchise.getName());
		
		return franchiseRepository.save(s);
	}
	
	public Franchise delete(Long id) {
		Franchise s = getOne(id);
		Franchise ret = new Franchise(s);
		franchiseRepository.delete(s);
		return ret;
	}
}

