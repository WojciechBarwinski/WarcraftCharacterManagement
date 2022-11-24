package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;

import java.util.Set;

public interface AuthorService {

    Set<AuthorDTO> getAllAuthors();

    AuthorDTO getAuthorById(Long id);

    AuthorDTO getAuthorByLastName(String lastName);

    AuthorDTO createAuthor(AuthorDTO authorDTO);

    AuthorDTO updateAuthor(AuthorDTO authorDTO, Long id);

    void deleteAuthorById(Long id);

    void deleteAuthorByFirstAndLastNames(String firstName, String lastName);
}
