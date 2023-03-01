package co.edu.uniquindio.biblioteca.services.excepciones;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String mensaje){
        super(mensaje);
    }

}
