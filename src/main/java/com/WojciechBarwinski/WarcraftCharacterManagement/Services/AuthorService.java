package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;

import java.util.Set;

public interface AuthorService {

    Set<AuthorDTO> getAllAuthors();

    AuthorDTO getAuthorById(Long id);

    AuthorDTO getAuthorByLastName(String lastName);

    AuthorDTO createAuthor(AuthorDTO dto);

    AuthorDTO updateAuthor(Long id, AuthorDTO dto);

    void deleteAuthorById(Long id);

    void deleteAuthorByFirstAndLastNames(String firstName, String lastName);
}
