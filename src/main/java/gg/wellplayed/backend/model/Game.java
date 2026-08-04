package gg.wellplayed.backend.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String synopsis;
	//private List<String> externalLinks;
	private String portrait;
	private String banner;
	private String pictures;
	private LocalDate releaseDate;
	
	// Relación N:M (lado propietario)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "game_shop",
		joinColumns = @JoinColumn(name = "game_id"),
		inverseJoinColumns = @JoinColumn(name = "shop_id")
	    )
	private List<Shop> shops;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "game_platform",
		joinColumns = @JoinColumn(name = "game_id"),
		inverseJoinColumns = @JoinColumn(name = "platform_id")
	    )
	private List<Platform> platforms;
	
	//@OneToMany(mappedBy = "game", fetch=FetchType.LAZY)
	@ManyToMany
	@JoinTable(
		name = "game_studio",
		joinColumns = @JoinColumn(name = "game_id"),
		inverseJoinColumns = @JoinColumn(name = "studio_id")
		)
	private List<Studio> studios;
	
	
	// @OneToMany(mappedBy = "game")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "game_tag",
			joinColumns = @JoinColumn(name = "game_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
		    )
	private List<Tag> tags;
	
	//Sin este JsonIgnore, en el front se loopea de game a review y de nuevo a game
	@JsonManagedReference
	@OneToMany(mappedBy = "game", fetch=FetchType.LAZY)
	private List<Review> reviews;

	@ManyToOne
	@JoinColumn(name = "franchise_id")
	@JsonIgnoreProperties("games")
	private Franchise franchise;

	public double getCumulativeRating() {
		if (reviews == null || reviews.isEmpty()) {
			return 0;
		}
		return reviews.stream()
			.mapToDouble(Review::getScore)
			.sum();
	}

	public int getReviewCount() {
		return reviews == null ? 0 : reviews.size();
	}

	public boolean linkShop(Shop shop) {
		return shops.add(shop);
	}
	
	public boolean unlinkShop(Shop shop) {
		return shops.remove(shop);
	}
	
	public boolean linkStudio(Studio studio) {
		return studios.add(studio);
	}
	
	public boolean unlinkStudio(Studio studio) {
		return studios.remove(studio);
	}
	public boolean linkPlatform(Platform platform) {
		return platforms.add(platform);
	}
	
	public boolean unlinkPlatform(Platform platform) {
		return platforms.remove(platform);
	}
}
