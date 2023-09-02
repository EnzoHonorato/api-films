package br.com.enzohonorato.revisando.util.film;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.domain.Genre;
import br.com.enzohonorato.revisando.util.genre.GenreCreator;

import java.time.LocalDate;
import java.time.Month;

public class FilmCreator {
    public static Film createFilmToBeSaved() {
        return Film.builder()
                .name("openheimer")
                .releaseDate(LocalDate.of(2023, Month.JULY, 20))
                .minutes(180L)
                .genre(GenreCreator.createValidGenre())
                .build();
    }

    public static Film createValidFilm() {
        return Film.builder()
                .id(1L)
                .name("openheimer")
                .releaseDate(LocalDate.of(2023, Month.JULY, 20))
                .minutes(180L)
                .genre(GenreCreator.createValidGenre())
                .build();
    }

    public static Film createValidUpdatedFilm() {
        return Film.builder()
                .id(1L)
                .name("barbie")
                .releaseDate(LocalDate.of(2023, Month.JULY, 20))
                .minutes(180L)
                .genre(GenreCreator.createValidGenre())
                .build();
    }
}
