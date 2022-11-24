package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;

public class AuthorMapper {

    private AuthorMapper(){};


    public static AuthorDTO mapAuthorToDTO(Author author){
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .nationality(author.getNationality())
                .build();
    }

    public static Author mapDTOToAuthor(AuthorDTO authorDTO){
        return Author.builder()
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .nationality(authorDTO.getNationality())
                .build();
    }
}
