package gg.wellplayed.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.dataTransfer.user.UserPatchDTO;
import gg.wellplayed.backend.dataTransfer.user.UserPutDTO;
import gg.wellplayed.backend.dataTransfer.user.UserResponseDTO;
import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	TagService tagService;

	public User save(User user) {
		return userRepo.save(user);
	}
	
	public Optional<User> findByNick(String nick) {
		return userRepo.findByNick(nick);
	}
	
	public User getOne(Long id) {
		return userRepo.findById(id).orElseThrow();
	}

	public UserResponseDTO getOwnedProfile(Long id, String requesterNick) {
		User user = getOne(id);
		assertOwnership(user, requesterNick);
		return UserResponseDTO.fromEntity(user);
	}

	public UserResponseDTO putOwnedProfile(Long id, UserPutDTO request, String requesterNick) {
		User user = getOne(id);
		assertOwnership(user, requesterNick);

		user.setNick(request.nick());
		user.setEmail(request.email());
		user.setProfileImg(request.profileImg());
		user.setBiographyText(request.biographyText());
		if (request.password() != null) {
			user.setHashedPassword(passwordEncoder.encode(request.password()));
		}
		if (request.linkedAccounts() != null) {
			user.setLinkedAccounts(request.linkedAccounts());
		}
		if (request.likedTags() != null) {
			user.setLikedTags(request.likedTags().stream()
				.map(tagId -> tagService.getOne(tagId))
				.collect(java.util.stream.Collectors.toList()));
		}

		return UserResponseDTO.fromEntity(userRepo.save(user));
	}

	public UserResponseDTO patchOwnedProfile(Long id, UserPatchDTO request, String requesterNick) {
		User user = getOne(id);
		assertOwnership(user, requesterNick);

		if (request.nick() != null) {
			user.setNick(request.nick());
		}
		if (request.email() != null) {
			user.setEmail(request.email());
		}
		if (request.profileImg() != null) {
			user.setProfileImg(request.profileImg());
		}
		if (request.biographyText() != null) {
			user.setBiographyText(request.biographyText());
		}
		if (request.password() != null) {
			user.setHashedPassword(passwordEncoder.encode(request.password()));
		}
		if (request.linkedAccounts() != null) {
			user.setLinkedAccounts(request.linkedAccounts());
		}
		if (request.likedTags() != null) {
			user.setLikedTags(request.likedTags().stream()
				.map(tagId -> tagService.getOne(tagId))
				.collect(java.util.stream.Collectors.toList()));
		}

		return UserResponseDTO.fromEntity(userRepo.save(user));
	}

	private void assertOwnership(User targetUser, String requesterNick) {
		if (!targetUser.getNick().equals(requesterNick)) {
			throw new AccessDeniedException("You can only modify your own user");
		}
	}
}
