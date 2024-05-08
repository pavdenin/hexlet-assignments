package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getBookList() {
        var books = bookRepository.findAll();
        return books.stream().map(bookMapper::map).toList();
    }

    public BookDTO getBook(Long id) {
        var book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " is not found"));

        return bookMapper.map(book);
    }

    public BookDTO addNewBook(BookCreateDTO newBook) {
        var book = bookMapper.map(newBook);
        bookRepository.save(book);

        return bookMapper.map(book);
    }

    public BookDTO updateBook(BookUpdateDTO bookUpdateDTO, Long id) {

        var book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " is not found"));

        bookMapper.update(bookUpdateDTO, book);

        bookRepository.save(book);

        return bookMapper.map(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    // END
}
