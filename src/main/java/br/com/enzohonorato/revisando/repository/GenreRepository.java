package br.com.enzohonorato.revisando.repository;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "SELECT * FROM genre ORDER BY name ASC", nativeQuery = true)
    List<Genre> listOrderByNameAsc();
}
