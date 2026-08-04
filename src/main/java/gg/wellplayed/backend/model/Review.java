package gg.wellplayed.backend.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// @Builder.Default es necesario: @Builder por si solo ignora los inicializadores de campo,
	// así que sin esto Review.builder().build() (usado al crear reviews) siempre dejaba createdAt en null.
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	@Min(1)
	@Max(5)
	private int score;
	private String title = null;
	private String body = null;

	@JsonBackReference
	@ManyToOne
	//@JoinColumn(name = "game_id")
	private Game game;
	@ManyToOne
	//@JoinColumn(name = "user_id")
	private User author;
}
