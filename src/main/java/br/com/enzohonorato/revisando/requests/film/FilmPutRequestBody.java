package br.com.enzohonorato.revisando.requests.film;

import br.com.enzohonorato.revisando.domain.Genre;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FilmPutRequestBody {
    private Long id;
    @NotBlank(message = "The film name cannot be blank")
    private String name;
    @NotNull(message = "The film releaseDate cannot be null")
    private LocalDate releaseDate;
    @NotNull(message = "The 'minutes' attribute of the film cannot be null")
    @Max(value= 999, message = "The 'minutes' attribute of the film must be a valid integer number less than or equal to 999")
    private Long minutes;
    @NotNull(message = "The movie must be associated with a valid genre")
    private Genre genre;
}
