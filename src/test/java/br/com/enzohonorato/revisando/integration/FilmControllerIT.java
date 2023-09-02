package br.com.enzohonorato.revisando.integration;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.repository.FilmRepository;
import br.com.enzohonorato.revisando.repository.GenreRepository;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import br.com.enzohonorato.revisando.util.film.FilmCreator;
import br.com.enzohonorato.revisando.util.film.FilmPostRequestBodyCreator;
import br.com.enzohonorato.revisando.util.film.FilmPutRequestBodyCreator;
import br.com.enzohonorato.revisando.util.genre.GenreCreator;
import br.com.enzohonorato.revisando.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("list returns list of film when successful")
    void list_ReturnsListOfFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film expectedFilm = filmRepository.save(FilmCreator.createValidFilm());

        List<Film> filmList = testRestTemplate.exchange("/films", HttpMethod.GET, null, new ParameterizedTypeReference<List<Film>>() {
        }).getBody();

        Assertions.assertThat(filmList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(expectedFilm);
    }

    @Test
    @DisplayName("listPage returns list of film inside page object when successful")
    void listPage_ReturnsListOfFilmInsidePageObject_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film expectedFilm = filmRepository.save(FilmCreator.createValidFilm());

        PageableResponse<Film> filmPage = testRestTemplate.exchange("/films/page", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Film>>() {
        }).getBody();

        Assertions.assertThat(filmPage).isNotNull();

        Assertions.assertThat(filmPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(filmPage.toList().get(0)).isEqualTo(expectedFilm);
    }

    @Test
    @DisplayName("findById returns film when successful")
    void findById_ReturnsFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());

        long expectedId = savedFilm.getId();

        Film filmFound = testRestTemplate.getForObject("/films/{id}", Film.class, expectedId);

        Assertions.assertThat(filmFound)
                .isNotNull();

        Assertions.assertThat(filmFound.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns list of film when successful")
    void findByName_ReturnsListOfFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());

        String expectedName = savedFilm.getName();

        List<Film> filmList = testRestTemplate.exchange("/films/find?name={name}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Film>>() {
        }, expectedName).getBody();

        Assertions.assertThat(filmList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(filmList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns empty list of film when film is not found")
    void findByName_ReturnsEmptyList_WhenFilmIsNotFound() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());

        List<Film> filmList = testRestTemplate.exchange("/films/find?name={name}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Film>>() {
        }, "name").getBody();

        Assertions.assertThat(filmList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns film when successful")
    void save_ReturnsFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        FilmPostRequestBody filmToBeSaved = FilmPostRequestBodyCreator.createFilmPostRequestBody();

        ResponseEntity<Film> entity = testRestTemplate.postForEntity("/films", filmToBeSaved, Film.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(entity.getBody())
                .isNotNull()
                .isEqualTo(FilmCreator.createValidFilm());
    }

    @Test
    @DisplayName("replace updates film when successful")
    void replace_UpdatesFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());

        FilmPutRequestBody filmPutRequestBody = FilmPutRequestBodyCreator.createFilmPutRequestBody();

        ResponseEntity<Void> entity = testRestTemplate.exchange("/films", HttpMethod.PUT, new HttpEntity<>(filmPutRequestBody), new ParameterizedTypeReference<Void>() {
        });

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

//    @Test
//    @DisplayName("partiallyReplace partially updates film when successful")
//    void partiallyReplace_PartiallyUpdatesFilm_WhenSuccessful() {
//        genreRepository.save(GenreCreator.createGenreToBeSaved());
//        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
//
//        FilmPatchRequestBody filmPatchRequestBody = FilmPatchRequestBodyCreator.createFilmPatchRequestBody();
//
//        ResponseEntity<Void> entity = testRestTemplate.exchange("/films", HttpMethod.PATCH, new HttpEntity<>(filmPatchRequestBody), new ParameterizedTypeReference<Void>() {
//        });
//
//        Assertions.assertThat(entity).isNotNull();
//
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }

    @Test
    @DisplayName("delete removes film when successful")
    void delete_RemovesFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());

        ResponseEntity<Void> entity = testRestTemplate.exchange("/films/{id}", HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        }, savedFilm.getId());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
