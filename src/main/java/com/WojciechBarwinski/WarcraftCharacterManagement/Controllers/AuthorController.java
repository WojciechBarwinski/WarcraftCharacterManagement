package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.WojciechBarwinski.WarcraftCharacterManagement.ControllerURL.AUTHOR;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Controllers.RespEntityPathCreator.getCorrectResponseEntaty;

@RestController()
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @PostMapping
    public ResponseEntity<DTOFlag> createAuthor(@RequestBody AuthorDTO authorDTO){
        return getCorrectResponseEntaty(AUTHOR, authorService.createAuthor(authorDTO));
    }

    @GetMapping
    public Set<AuthorDTO> readAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping(value = "/{id}")
    public AuthorDTO readAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/")
    public AuthorDTO readAuthorByLastName(@RequestParam String name){
        return authorService.getAuthorByLastName(name);
    }

    //TODO UPDATE AUTHOR

    @DeleteMapping(value = "/{id}")
    void deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }

    @DeleteMapping
    void deleteAuthorByFirstAndLastNames(@RequestParam String firstName, @RequestParam String lastName){
        authorService.deleteAuthorByFirstAndLastNames(firstName, lastName);
    }
}
