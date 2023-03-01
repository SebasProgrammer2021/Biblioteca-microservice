package co.edu.uniquindio.biblioteca.services.utils.author;

import co.edu.uniquindio.biblioteca.dto.author.AuthorDTO;
import co.edu.uniquindio.biblioteca.entity.Autor;
import co.edu.uniquindio.biblioteca.repo.AuthorRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.AuthorNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthorUtils {
    private final AuthorRepo authorRepo;

    public Autor getAuthor(long id) {
        return authorRepo.findById(id).orElseThrow(() -> new AuthorNotFoundException("El Autor no existe"));
    }

    public AuthorDTO convertAuthorToAuthorDTO(Autor author) {
        return new AuthorDTO(author.getId(), author.getNombre());
    }
}
