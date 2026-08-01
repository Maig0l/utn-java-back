package gg.wellplayed.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long>{

	List<Studio> findByNameContainingIgnoreCase(String name);

}
