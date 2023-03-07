package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.customer.CustomerDTO;
import co.edu.uniquindio.biblioteca.dto.customer.CustomerBodyDTO;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.CustomerRepo;
import co.edu.uniquindio.biblioteca.repo.LoanRepo;
import co.edu.uniquindio.biblioteca.services.utils.customer.CustomerUtils;
import co.edu.uniquindio.biblioteca.services.utils.loan.LoanUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final LoanRepo loanRepo;
    private final CustomerUtils customerUtils;
    public final LoanUtils loanUtils;


    public void delete(long codigoCliente) {
        customerUtils.getCustomer(codigoCliente);
        customerRepo.deleteById(codigoCliente);
    }

    public CustomerDTO findById(long codigoCliente) {
        Cliente cliente = customerUtils.getCustomer(codigoCliente);
        return customerUtils.transformCustomerToCustomerDTO(cliente);
    }

    public List<CustomerDTO> findAll() {
        return customerRepo.findAll().stream().map(c -> customerUtils.transformCustomerToCustomerDTO(c)).collect(Collectors.toList());
    }

    public CustomerDTO save(CustomerBodyDTO cliente) {
        return customerUtils.transformCustomerToCustomerDTO(customerRepo.save(customerUtils.receiveCustomer(cliente)));
    }

    public CustomerDTO update(long codigoCliente, CustomerBodyDTO clienteNuevo) {
        customerUtils.getCustomer(codigoCliente);
        Cliente nuevo = customerUtils.receiveCustomer(clienteNuevo);
        nuevo.setCodigo(codigoCliente);
        return customerUtils.transformCustomerToCustomerDTO(customerRepo.save(nuevo));
    }
}
