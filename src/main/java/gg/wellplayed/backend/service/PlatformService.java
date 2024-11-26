package gg.wellplayed.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Platform;
import gg.wellplayed.backend.repository.PlatformRepository;

@Service
public class PlatformService {
	@Autowired
	PlatformRepository platformRepository;
	
	
	public Platform saveUser(Platform platform) {
		return platformRepository.save(platform);
	}
	
	public List<Platform> findAll() {
		return platformRepository.findAll();
	}
	
	public Platform getOne(Long id) {
		return platformRepository.findById(id).get();
	}
	
	public Platform update(Long id, Platform newPlatform) {
		Platform p = getOne(id);
		p.setName(newPlatform.getName());
		p.setImg(newPlatform.getImg());
		
		return platformRepository.save(p);
	}
	
	public Platform delete(Long id) {
		Platform p = getOne(id);
		Platform ret = new Platform(p);
		platformRepository.delete(p);
		return ret;
	}
	

}
