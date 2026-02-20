package gg.wellplayed.backend.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nick;
	private String email;
	private String password;
	private String profile_img;
	private String bio_text;
	private Boolean is_admin;
	private List<String> linked_accounts;

	@JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	@JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Playlist> playlists;
}
