package co.edu.uniquindio.biblioteca.services.excepciones;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
