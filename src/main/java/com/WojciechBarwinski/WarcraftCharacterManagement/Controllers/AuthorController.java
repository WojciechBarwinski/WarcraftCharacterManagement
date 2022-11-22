package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController()
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){
        return getCorrectURILocation(authorService.createAuthor(authorDTO));
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


    @DeleteMapping(value = "/{id}")
    void deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }

    @DeleteMapping
    void deleteAuthorByFirstAndLastNames(@RequestParam String firstName, @RequestParam String lastName){
        authorService.deleteAuthorByFirstAndLastNames(firstName, lastName);
    }

    private ResponseEntity<AuthorDTO> getCorrectURILocation(AuthorDTO authorDTO){
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/authors/{id}")
                .buildAndExpand(authorDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(authorDTO);
    }
}
