package gg.wellplayed.backend.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Tag;
import gg.wellplayed.backend.repository.TagRepository;

// Acá implementamos las funciones del DAO para Tag que necesitemos,
// y agregamos lógica de negocio si hace falta.

@Service
public class TagService {
	// Autowired se encarga de la inyección de dependencias
	@Autowired
	TagRepository tagRepository;
	
	public Tag saveUser(Tag tag) {
		return tagRepository.save(tag);
	}
	
	public List<Tag> findAll() {
		return tagRepository.findAll();
	}

	public List<Tag> findByName(String name) {
		return tagRepository.findByNameContainingIgnoreCase(name);
	}
	
	public Tag getOne(Long id) {
		return tagRepository.findById(id).orElseThrow();
	}
	
	public Tag update(Long id, Tag newTag) {
		Tag s = getOne(id);
		s.setName(newTag.getName());
		s.setDescription(newTag.getDescription());
		
		return tagRepository.save(s);
	}

	public Tag patch(Long id, Tag tagPatch) {
		Tag s = getOne(id);

		if (tagPatch.getName() != null) {
			s.setName(tagPatch.getName());
		}
		if (tagPatch.getDescription() != null) {
			s.setDescription(tagPatch.getDescription());
		}

		return tagRepository.save(s);
	}

	public Tag delete(Long id) {
		Tag s = getOne(id);
		Tag ret = new Tag(s);
		tagRepository.delete(s);
		return ret;
	}
}
