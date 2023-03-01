package co.edu.uniquindio.biblioteca.dto.loan;

import java.time.LocalDateTime;
import java.util.List;

public record LoanBodyDTO(long clienteID, List<String> isbnLibros, LocalDateTime fechaDevolucion) {
}
