package br.com.enzohonorato.revisando.service;

import br.com.enzohonorato.revisando.domain.Genre;
import br.com.enzohonorato.revisando.exception.BadRequestException;
import br.com.enzohonorato.revisando.mapper.GenreMapper;
import br.com.enzohonorato.revisando.repository.GenreRepository;
import br.com.enzohonorato.revisando.requests.genre.GenrePostRequestBody;
import br.com.enzohonorato.revisando.requests.genre.GenrePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre findByIdOrThrowBadRequestException(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(("Genre not found")));
    }

    public List<Genre> listAll() {
        return genreRepository.listOrderByNameAsc();
    }

    public Genre save(GenrePostRequestBody genrePostRequestBody) {
        Genre genreToBeSaved = GenreMapper.INSTANCE.toGenre(genrePostRequestBody);

        return genreRepository.save(genreToBeSaved);
    }

    public void replace(GenrePutRequestBody genrePutRequestBody) {
        this.findByIdOrThrowBadRequestException(genrePutRequestBody.getId());

        Genre genreToBeUpdated = GenreMapper.INSTANCE.toGenre(genrePutRequestBody);

        genreRepository.save(genreToBeUpdated);
    }
}
