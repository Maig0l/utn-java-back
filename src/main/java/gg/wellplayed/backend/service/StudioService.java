package gg.wellplayed.backend.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Studio;
import gg.wellplayed.backend.repository.StudioRepository;

@Service
public class StudioService {
	@Autowired
	StudioRepository studioRepository;
	
	public Studio saveUser(Studio studio) {
		return studioRepository.save(studio);
	}
	
	public List<Studio> findAll(){
		return studioRepository.findAll();
	}
	
	public Studio getOne(Long id) {
		return studioRepository.getReferenceById(id);
	}
	
	public Studio update (Long id, Studio newStudio) {
		Studio s = getOne(id);
		s.setName(newStudio.getName());
		s.setType(newStudio.getType());
		s.setSite(newStudio.getSite());
		
		return studioRepository.save(s); 
	}
	
	public Studio delete(Long id) {
		Studio s = getOne(id);
		Studio ret = new Studio(s);
		studioRepository.delete(s);
		return ret;
	}
}
