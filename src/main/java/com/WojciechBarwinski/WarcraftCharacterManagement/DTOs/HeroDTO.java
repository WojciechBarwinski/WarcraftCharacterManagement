package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String race;
    private Set<String> fractions = new HashSet<>();
    private Set<String> books = new HashSet<>();
}
