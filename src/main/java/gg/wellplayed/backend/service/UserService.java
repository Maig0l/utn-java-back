package gg.wellplayed.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public User save(User user) {
		return userRepo.save(user);
	}
}