package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
}
