package gg.wellplayed.backend.dataTransfer.user;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import gg.wellplayed.backend.model.User;

public record UserResponseDTO(
	Long id,
	String nick,
	String email,
	String profileImg,
	String biographyText,
	String role,
	List<String> linkedAccounts,
	List<TagSummaryDTO> likedTags
) {
	public static UserResponseDTO fromEntity(User user) {
		String roleValue = user.getRole() != null ? user.getRole().name() : null;
		List<String> linkedAccounts = user.getLinkedAccounts() != null
			? user.getLinkedAccounts()
			: Collections.emptyList();
		List<TagSummaryDTO> likedTags = user.getLikedTags() != null
			? user.getLikedTags().stream().map(TagSummaryDTO::fromEntity).collect(Collectors.toList())
			: Collections.emptyList();

		return new UserResponseDTO(
			user.getId(),
			user.getNick(),
			user.getEmail(),
			user.getProfileImg(),
			user.getBiographyText(),
			roleValue,
			linkedAccounts,
			likedTags
		);
	}
}
