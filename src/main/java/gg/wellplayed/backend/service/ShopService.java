package gg.wellplayed.backend.service;

import java.util.List;

//tengo hambre che

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Shop;
import gg.wellplayed.backend.repository.ShopRepository;

// Acá implementamos las funciones del DAO para Shop que necesitemos,
// y agregamos lógica de negocio si hace falta.

@Service
public class ShopService {
	// Autowired se encarga de la inyección de dependencias
	@Autowired
	ShopRepository shopRepository;
	
	public Shop saveUser(Shop shop) {
		return shopRepository.save(shop);
	}
	
	public List<Shop> findAll() {
		return shopRepository.findAll();
	}

	public List<Shop> findByName(String name) {
		return shopRepository.findByNameContainingIgnoreCase(name);
	}
	
	public Shop getOne(Long id) {
		// Uso getReferenceById porque getOne y getById están deprecados lol
		// pero la función en mi DAO puedo llamarla como yo quiera entonces lo pongo getOne
		return shopRepository.findById(id).get();
	}
	
	public Shop update(Long id, Shop newShop) {
		Shop s = getOne(id);
		s.setName(newShop.getName());
		s.setIcon(newShop.getIcon());
		s.setSiteUrl(newShop.getSiteUrl());
		
		return shopRepository.save(s);
	}

	public Shop patch(Long id, Shop shopPatch) {
		Shop s = getOne(id);

		if (shopPatch.getName() != null) {
			s.setName(shopPatch.getName());
		}
		if (shopPatch.getIcon() != null) {
			s.setIcon(shopPatch.getIcon());
		}
		if (shopPatch.getSiteUrl() != null) {
			s.setSiteUrl(shopPatch.getSiteUrl());
		}

		return shopRepository.save(s);
	}

	public Shop delete(Long id) {
		Shop s = getOne(id);
		Shop ret = new Shop(s);
		shopRepository.delete(s);
		return ret;
	}
}
