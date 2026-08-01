package gg.wellplayed.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Review;

// Esto le dice a Spring que implemente un DAO para el tipo Review

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByAuthor_Nick(String nick);

}
