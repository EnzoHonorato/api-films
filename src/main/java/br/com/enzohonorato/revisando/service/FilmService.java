package br.com.enzohonorato.revisando.service;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.exception.BadRequestException;
import br.com.enzohonorato.revisando.mapper.FilmMapper;
import br.com.enzohonorato.revisando.repository.FilmRepository;
import br.com.enzohonorato.revisando.requests.film.FilmPatchRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> listAll() {
        return filmRepository.findAll();
    }

    public List<Film> listOrderByReleaseDateAsc() {
        return filmRepository.listOrderByReleaseDateAsc();
    }

    public Page<Film> listPage(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    public List<Film> findByName(String name) {
        return filmRepository.findByName(name);
    }

    public Film findByIdOrThrowBadRequestException(long id) {
        /*return filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Film not found"));*/

        return filmRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Film not found"));
    }

    @Transactional/*(rollbackOn = Exception.class)*/
    public Film save(FilmPostRequestBody filmPostRequestBody) {
        Film film = FilmMapper.INSTANCE.toFilm(filmPostRequestBody);

        return filmRepository.save(film);

        /*if(true) throw  new Exception("deu ruim no metodo meu parceiro");

        return film;*/
    }

    public void delete(long id) {
        filmRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(FilmPutRequestBody filmPutRequestBody) {
        findByIdOrThrowBadRequestException(filmPutRequestBody.getId());

        Film film = FilmMapper.INSTANCE.toFilm(filmPutRequestBody);

        filmRepository.save(film);
    }

    public void partiallyReplace(FilmPatchRequestBody filmPatchRequestBody) {
        Film film = findByIdOrThrowBadRequestException(filmPatchRequestBody.getId());

        film = checkAttributesToBeUpdated(filmPatchRequestBody, film);

        filmRepository.save(film);
    }

    public Film checkAttributesToBeUpdated(FilmPatchRequestBody filmPatchRequestBody, Film film) {
        if (filmPatchRequestBody.getName() != null) {
            film.setName(filmPatchRequestBody.getName());
        }
        if (filmPatchRequestBody.getReleaseDate() != null) {
            film.setReleaseDate(filmPatchRequestBody.getReleaseDate());
        }
        if (filmPatchRequestBody.getMinutes() != null) {
            film.setMinutes(filmPatchRequestBody.getMinutes());
        }

        if (filmPatchRequestBody.getGenre() != null) {
            film.setGenre(filmPatchRequestBody.getGenre());
        }

        return film;
    }
}
