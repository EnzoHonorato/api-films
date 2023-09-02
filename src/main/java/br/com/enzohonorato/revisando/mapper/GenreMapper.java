package br.com.enzohonorato.revisando.mapper;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.domain.Genre;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import br.com.enzohonorato.revisando.requests.genre.GenrePostRequestBody;
import br.com.enzohonorato.revisando.requests.genre.GenrePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    @Mapping(source = "genrePostRequestBody.name", target = "name")
    Genre toGenre(GenrePostRequestBody genrePostRequestBody);

    Genre toGenre(GenrePutRequestBody genrePutRequestBody);
}
