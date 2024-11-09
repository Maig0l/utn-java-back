package gg.wellplayed.backend.dataTransfer.user;

import gg.wellplayed.backend.model.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.*;

public record UserRegistrationRequest(
	String nick,
	String email,
	// Password comes in plaintext to the server
	// (encrypted in the way via TLS, and gets hashed w/ bcrypt here)
	String password
	) {
	
	public User parseToUser() {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		User user = new User();
		user.setNick(nick);
		user.setEmail(email);
		user.setHashedPassword(hashedPassword);
		return user;
	}
}

//@Data
//public class UserRegistrationRequest {
//	final String nick;
//	final String email;
//	// Password comes in plaintext to the server
//	// (encrypted in the way via TLS, and gets hashed w/ bcrypt here)
//	final String clearPassword;
//	
//	public User parseToUser() {
//		String hashedPassword = BCrypt.hashpw(clearPassword, BCrypt.gensalt());
//		
//		User user = new User();
//		user.setNick(nick);
//		user.setEmail(email);
//		user.setHashedPassword(hashedPassword);
//		return user;
//	}
//}
