package gg.wellplayed.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gg.wellplayed.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
