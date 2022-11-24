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

    @DeleteMapping(value = "/{id}")
    void deleteBookById(@RequestParam Long id){
        bookService.deleteBookById(id);
    }
}
