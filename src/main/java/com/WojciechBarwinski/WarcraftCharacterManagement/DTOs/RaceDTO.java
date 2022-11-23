package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RaceDTO implements DTOFlag{

    private String name;
    private String description;

    @Override
    public String getId() {
        return name;
    }
}
