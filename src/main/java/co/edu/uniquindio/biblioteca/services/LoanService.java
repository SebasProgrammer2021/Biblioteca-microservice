package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.loan.LoanBodyDTO;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.entity.Cliente;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Prestamo loan = loanUtils.getLoan(id);
        loan.setEstaActivo(false);
        LoanBodyDTO loanBodyDTO = new LoanBodyDTO(loan.getCliente().getCodigo(), loan.getLibros().stream().map(libro -> libro.getIsbn()).collect(Collectors.toList()), loan.getFechaDevolucion());
        update(id, loanBodyDTO);
    }

    public List<LoanGetDTO> findAll() {
        boolean activeLoans = true;
        return loanUtils.setListLoanGetDTO(loanRepo.findByAllActiveLoans(activeLoans));
    }

    /**
     * findById
     *
     * @param id
     * @return LoanGetDTO
     */
    public LoanGetDTO findById(long id) {
        boolean activeLoans = true;
        return loanUtils.transformLoanToLoanGetDTO(loanRepo.findByEstaActivo(id, activeLoans).orElseThrow(() -> new LoanNotFoundException("El Prestamo " + id + " no fue encontrado")));
    }

    /**
     * Get list of loans by a specific date
     *
     * @param date
     * @return LoansList
     */
    public List<LoanGetDTO> getLoansByDate(LocalDate date) {
        LocalDateTime reformattedDate = date.atStartOfDay();
        return loanUtils.setListLoanGetDTO(loanRepo.findLoansByDate(reformattedDate));
    }

    public Long getLoansAmountByISBN(String isbn) {
        return loanRepo.getLoansAmountByISBN(isbn);
    }

    /**
     * get customer loans
     *
     * @param customerId
     * @return List of loans
     */
    public List<LoanGetDTO> getCustomerLoans(Long customerId) {
        customerUtils.getCustomer(customerId);
        List<Prestamo> prestamos = loanRepo.getCustomerLoans(customerId);

        return loanUtils.setListLoanGetDTO(prestamos);
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
        loan.setEstaActivo(true);
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
        loanUtils.validateLoanExistence(loanId);
        Prestamo prestamo = loanUtils.validateLoan(loanBodyDTO, loanId);
        return loanUtils.transformLoanToLoanDTO(loanRepo.save(prestamo));
    }
}
