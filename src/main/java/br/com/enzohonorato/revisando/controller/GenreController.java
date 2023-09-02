package br.com.enzohonorato.revisando.controller;

import br.com.enzohonorato.revisando.domain.Genre;
import br.com.enzohonorato.revisando.requests.genre.GenrePostRequestBody;
import br.com.enzohonorato.revisando.requests.genre.GenrePutRequestBody;
import br.com.enzohonorato.revisando.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> listAll() {
        return ResponseEntity.ok(genreService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Genre> findById(@PathVariable long id) {
        return new ResponseEntity<>(genreService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> save(@RequestBody @Valid GenrePostRequestBody genrePostRequestBody) {
        return new ResponseEntity<>(genreService.save(genrePostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody GenrePutRequestBody genrePutRequestBody) {
        genreService.replace(genrePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
