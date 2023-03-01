package co.edu.uniquindio.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response<T> {
    private String message;
    private T response;

    public Response(String mensaje) {
        this.message = mensaje;
    }
}
