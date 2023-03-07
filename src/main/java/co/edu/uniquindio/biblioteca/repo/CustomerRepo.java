package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.dto.customer.CustomerDTO;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Cliente, Long> {
}
