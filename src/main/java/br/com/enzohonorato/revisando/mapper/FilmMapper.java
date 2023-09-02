package br.com.enzohonorato.revisando.mapper;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FilmMapper {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    Film toFilm(FilmPostRequestBody filmPostRequestBody);

    Film toFilm(FilmPutRequestBody filmPutRequestBody);

}
