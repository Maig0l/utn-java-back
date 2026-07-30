package gg.wellplayed.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	List<Game> findByTitleContainingIgnoreCase(String title);

}
