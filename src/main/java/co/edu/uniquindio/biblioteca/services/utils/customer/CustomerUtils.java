package co.edu.uniquindio.biblioteca.services.utils.customer;

import co.edu.uniquindio.biblioteca.dto.customer.CustomerBodyDTO;
import co.edu.uniquindio.biblioteca.dto.customer.CustomerDTO;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.repo.CustomerRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerUtils {
    private final CustomerRepo customerRepo;

    public Cliente getCustomer(long codigoCliente) throws CustomerNotFoundException {
        return customerRepo.findById(codigoCliente).orElseThrow(() -> new CustomerNotFoundException("El cliente no existe"));
    }

    public CustomerDTO transformCustomerToCustomerDTO(Cliente cliente) {
        return new CustomerDTO(cliente.getCodigo(), cliente.getNombre(), cliente.getEmail(), cliente.getTelefono());
    }

    public Cliente receiveCustomer(CustomerBodyDTO cliente) {
        return Cliente.builder().nombre(cliente.nombre()).email(cliente.email()).telefono(cliente.telefono()).password(cliente.password()).build();
    }

    public void validateCustomerExistence(long customerId) {
        Optional<Cliente> customer = customerRepo.findById(customerId);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("El cliente no existe.");
        }
    }

}
