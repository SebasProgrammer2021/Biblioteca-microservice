package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.loan.LoanBodyDTO;
import co.edu.uniquindio.biblioteca.dto.Response;
import co.edu.uniquindio.biblioteca.dto.loan.LoanGetDTO;
import co.edu.uniquindio.biblioteca.services.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@AllArgsConstructor
public class Loan {
    public final LoanService loanService;

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Response<String>> deleteLoan(@PathVariable Long loanId) {
        loanService.delete(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("El Prestamo se eliminó correctamente"));
    }

    @GetMapping
    public ResponseEntity<Response<List<LoanGetDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Prestamos encontrados", loanService.findAll()));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Response<LoanGetDTO>> findById(@PathVariable long loanId) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Prestamo encontrado", loanService.findById(loanId)));
    }

    @PostMapping
    public ResponseEntity<Response<LoanBodyDTO>> save(@RequestBody LoanBodyDTO loan) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Prestamo creado exitosamente", loanService.save(loan)));
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<Response<LoanBodyDTO>> update(@PathVariable long loanId, @RequestBody LoanBodyDTO loan) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>("Prestamos actualizado exitosamente", loanService.update(loanId, loan)));
    }
}
