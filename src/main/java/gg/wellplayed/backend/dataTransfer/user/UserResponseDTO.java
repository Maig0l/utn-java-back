package gg.wellplayed.backend.dataTransfer.user;

import gg.wellplayed.backend.model.User;

public record UserResponseDTO(
	Long id,
	String nick,
	String email,
	String profileImg,
	String biographyText,
	String role
) {
	public static UserResponseDTO fromEntity(User user) {
		String roleValue = user.getRole() != null ? user.getRole().name() : null;
		return new UserResponseDTO(
			user.getId(),
			user.getNick(),
			user.getEmail(),
			user.getProfileImg(),
			user.getBiographyText(),
			roleValue
		);
	}
}

