package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.book.BookDTO;
import co.edu.uniquindio.biblioteca.dto.Response;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class Book {
    private final BookService bookService;

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Response<String>> deleteBook(@PathVariable String bookId) {
        bookService.delete(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El libro se eliminó correctamente"));
    }

    @GetMapping
    public ResponseEntity<Response<List<Libro>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("", bookService.findAll()));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Response<Libro>> findById(@PathVariable String bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("", bookService.findById(bookId)));
    }

    @PostMapping
    public ResponseEntity<Response<Libro>> saveBook(@RequestBody BookDTO book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Libro creado correctamente", bookService.save(book)));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Response<Libro>> update(@PathVariable String isbn, @RequestBody BookDTO libro) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El libro se actualizo correctamente", bookService.update(isbn, libro)));
    }
}
