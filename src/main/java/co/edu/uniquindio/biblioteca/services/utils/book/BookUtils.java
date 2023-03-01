package co.edu.uniquindio.biblioteca.services.utils.book;

import co.edu.uniquindio.biblioteca.dto.book.BookDTO;
import co.edu.uniquindio.biblioteca.entity.Autor;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.repo.AuthorRepo;
import co.edu.uniquindio.biblioteca.repo.BookRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.AuthorNotFoundException;
import co.edu.uniquindio.biblioteca.services.excepciones.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookUtils {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    public Libro getBook(String isbn) {
        return bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("El Libro no existe."));
    }

    public Libro validateBook(BookDTO book) {
        List<Autor> authors = authorRepo.findAllById(book.idAutores());

        if (authors.size() != book.idAutores().size()) {
            String noEncontrados = book.idAutores().stream().filter(id -> !authors.stream().map(Autor::getId).toList().contains(id)).map(Object::toString).collect(Collectors.joining(","));
            throw new AuthorNotFoundException("Los autores " + noEncontrados + " no existen.");
        }

        Libro bookToSave = Libro.builder().isbn(book.isbn()).nombre(book.nombre()).genero(book.genero()).fechaPublicacion(book.fechaPublicacion()).unidades(book.unidades()).autor(authors).build();
        return bookToSave;
    }
}
