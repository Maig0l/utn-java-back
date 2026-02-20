package gg.wellplayed.backend.dataTransfer.auth;

import gg.wellplayed.backend.model.User;
import gg.wellplayed.backend.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.crypto.bcrypt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
	String nick;
	String email;
	// Password comes in plaintext to the server
	// (encrypted in the way via TLS (IF the site run on httpS), and gets hashed w/ bcrypt here)
	String password;
	
	public User parseToUser() {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		User user = new User();
		user.setNick(nick);
		user.setEmail(email);
		user.setHashedPassword(hashedPassword);
		user.setRole(UserRole.USER);
		return user;
	}
}
