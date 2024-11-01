package gg.wellplayed.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import gg.wellplayed.backend.model.Game;

// Le decimos a Spring que esto es una Entidad
// Y que además corresponde a una Tabla en la DB

@Entity
@Table
public class Shop {
	// Indicamos que este es el ID/Clave primaria
	// Y que su su valor generado sigue la "identidad" (entero incremental)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String icon;
	private String siteUrl;
	
	// Relación N:M
	@ManyToMany(mappedBy = "shops")
	private List<Game> games;

	/** En cada entidad DEBEMOS definir (al menos 2) constructores
	 * ¿Por qué? -> https://stackoverflow.com/a/73583219
	 */
	
	// Constructor default. Spring lo necesita.
	public Shop() {super();}
	
	// Constructor con los datos, pero sin ID. Spring lo necesita.
	public Shop(String name, String icon, String siteUrl, List<Game> games) {
		this.name = name;
		this.icon = icon;
		this.siteUrl = siteUrl;
		this.games = games;
	}
	
	// Constructor con los datos Y la ID. Útil para hacer pruebas a mano nosotros.
	public Shop(Long id, String name, String icon, String siteUrl, List<Game> games) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.siteUrl = siteUrl;
		this.games = games;
	}
	
	// Constructor que toma una entidad. Sirve para hacer una copia profunda (deep copy) pero no es crucial
	// La uso en el DELETE para crear una copia que puedo devolver al cliente luego de borrarlo de la db
	public Shop(Shop s) {
		this.id = s.getId();
		this.name = s.getName();
		this.icon = s.getIcon();
		this.siteUrl = s.getSiteUrl();
		this.games = s.getGames();
	}
	
	// Con el IDE definimos getters/setters

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIcon() {
		return icon;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public List<Game> getGames() {
		return games;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public void setGames(List<Game> games) {
		this.games = games;
	}
}
