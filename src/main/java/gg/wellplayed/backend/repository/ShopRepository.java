package gg.wellplayed.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Shop;

// Esto le dice a Spring que implemente un DAO para el tipo Shop

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

	List<Shop> findByNameContainingIgnoreCase(String name);

}
