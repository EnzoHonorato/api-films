package br.com.enzohonorato.revisando.util.film;

import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;

public class FilmPutRequestBodyCreator {
    public static FilmPutRequestBody createFilmPutRequestBody(){
        return FilmPutRequestBody.builder()
                .id(FilmCreator.createValidFilm().getId())
                .name(FilmCreator.createValidFilm().getName())
                .releaseDate(FilmCreator.createValidFilm().getReleaseDate())
                .minutes(FilmCreator.createValidFilm().getMinutes())
                .genre(FilmCreator.createValidFilm().getGenre())
                .build();
    }
}
