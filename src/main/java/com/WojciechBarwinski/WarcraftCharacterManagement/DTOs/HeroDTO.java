package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class HeroDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String race;
    private Set<String> fractions = new HashSet<>();
    private Set<String> books = new HashSet<>();
    private Set<String> titles = new HashSet<>();
}
