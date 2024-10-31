package gg.wellplayed.backend.model;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String summary;
	private ArrayList<String> externalLinks = new ArrayList<>();
	private String imgCover;
	private String imgBanner;

	// Relación N:M (lado propietario)
	@ManyToMany
	private ArrayList<Shop> shops = new ArrayList<>();
}
