package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

}
