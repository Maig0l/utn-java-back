package gg.wellplayed.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

	List<Platform> findByNameContainingIgnoreCase(String name);

}
