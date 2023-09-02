package br.com.enzohonorato.revisando.service;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.exception.BadRequestException;
import br.com.enzohonorato.revisando.repository.FilmRepository;
import br.com.enzohonorato.revisando.requests.film.FilmPatchRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Film Service")
class FilmServiceTest {
    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmRepository filmRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Film> filmPage = new PageImpl<>(List.of(FilmCreator.createValidFilm()));
        List<Film> filmList = new ArrayList<>(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.findAll())
                .thenReturn(filmList);

        BDDMockito.when(filmRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(filmPage);

        BDDMockito.when(filmRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.save(ArgumentMatchers.any(Film.class)))
                .thenReturn(FilmCreator.createValidFilm());

        BDDMockito.doNothing().when(filmRepositoryMock).delete(ArgumentMatchers.any(Film.class));

    }

    @Test
    @DisplayName("listAll returns list of film when successful")
    void listAll_ReturnsListOfFilm_WhenSuccessful() {
        List<Film> filmList = filmService.listAll();

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
        Page<Film> filmPage = filmService.listPage(PageRequest.of(0, 1));

        Film validFilm = FilmCreator.createValidFilm();

        Assertions.assertThat(filmPage).isNotNull();

        Assertions.assertThat(filmPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(filmPage.toList().get(0)).isEqualTo(validFilm);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns film when successful")
    void findByIdOrThrowBadRequestException_ReturnsFilm_WhenSuccessful() {
        long expectedId = FilmCreator.createValidFilm().getId();

        Film filmFound = this.filmService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(filmFound)
                .isNotNull();

        Assertions.assertThat(filmFound.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when film is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenFilmIsNotFound() {
        BDDMockito.when(filmRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new BadRequestException("Film not found"));

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> this.filmService.findByIdOrThrowBadRequestException(1L))
                .withMessageContaining("Film not found");

    }

    @Test
    @DisplayName("findByName returns list of film when successful")
    void findByName_ReturnsListOfFilm_WhenSuccessful() {
        String expectedName = FilmCreator.createValidFilm().getName();

        List<Film> films = this.filmService.findByName(expectedName);

        Assertions.assertThat(films)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns empty list of film when film is not found")
    void findByName_ReturnsEmptyList_WhenFilmIsNotFound() {
        BDDMockito.when(filmRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Film> films = this.filmService.findByName("name");

        Assertions.assertThat(films)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns film when successful")
    void save_ReturnsFilm_WhenSuccessful() {
        FilmPostRequestBody filmToBeSaved = FilmPostRequestBodyCreator.createFilmPostRequestBody();

        Film savedFilm = this.filmService.save(filmToBeSaved);

        Assertions.assertThat(savedFilm)
                .isNotNull()
                .isEqualTo(FilmCreator.createValidFilm());
    }

    @Test
    @DisplayName("replace updates film when successful")
    void replace_UpdatesFilm_WhenSuccessful() {
        FilmPutRequestBody filmToBeUpdated = FilmPutRequestBodyCreator.createFilmPutRequestBody();

        Assertions.assertThatCode(() -> this.filmService.replace(filmToBeUpdated))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("partiallyReplace partially updates film when successful")
    void partiallyReplace_PartiallyUpdatesFilm_WhenSuccessful() {
        FilmPatchRequestBody filmPatchRequestBody = FilmPatchRequestBodyCreator.createFilmPatchRequestBody();

        Assertions.assertThatCode(() -> this.filmService.partiallyReplace(filmPatchRequestBody))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes film when successful")
    void delete_RemovesFilm_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.filmService.delete(1L))
                .doesNotThrowAnyException();
    }

}