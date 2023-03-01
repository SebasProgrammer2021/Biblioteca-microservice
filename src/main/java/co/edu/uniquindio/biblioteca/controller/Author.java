package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.Response;
import co.edu.uniquindio.biblioteca.dto.author.AuthorDTO;
import co.edu.uniquindio.biblioteca.entity.Autor;
import co.edu.uniquindio.biblioteca.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
@AllArgsConstructor
public class Author {
    private final AuthorService authorService;

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Response<String>> deleteAuthor(@PathVariable long authorId) {
        authorService.delete(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El autor se elimino correctamente"));
    }

    @GetMapping
    public ResponseEntity<Response<List<AuthorDTO>>> findAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Autores encontrados", authorService.findAll()));
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Response<Autor>> findAuthorById(@PathVariable Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Autor encontrado", authorService.findById(authorId)));
    }

    @PostMapping
    public ResponseEntity<Response<Autor>> saveAuthor(@RequestBody Autor author) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Autor creado correctamente", authorService.save(author)));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Response<Autor>> updateAuthor(@PathVariable Long authorId, @RequestBody Autor author) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El Autor se actualizo correctamente", authorService.update(authorId, author)));
    }
}
