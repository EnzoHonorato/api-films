package br.com.enzohonorato.revisando.repository;

import br.com.enzohonorato.revisando.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByName(String name);

    @Query(value = "SELECT * FROM film ORDER BY release_date ASC", nativeQuery = true)
    List<Film> listOrderByReleaseDateAsc();
}
