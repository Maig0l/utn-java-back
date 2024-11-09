package gg.wellplayed.backend.dataTransfer.auth;

public record UserLoginRequest(
	String nick,
	String password
	) { }
