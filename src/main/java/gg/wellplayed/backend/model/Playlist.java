package gg.wellplayed.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private Boolean isPrivate;
	
	// Relación N:M
	@JsonIgnore
	@ManyToMany(mappedBy = "playlists", fetch = FetchType.LAZY)
	private List<Game> games;
	
	@ManyToOne
	//@JoinColumn(name = "user_id")
	private User author;
	
	public Playlist(Playlist p) {
		this.id = p.getId();
		this.name = p.getName();
		this.description = p.getDescription();
		this.games = p.getGames();
	}
}