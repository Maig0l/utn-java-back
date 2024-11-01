package gg.wellplayed.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
