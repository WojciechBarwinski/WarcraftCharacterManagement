package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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

    @DeleteMapping(value = "/{id}")
    void deleteBookById(@RequestParam Long id){
        bookService.deleteBookById(id);
    }
}
