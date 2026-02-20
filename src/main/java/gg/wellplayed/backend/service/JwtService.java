package gg.wellplayed.backend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final static String API_SECRET = System.getenv("API_SECRET");
	
	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(), user);
	}

	private String getToken(Map<String, Object> extraClaims, UserDetails user) {
		String token = Jwts
				.builder()
				.claims(extraClaims)
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
				.signWith(getKey(), SIG.HS256)
				.compact();
		
		
		return token;
	}
	
	private SecretKey getKey() {
		byte[] keyBytes = API_SECRET.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getNickFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String nick = getNickFromToken(token);

		return (nick.equals(userDetails.getUsername()) &&
				!isTokenExpired(token));
	}
	
	public Claims getAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private boolean isTokenExpired(String token) {
		Date expirationDate = getClaim(token, Claims::getExpiration);
		return expirationDate.before(new Date());
	}
}
