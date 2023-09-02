package br.com.enzohonorato.revisando.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The film name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "The film releaseDate cannot be null")
    @Column(nullable = false)
    private LocalDate releaseDate;

    @NotNull(message = "The 'minutes' attribute of the film cannot be null")
    @Max(value = 999, message = "The 'minutes' attribute of the film must be a valid integer number less than or equal to 999")
    @Column(nullable = false)
    private Long minutes;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    @NotNull(message = "The movie must be associated with a genre")
    private Genre genre;


}
