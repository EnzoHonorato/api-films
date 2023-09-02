package br.com.enzohonorato.revisando.requests.film;

import br.com.enzohonorato.revisando.domain.Genre;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FilmPatchRequestBody {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private Long minutes;
    private Genre genre;
}
