package br.com.enzohonorato.revisando.util.film;

import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;

public class FilmPostRequestBodyCreator {
    public static FilmPostRequestBody createFilmPostRequestBody(){
        return FilmPostRequestBody.builder()
                .name(FilmCreator.createValidFilm().getName())
                .releaseDate(FilmCreator.createValidFilm().getReleaseDate())
                .minutes(FilmCreator.createValidFilm().getMinutes())
                .genre(FilmCreator.createValidFilm().getGenre())
                .build();
    }
}
