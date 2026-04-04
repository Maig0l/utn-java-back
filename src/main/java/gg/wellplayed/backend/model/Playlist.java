package gg.wellplayed.backend.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "game_playlist",
		joinColumns = @JoinColumn(name = "playlist_id"),
		inverseJoinColumns = @JoinColumn(name = "game_id")
	)
	private List<Game> games;
	
	@ManyToOne
	//@JoinColumn(name = "user_id")
	private User author;
}