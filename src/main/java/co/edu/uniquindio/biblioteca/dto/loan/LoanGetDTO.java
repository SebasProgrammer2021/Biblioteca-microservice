package co.edu.uniquindio.biblioteca.dto.loan;

import java.time.LocalDateTime;
import java.util.List;

public record LoanGetDTO(long prestamoID, long clienteID, List<String> isbnsLibros, LocalDateTime fechaCreacion,
                         LocalDateTime fechaDevolucion) {
}
