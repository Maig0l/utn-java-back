package gg.wellplayed.backend.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gg.wellplayed.backend.dataTransfer.game.GameCreateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
	private String name;
	private String summary;
	private List<String> externalLinks;
	private String imgCover;
	private String imgBanner;
	private LocalDate releasedAt;
	// Relación N:M (lado propietario)
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "game_shop",
		joinColumns = @JoinColumn(name = "game_id"),
		inverseJoinColumns = @JoinColumn(name = "shop_id")
	    )
	private List<Shop> shops;
	@JsonIgnore
	@OneToMany(mappedBy = "game", fetch=FetchType.LAZY)
	private List<Review> reviews;

	
	public boolean linkShop(Shop shop) {
		return shops.add(shop);
	}
	
	public boolean unlinkShop(Shop shop) {
		return shops.remove(shop);
	}
}
