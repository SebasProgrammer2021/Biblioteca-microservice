package co.edu.uniquindio.biblioteca.dto.book;

import co.edu.uniquindio.biblioteca.entity.Gender;

import java.time.LocalDate;
import java.util.List;

public record BookDTO(String isbn, String nombre, Gender genero, int unidades, LocalDate fechaPublicacion, List<Long> idAutores) {
}
