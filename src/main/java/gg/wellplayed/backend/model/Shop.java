package gg.wellplayed.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;

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
	private ArrayList<Game> games = new ArrayList<>();

	/** En cada entidad DEBEMOS definir (al menos 2) constructores
	 * ¿Por qué? -> https://stackoverflow.com/a/73583219
	 */
	
	// Constructor default. Spring lo necesita.
	public Shop() {super();}
	
	// Constructor con los datos, pero sin ID. Spring lo necesita.
	public Shop(String name, String icon, String siteUrl) {
		this.name = name;
		this.icon = icon;
		this.siteUrl = siteUrl;
	}
	
	// Constructor con los datos Y la ID. Útil para hacer pruebas a mano nosotros.
	public Shop(Long id, String name, String icon, String siteUrl) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.siteUrl = siteUrl;
	}
	
	// Constructor que toma una entidad. Sirve para hacer una copia profunda (deep copy) pero no es crucial
	// La uso en el DELETE para crear una copia que puedo devolver al cliente luego de borrarlo de la db
	public Shop(Shop s) {
		this.id = s.getId();
		this.name = s.getName();
		this.icon = s.getIcon();
		this.siteUrl = s.getSiteUrl();
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
}
