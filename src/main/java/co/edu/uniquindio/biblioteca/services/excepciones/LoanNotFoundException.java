package co.edu.uniquindio.biblioteca.services.excepciones;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
