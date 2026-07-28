package gg.wellplayed.backend.dataTransfer.user;

import java.util.List;

public record UserPutDTO(
	String nick,
	String email,
	String password,
	String profileImg,
	String biographyText,
	List<String> linkedAccounts,
	List<Long> likedTags
) {
}
