package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.ControllerType;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.WojciechBarwinski.WarcraftCharacterManagement.ControllerType.BOOK;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Controllers.RespEntityPathCreator.getCorrectResponseEntity;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<DTOFlag> createBook(@RequestBody BookDTO bookDTO){
        return getCorrectResponseEntity(BOOK, bookService.createBook(bookDTO));
    }

    @GetMapping
    public Set<BookDTO> readAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/series")
    public Set<BookDTO> readAllBooksBySeries(@RequestParam String seriesTitle){
        return bookService.getAllBooksBySeries(seriesTitle);
    }

    @GetMapping(value = "/{id}")
    public BookDTO readBookById(Long id){
        return bookService.getBookById(id);
    }

    @GetMapping("/")
    public BookDTO readBookByTitle(@RequestParam String title){
        return bookService.getBookByTitle(title);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DTOFlag> updateBook(@RequestBody BookDTO bookDTO, @RequestParam Long id){
        return getCorrectResponseEntity(BOOK, bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping(value = "/{id}")
    void deleteBookById(@RequestParam Long id){
        bookService.deleteBookById(id);
    }

    @DeleteMapping("/")
    void deleteBookByTitle(@RequestParam String title){
        bookService.deleteBookByTitle(title);
    }
}
