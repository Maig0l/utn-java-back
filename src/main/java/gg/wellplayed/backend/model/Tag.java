package gg.wellplayed.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;

import java.util.List;

// Le decimos a Spring que esto es una Entidad
// Y que además corresponde a una Tabla en la DB

@Entity
@Table
@Getter					// < # Qué es todo esto?
@Setter					// < Lombok es una librería que nos ahorra escribir
@AllArgsConstructor		// < los getters/setters, así como también los
@NoArgsConstructor		// < constructores mediante estas anotaciones (@)
public class Tag {
	// Indicamos que este es el ID/Clave primaria
	// Y que su su valor generado sigue la "identidad" (entero incremental)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	
	// Relación N:M
	@JsonIgnore
	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
	private List<Game> games;
	
	// Constructor que toma una entidad. Sirve para hacer una copia profunda (deep copy) pero no es crucial
	// La uso en el DELETE para crear una copia que puedo devolver al cliente luego de borrarlo de la db
	public Tag(Tag t) {
		this.id = t.getId();
		this.name = t.getName();
		this.description = t.getDescription();
		this.games = t.getGames();
	}
}