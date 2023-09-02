package br.com.enzohonorato.revisando.controller;

import br.com.enzohonorato.revisando.domain.Film;
import br.com.enzohonorato.revisando.requests.film.FilmPatchRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPostRequestBody;
import br.com.enzohonorato.revisando.requests.film.FilmPutRequestBody;
import br.com.enzohonorato.revisando.service.FilmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController

@RequestMapping("films")

@Log4j2
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> list() {
        return ResponseEntity.ok(filmService.listAll());
    }

    @GetMapping(path = "/order-by-releaseDate-asc")
    public ResponseEntity<List<Film>> listOrderByReleaseDateAsc() {
        return new ResponseEntity<>(filmService.listOrderByReleaseDateAsc(), HttpStatus.OK);
    }

    @GetMapping(path = "/page")
    public ResponseEntity<Page<Film>> listPage(Pageable pageable) {
        return ResponseEntity.ok(filmService.listPage(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Film> findById(@PathVariable long id) {
        return new ResponseEntity<>(filmService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Film> findById(@PathVariable long id,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);

        return new ResponseEntity<>(filmService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Film>> findByName(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(filmService.findByName(name), HttpStatus.OK);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Film> save(@RequestBody @Valid FilmPostRequestBody filmPostRequestBody) {
        return new ResponseEntity<>(filmService.save(filmPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        filmService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    ResponseEntity<Void> replace(@RequestBody FilmPutRequestBody filmPutRequestBody) {
        filmService.replace(filmPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    ResponseEntity<Void> partiallyReplace(@RequestBody FilmPatchRequestBody filmPatchRequestBody) {
        filmService.partiallyReplace(filmPatchRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<Void> getOptions() {
        HttpHeaders headers = new HttpHeaders();

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.PUT);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.OPTIONS);

        headers.setAllow(allowedMethods);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }


}
