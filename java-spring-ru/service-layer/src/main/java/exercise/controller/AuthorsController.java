package exercise.controller;

import exercise.dto.AuthorDTO;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping("")
    public List<AuthorDTO> getAuthorList() {
        return authorService.getAuthorList();
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO addAuthor(@RequestBody AuthorCreateDTO authorCreateDTO) {
        return authorService.addNewAuthor(authorCreateDTO);
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@RequestBody AuthorUpdateDTO authorUpdateDTO, @PathVariable Long id){
        return authorService.updateAuthor(id, authorUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
    // END
}
