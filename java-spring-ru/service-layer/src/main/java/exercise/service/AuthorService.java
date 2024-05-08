package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private Validator validator;

    public List<AuthorDTO> getAuthorList() {

        var result = authorRepository.findAll();
        return result.stream().map(authorMapper::map).toList();
    }

    public AuthorDTO getAuthor(Long id) {

        var result = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + "is not found"));

        return authorMapper.map(result);
    }

    public AuthorDTO addNewAuthor(AuthorCreateDTO newAuthor) {
        
        validateAuthorCreateDto(newAuthor);
        
        var author = authorMapper.map(newAuthor);

        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorUpdateDTO updatedAuthor) {

        validateAuthorUpdateDto(updatedAuthor);

        var author = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + "is not found"));

        authorMapper.update(updatedAuthor, author);

        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public void deleteAuthor(Long id) {

        authorRepository.deleteById(id);
    }

    private void validateAuthorCreateDto(AuthorCreateDTO author) {

        Set<ConstraintViolation<AuthorCreateDTO>> violations = validator.validate(author);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<AuthorCreateDTO> violation:violations) {
                sb.append(violation.getMessage());
            }

            throw new ConstraintViolationException("Error occured: " + sb.toString(), violations);
        }
    }

    private void validateAuthorUpdateDto(AuthorUpdateDTO author) {

        Set<ConstraintViolation<AuthorUpdateDTO>> violations = validator.validate(author);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<AuthorUpdateDTO> violation:violations) {
                sb.append(violation.getMessage());
            }

            throw new ConstraintViolationException("Error occured: " + sb.toString(), violations);
        }
    }
    // END
}
