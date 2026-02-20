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
	
	public Shop getOne(Long id) {
		// Uso getReferenceById porque getOne y getById están deprecados lol
		// pero la función en mi DAO puedo llamarla como yo quiera entonces lo pongo getOne
		return shopRepository.findById(id).get();
	}
	
	public Shop update(Long id, Shop newShop) {
		Shop s = getOne(id);
		s.setName(newShop.getName());
		s.setImg(newShop.getImg());
		s.setSite(newShop.getSite());
		
		return shopRepository.save(s);
	}
	
	
	public Shop patch(Long id, Shop newShop) {
		Shop s = getOne(id);
		if( newShop.getName() != null) {
		s.setName(newShop.getName());
		}
		if( newShop.getImg() != null) {
		s.setImg(newShop.getImg());
		}
		if( newShop.getSite() != null) {
		s.setSite(newShop.getSite());
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
