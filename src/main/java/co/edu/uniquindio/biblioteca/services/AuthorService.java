package co.edu.uniquindio.biblioteca.services;

import co.edu.uniquindio.biblioteca.dto.author.AuthorDTO;
import co.edu.uniquindio.biblioteca.entity.Autor;
import co.edu.uniquindio.biblioteca.repo.AuthorRepo;
import co.edu.uniquindio.biblioteca.services.excepciones.AuthorNotFoundException;
import co.edu.uniquindio.biblioteca.services.utils.author.AuthorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {
private final AuthorUtils authorUtils;

    private final AuthorRepo authorRepo;

    public void delete(long id) {
        authorUtils.getAuthor(id);
        authorRepo.deleteById(id);
    }

    public Autor findById(Long id) {
        return authorRepo.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
    }

    public List<AuthorDTO> findAll() {
        return authorRepo.findAll().stream().map(author -> authorUtils.convertAuthorToAuthorDTO(author)).collect(Collectors.toList());
    }

    public Autor save(Autor author) {
        return authorRepo.save(author);
    }

    public Autor update(Long id, Autor author) {
        authorRepo.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
        return authorRepo.save(author);
    }
}
