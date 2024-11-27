package gg.wellplayed.backend.model;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nick"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nick;
	private String email;
	private String hashedPassword;
	private String profileImg;
	private String biographyText;
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	@JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Playlist> playlists;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return hashedPassword;
	}

	@Override
	public String getUsername() {
		return nick;
	}
}
