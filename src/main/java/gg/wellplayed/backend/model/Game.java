package gg.wellplayed.backend.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<Shop> shops;

	public Game() { super(); }

	public Game(String name, String summary, ArrayList<String> externalLinks, String imgCover, String imgBanner,
			List<Shop> shops) {
		super();
		this.name = name;
		this.summary = summary;
		this.externalLinks = externalLinks;
		this.imgCover = imgCover;
		this.imgBanner = imgBanner;
		this.shops = shops;
	}

	// TODO: Setters de las colecciones: Está bien tenerlos?

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSummary() {
		return summary;
	}

	public ArrayList<String> getExternalLinks() {
		return externalLinks;
	}

	public String getImgCover() {
		return imgCover;
	}

	public String getImgBanner() {
		return imgBanner;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setExternalLinks(ArrayList<String> externalLinks) {
		this.externalLinks = externalLinks;
	}

	public void setImgCover(String imgCover) {
		this.imgCover = imgCover;
	}

	public void setImgBanner(String imgBanner) {
		this.imgBanner = imgBanner;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

}
