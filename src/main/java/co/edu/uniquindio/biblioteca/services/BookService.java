package co.edu.uniquindio.biblioteca.services;


import co.edu.uniquindio.biblioteca.dto.book.BookDTO;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.repo.BookRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.BookNotFoundException;
import co.edu.uniquindio.biblioteca.services.utils.book.BookUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @author Sebastián Londoño Valencia
 * @date: 20-02-2023
 */
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final BookUtils bookUtils;


    public void delete(String isbn) {
        bookUtils.getBook(isbn);
        bookRepo.deleteById(isbn);
    }

    public List<Libro> findAll() {
        return bookRepo.findAll();
    }

    public Libro findById(String isbn) {
        return bookRepo.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public Libro save(BookDTO book) {
        Optional<Libro> bookFinded = bookRepo.findById(book.isbn());

        if (bookFinded.isPresent()) {
            throw new RuntimeException("El libro con el isbn " + book.isbn() + " ya existe.");
        }

        return bookRepo.save(bookUtils.validateBook(book));
    }

    public Libro update(String isbn, BookDTO libroNuevo) {
        bookUtils.getBook(isbn);
        Libro libroN = bookUtils.validateBook(libroNuevo);
        libroN.setIsbn(isbn);
        return bookRepo.save(libroN);
    }
}
