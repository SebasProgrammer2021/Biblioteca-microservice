package co.edu.uniquindio.biblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
@Slf4j
@AllArgsConstructor
public class Libro {

    @Id
    private String isbn;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Gender genero;

    @Column(nullable = false)
    private int unidades;

    @ManyToMany
    private List<Autor> autor;

    @Column(nullable = false)
    private LocalDate fechaPublicacion;
}
