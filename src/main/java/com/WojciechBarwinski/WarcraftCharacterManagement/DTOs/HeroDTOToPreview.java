package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroDTOToPreview {

    private Long id;
    private String firstName;
    private String lastName;
    private String race;
    private Set<String> fractions = new HashSet<>();
}
