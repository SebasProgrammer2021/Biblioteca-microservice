package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.loan.LoanBodyDTO;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.CustomerRepo;
import co.edu.uniquindio.biblioteca.repo.BookRepo;
import co.edu.uniquindio.biblioteca.repo.LoanRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.BookNotFoundException;
import co.edu.uniquindio.biblioteca.services.excepciones.LoanNotFoundException;
import co.edu.uniquindio.biblioteca.services.utils.customer.CustomerUtils;
import co.edu.uniquindio.biblioteca.services.utils.loan.LoanUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * LoanService
 *
 * @author Sebastián Londoño Valencia
 * {@code @date:} 20-02-2023
 */
@Service
@AllArgsConstructor
public class LoanService {
    public final CustomerRepo customerRepo;
    private final CustomerUtils customerUtils;
    public final BookRepo bookRepo;
    public final LoanUtils loanUtils;
    public final LoanRepo loanRepo;

    /**
     * delete
     *
     * @param id
     */
    public void delete(long id) {
        loanUtils.getLoan(id);
        loanRepo.deleteById(id);
    }

    public List<LoanGetDTO> findAll() {
        return loanUtils.setListLoanGetDTO(loanRepo.findAll());
    }

    /**
     * findById
     * @param id
     * @return LoanGetDTO
     */
    public LoanGetDTO findById(long id) {
        return loanUtils.transformLoanToLoanGetDTO(loanRepo.findById(id).orElseThrow(() -> new LoanNotFoundException("El Prestamo " + id + " no fue encontrado")));
    }

    /**
     * save
     *
     * @param loanBodyDTO
     * @return LoanGetDTO
     */
    public LoanBodyDTO save(LoanBodyDTO loanBodyDTO) {
        long customerId = loanBodyDTO.clienteID();

        customerUtils.validateCustomerExistence(customerId);

        Prestamo loan = new Prestamo();
        loan.setCliente(customerUtils.getCustomer(customerId));
        loan.setFechaPrestamo(LocalDateTime.now());
        List<String> booksIsbns = loanBodyDTO.isbnLibros();
        List<Libro> libros = new ArrayList<>();

        for (String bookIsn : booksIsbns) {
            Optional<Libro> libro = bookRepo.findById(bookIsn);

            if (libro.isEmpty()) {
                throw new BookNotFoundException("El libro no existe");
            }

            libros.add(libro.get());
        }
        loan.setLibros(libros);
        loan.setFechaDevolucion(loanBodyDTO.fechaDevolucion());

        return loanUtils.transformLoanToLoanDTO(loanRepo.save(loan));
    }

    /**
     * update
     *
     * @param loanId
     * @param loanBodyDTO
     * @return LoanBodyDTO
     */
    public LoanBodyDTO update(long loanId, LoanBodyDTO loanBodyDTO) {
        loanUtils.getLoan(loanId);
        Prestamo prestamo = loanUtils.validateLoan(loanBodyDTO, loanId);
        return loanUtils.transformLoanToLoanDTO(loanRepo.save(prestamo));
    }
}
