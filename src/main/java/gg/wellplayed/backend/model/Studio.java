package gg.wellplayed.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Studio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Enumerated(EnumType.STRING)
	private StudioType type;
	
	private String site;

	@ManyToMany
	private List<Game> game;
	
	public enum StudioType {
		Publisher,
		Developer
	}
	public Studio(Studio s) {
		this.id = s.getId();
		this.name = s.getName();
		this.type = s.getType();
		this.site = s.getSite();
	}
}
