package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AuthorDTO implements DTOFlag{

    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;

    @Override
    public String getId() {
        return id.toString();
    }
}
