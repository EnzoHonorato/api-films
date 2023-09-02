package br.com.enzohonorato.revisando.util.film;

import br.com.enzohonorato.revisando.requests.film.FilmPatchRequestBody;

public class FilmPatchRequestBodyCreator {
    public static FilmPatchRequestBody createFilmPatchRequestBody(){
        return FilmPatchRequestBody.builder()
                .id(FilmCreator.createValidFilm().getId())
                .name("nome alterado")
                .build();
    }
}
