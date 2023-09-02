package br.com.enzohonorato.revisando.util.genre;

import br.com.enzohonorato.revisando.domain.Genre;

public class GenreCreator {
    public static Genre createGenreToBeSaved() {
        return Genre.builder()
                .name("Indefinido")
                .build();
    }

    public static Genre createValidGenre() {
        return Genre.builder()
                .id(1L)
                .name("Indefinido")
                .build();
    }
}
