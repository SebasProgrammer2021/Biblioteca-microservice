package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.customer.CustomerDTO;
import co.edu.uniquindio.biblioteca.dto.customer.CustomerBodyDTO;
import co.edu.uniquindio.biblioteca.dto.Response;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@AllArgsConstructor
public class Customer {
    private final CustomerService customerService;

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Response<String>> delete(@PathVariable long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Cliente eliminado exitosamente"));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Response<CustomerDTO>> findById(@PathVariable long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Cliente encontrado", customerService.findById(customerId)));
    }

    @GetMapping
    public ResponseEntity<Response<List<CustomerDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Clientes encontrados", customerService.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<CustomerDTO>> save(@RequestBody CustomerBodyDTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Cliente creado exitosamente", customerService.save(customer)));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Response<CustomerDTO>> update(@PathVariable long customerId, @RequestBody CustomerBodyDTO cliente) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Cliente actualizado exitosamente", customerService.update(customerId, cliente)));
    }
}
