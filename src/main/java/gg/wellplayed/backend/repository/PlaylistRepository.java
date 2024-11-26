package gg.wellplayed.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Playlist;

@Repository
public interface PlaylistRepository  extends JpaRepository<Playlist, Long> {

}
