package gg.wellplayed.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.Franchise;



@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {

}
