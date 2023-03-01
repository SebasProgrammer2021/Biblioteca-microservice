package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.customer.CustomerDTO;
import co.edu.uniquindio.biblioteca.dto.customer.CustomerBodyDTO;
import co.edu.uniquindio.biblioteca.dto.Response;
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


    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Response<String>> delete(@PathVariable long idCliente) {
        customerService.delete(idCliente);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El cliente se eliminó correctamente"));
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Response<CustomerDTO>> findById(@PathVariable long idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("", customerService.findById(idCliente)));
    }

    @GetMapping
    public ResponseEntity<Response<List<CustomerDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("", customerService.findAll()));

    }

    @PostMapping
    public ResponseEntity<Response<CustomerDTO>> save(@RequestBody CustomerBodyDTO cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Cliente creado correctamente", customerService.save(cliente)));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Response<CustomerDTO>> update(@PathVariable long idCliente, @RequestBody CustomerBodyDTO cliente) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El cliente se actualizó correctamente", customerService.update(idCliente, cliente)));
    }
}
