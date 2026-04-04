package gg.wellplayed.backend.dataTransfer.user;

public record UserPutDTO(
	String nick,
	String email,
	String password,
	String profileImg,
	String biographyText
) {
}

