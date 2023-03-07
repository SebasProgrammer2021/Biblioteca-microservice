package co.edu.uniquindio.biblioteca.services.utils.loan;

import co.edu.uniquindio.biblioteca.dto.loan.LoanBodyDTO;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.CustomerRepo;
import co.edu.uniquindio.biblioteca.repo.BookRepo;
import co.edu.uniquindio.biblioteca.repo.LoanRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LoanUtils {
    private final LoanRepo loanRepo;
    private final CustomerRepo customerRepo;

    private final BookRepo bookRepo;

    /**
     * getLoan
     *
     * @param code
     */
    public void validateLoanExistence(Long code) {
        loanRepo.findById(code).orElseThrow(() -> new BookNotFoundException("El Prestamo no existe."));
    }

    public Prestamo getLoan(Long code) {
        Prestamo loanFinded = loanRepo.findById(code).orElseThrow(() -> new BookNotFoundException("El Prestamo no existe."));
        return loanFinded;
    }

    public List<LoanGetDTO> setListLoanGetDTO(List<Prestamo> loanList) {
        return loanList.stream().map(this::transformLoanToLoanGetDTO).toList();
    }

    public LoanGetDTO transformLoanToLoanGetDTO(Prestamo prestamo) {
        return new LoanGetDTO(prestamo.getCodigo(), prestamo.getCliente().getCodigo(), prestamo.getLibros().stream().map(Libro::getIsbn).toList(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion());
    }

    /**
     * transformLoanToLoanDTO
     *
     * @param loan
     * @return LoanBodyDTO
     */
    public LoanBodyDTO transformLoanToLoanDTO(Prestamo loan) {
        List<String> booksIsbns = new ArrayList<>();

        for (Libro libro : loan.getLibros()) {
            booksIsbns.add(libro.getIsbn());
        }
        return new LoanBodyDTO(loan.getCodigo(), booksIsbns, loan.getFechaDevolucion());
    }

    /**
     * validateLoan
     *
     * @param loanBodyDTO
     * @param loanId
     * @return Prestamo
     */
    public Prestamo validateLoan(LoanBodyDTO loanBodyDTO, long loanId) {
        List<Libro> libros = bookRepo.findAllById(loanBodyDTO.isbnLibros());
        Optional<Cliente> customer = customerRepo.findById(loanBodyDTO.clienteID());

        if (libros.size() != loanBodyDTO.isbnLibros().size()) {
            throw new BookNotFoundException("Hay libros que no existen en la base de datos");
        }

        return Prestamo.builder().codigo(loanId).cliente(customer.get()).fechaPrestamo(LocalDateTime.now()).fechaDevolucion(loanBodyDTO.fechaDevolucion()).libros(libros).build();
    }


}
