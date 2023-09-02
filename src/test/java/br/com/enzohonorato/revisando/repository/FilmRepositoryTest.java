package br.com.enzohonorato.revisando.repository;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.domain.Genre;
import br.com.enzohonorato.revisando.util.film.FilmCreator;
import br.com.enzohonorato.revisando.util.genre.GenreCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Film Repository")
class FilmRepositoryTest {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("save persists film when successful")
    void save_PersistFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film filmToBeSaved = FilmCreator.createFilmToBeSaved();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);

        Assertions.assertThat(filmSaved).isNotNull();
        Assertions.assertThat(filmSaved.getId()).isNotNull();

        Assertions.assertThat(filmSaved.getName()).isEqualTo(filmToBeSaved.getName());
        Assertions.assertThat(filmSaved.getReleaseDate()).isEqualTo(filmToBeSaved.getReleaseDate());
        Assertions.assertThat(filmSaved.getMinutes()).isEqualTo(filmToBeSaved.getMinutes());
    }

    @Test
    @DisplayName("save updates film when successful")
    void save_UpdatesFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film filmToBeSaved = FilmCreator.createFilmToBeSaved();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);

        filmSaved.setName("Barbie");
        filmSaved.setReleaseDate(LocalDate.of(2024, Month.JULY, 20));
        filmSaved.setMinutes(200L);

        Film filmUpdated = this.filmRepository.save(filmSaved);

        Assertions.assertThat(filmUpdated).isNotNull();
        Assertions.assertThat(filmUpdated.getId()).isNotNull();

        Assertions.assertThat(filmUpdated.getName()).isEqualTo(filmSaved.getName());
        Assertions.assertThat(filmUpdated.getReleaseDate()).isEqualTo(filmSaved.getReleaseDate());
        Assertions.assertThat(filmUpdated.getMinutes()).isEqualTo(filmSaved.getMinutes());
    }

    @Test
    @DisplayName("delete removes film when successful")
    void delete_RemovesFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film filmToBeSaved = FilmCreator.createFilmToBeSaved();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);

        this.filmRepository.delete(filmSaved);

        Optional<Film> filmOptional = this.filmRepository.findById(filmSaved.getId());

        Assertions.assertThat(filmOptional).isEmpty();
    }

    @Test
    @DisplayName("findByName returns list of film when successful")
    void findByName_ReturnsListOfFilm_WhenSuccessful() {
        genreRepository.save(GenreCreator.createGenreToBeSaved());
        Film filmToBeSaved = FilmCreator.createFilmToBeSaved();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);

        String name = filmSaved.getName();

        List<Film> films = this.filmRepository.findByName(name);

        Assertions.assertThat(films).isNotEmpty().contains(filmSaved);
    }

    @Test
    @DisplayName("findByName returns empty list when no film is found")
    void findByName_ReturnsEmptyList_WhenNoFilmIsFound() {
        List<Film> films = this.filmRepository.findByName("name");

        Assertions.assertThat(films).isEmpty();
    }

    @Test
    @DisplayName("save throws ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Film film = new Film();

//        Assertions.assertThatThrownBy(() -> this.filmRepository.save(film))
//                        .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.filmRepository.save(film))
                .withMessageContaining("The film name cannot be blank");
    }


}