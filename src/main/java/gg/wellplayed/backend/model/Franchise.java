package gg.wellplayed.backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;

import java.util.List;



@Entity
@Table
@Getter					
@Setter					
@AllArgsConstructor		
@NoArgsConstructor		
public class Franchise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	
	// Hay que ver como se hace el uno a muchos, por ahora dejo la lista de juegos
	@JsonIgnore
	@OneToMany(mappedBy = "franchise", fetch = FetchType.LAZY)
	private List<Game> games;
	
	// Constructor que toma una entidad. Sirve para hacer una copia profunda (deep copy) pero no es crucial
	// La uso en el DELETE para crear una copia que puedo devolver al cliente luego de borrarlo de la db
	public Franchise(Franchise f) {
		this.id = f.getId();
		this.name = f.getName();
		this.games = f.getGames();
		

}
}
