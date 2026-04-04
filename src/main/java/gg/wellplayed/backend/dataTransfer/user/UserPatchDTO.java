package gg.wellplayed.backend.dataTransfer.user;

public record UserPatchDTO(
	String nick,
	String email,
	String password,
	String profileImg,
	String biographyText
) {
}

