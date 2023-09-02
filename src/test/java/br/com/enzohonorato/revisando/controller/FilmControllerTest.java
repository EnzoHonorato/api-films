package br.com.enzohonorato.revisando.controller;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.requests.film.FilmPatchRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import br.com.enzohonorato.revisando.service.FilmService;
import br.com.enzohonorato.revisando.util.film.FilmCreator;
import br.com.enzohonorato.revisando.util.film.FilmPatchRequestBodyCreator;
import br.com.enzohonorato.revisando.util.film.FilmPostRequestBodyCreator;
import br.com.enzohonorato.revisando.util.film.FilmPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@SpringBootTest Carrega nao so o contexto mas tbm o BD
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Film Controller")
class FilmControllerTest {
    @InjectMocks
    private FilmController filmController;

    @Mock
    private FilmService filmServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Film> filmPage = new PageImpl<>(List.of(FilmCreator.createValidFilm()));
        List<Film> filmList = new ArrayList<>(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmServiceMock.listAll())
                .thenReturn(filmList);

        BDDMockito.when(filmServiceMock.listPage(ArgumentMatchers.any()))
                .thenReturn(filmPage);

        BDDMockito.when(filmServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(FilmCreator.createValidFilm());

        BDDMockito.when(filmServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmServiceMock.save(ArgumentMatchers.any(FilmPostRequestBody.class)))
                .thenReturn(FilmCreator.createValidFilm());

        BDDMockito.doNothing().when(filmServiceMock).replace(ArgumentMatchers.any(FilmPutRequestBody.class));

        BDDMockito.doNothing().when(filmServiceMock).partiallyReplace(ArgumentMatchers.any(FilmPatchRequestBody.class));

        BDDMockito.doNothing().when(filmServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("list returns list of film when successful")
    void list_ReturnsListOfFilm_WhenSuccessful() {
        List<Film> filmList = filmController.list().getBody();
        Film validFilm = FilmCreator.createValidFilm();

        Assertions.assertThat(filmList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(validFilm);
    }

    @Test
    @DisplayName("listPage returns list of film inside page object when successful")
    void listPage_ReturnsListOfFilmInsidePageObject_WhenSuccessful() {
        Page<Film> filmPage = filmController.listPage(null).getBody();
        Film validFilm = FilmCreator.createValidFilm();

        Assertions.assertThat(filmPage).isNotNull();

        Assertions.assertThat(filmPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(filmPage.toList().get(0)).isEqualTo(validFilm);
    }

    @Test
    @DisplayName("findById returns film when successful")
    void findById_ReturnsFilm_WhenSuccessful() {
        long expectedId = FilmCreator.createValidFilm().getId();

        Film filmFound = this.filmController.findById(expectedId).getBody();

        Assertions.assertThat(filmFound)
                .isNotNull();

        Assertions.assertThat(filmFound.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns list of film when successful")
    void findByName_ReturnsListOfFilm_WhenSuccessful() {
        String expectedName = FilmCreator.createValidFilm().getName();

        List<Film> films = this.filmController.findByName(expectedName).getBody();

        Assertions.assertThat(films)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns empty list of film when film is not found")
    void findByName_ReturnsEmptyList_WhenFilmIsNotFound() {
        BDDMockito.when(filmServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Film> films = this.filmController.findByName("name").getBody();

        Assertions.assertThat(films)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns film when successful")
    void save_ReturnsFilm_WhenSuccessful() {
        FilmPostRequestBody filmToBeSaved = FilmPostRequestBodyCreator.createFilmPostRequestBody();

        Film savedFilm = this.filmController.save(filmToBeSaved).getBody();

        Assertions.assertThat(savedFilm)
                .isNotNull()
                .isEqualTo(FilmCreator.createValidFilm());
    }

    @Test
    @DisplayName("replace updates film when successful")
    void replace_UpdatesFilm_WhenSuccessful() {
        FilmPutRequestBody filmToBeUpdated = FilmPutRequestBodyCreator.createFilmPutRequestBody();

        ResponseEntity<Void> entity = this.filmController.replace(filmToBeUpdated);

        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("partiallyReplace partially updates Film when successful")
    void partiallyReplace_PartiallyUpdatesFilm_WhenSuccessful() {
        FilmPatchRequestBody filmPatchRequestBody = FilmPatchRequestBodyCreator.createFilmPatchRequestBody();

        ResponseEntity<Void> entity = this.filmController.partiallyReplace(filmPatchRequestBody);

        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes film when successful")
    void delete_RemovesFilm_WhenSuccessful() {
        ResponseEntity<Void> entity = this.filmController.delete(1L);

        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

}