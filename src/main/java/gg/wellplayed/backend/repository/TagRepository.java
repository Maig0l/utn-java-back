package gg.wellplayed.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Tag;

// Esto le dice a Spring que implemente un DAO para el tipo Tag

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
