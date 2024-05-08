package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping("")
    public List<BookDTO> getBookList() {
        return bookService.getBookList();
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO addNewBook(@Valid @RequestBody BookCreateDTO bookCreateDTO) {
        return bookService.addNewBook(bookCreateDTO);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@Valid @RequestBody BookUpdateDTO bookUpdateDTO, @PathVariable Long id) {
        return bookService.updateBook(bookUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    
    // END
}
