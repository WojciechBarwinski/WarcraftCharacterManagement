package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroDTO implements DTOFlag{

    private Long id;
    private String firstName;
    private String lastName;
    private String race;
    private Set<String> fractions = new HashSet<>();
    private Set<String> books = new HashSet<>();
    private Set<String> titles = new HashSet<>();
    private Set<String> items = new HashSet<>();
    private Set<RelationDTO> relations = new HashSet<>();

    @Override
    public String getId() {
        return id.toString();
    }
}
