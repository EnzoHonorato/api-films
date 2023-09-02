package br.com.enzohonorato.revisando.client;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpringClient {
    public static void main(String[] args) {

        ResponseEntity<Film> entity1 = new RestTemplate().getForEntity("http://127.0.0.1:8080/films/2", Film.class);

        Film film1 = new RestTemplate().getForEntity("http://127.0.0.1:8080/films/{id}", Film.class, 2).getBody();
        Film film2 = new RestTemplate().getForObject("http://127.0.0.1:8080/films/2", Film.class);

        Film[] filmsArray = new RestTemplate().getForObject("http://127.0.0.1:8080/films", Film[].class);
        List<Film> filmsList1 = new ArrayList<>(Arrays.asList(filmsArray));


        ResponseEntity<List<Film>> entity2 = new RestTemplate().exchange(
                "http://127.0.0.1:8080/films",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Film>>() {
                }
        );
        List<Film> filmsList2 = entity2.getBody();

        System.out.println(entity1);
        System.out.println(film1);
        System.out.println(film2);

        System.out.println(filmsList1);
        System.out.println(filmsList2);


//        Film film = Film.builder()
//                .name("openheimer")
//                .releaseDate(LocalDate.of(2023, Month.JULY, 7))
//                .minutes(180L)
//                .build();
//
//        Film savedFilm = new RestTemplate().postForObject("http://127.0.0.1:8080/films", film, Film.class);
//
//        System.out.println(savedFilm);

        deleteFilmResttemplate(25L);


    }

    public static void addFilmResttemplate(String name, LocalDate releaseDate, Long minutes) {
        FilmPostRequestBody film = FilmPostRequestBody.builder()
                .name(name)
                .releaseDate(releaseDate)
                .minutes(minutes)
                .build();

        ResponseEntity<Film> entity = new RestTemplate().exchange(
                "http://localhost:8080/films",
                HttpMethod.POST,
                new HttpEntity<>(film, new HttpHeaders()),
                Film.class
        );

        if (entity.getStatusCode().value() == 201) {
            System.out.println("Film salvo com sucesso: " + entity.getBody());
        } else {
            System.out.println("ERRO ao salvar o filme");
        }

    }

    public static void updateFilmResttemplate(Long id, String name, LocalDate releaseDate, Long minutes) {
        FilmPutRequestBody film = FilmPutRequestBody.builder()
                .id(id)
                .name(name)
                .releaseDate(releaseDate)
                .minutes(minutes)
                .build();

        ResponseEntity<Void> entity = new RestTemplate().exchange(
                "http://localhost:8080/films",
                HttpMethod.PUT,
                new HttpEntity<>(film, new HttpHeaders()),
                Void.class
        );

        if (entity.getStatusCode().value() == 204) {
            System.out.println("Film atualizado com sucesso");
        } else {
            System.out.println("ERRO ao atualizar o filme");
        }

    }

    public static void deleteFilmResttemplate(Long id) {

        ResponseEntity<Void> entity = new RestTemplate().exchange(
                "http://localhost:8080/films/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        if (entity.getStatusCode().value() == 204) {
            System.out.println("Film deletado com sucesso");
        } else {
            System.out.println("ERRO ao deletar o filme");
        }

    }
}
