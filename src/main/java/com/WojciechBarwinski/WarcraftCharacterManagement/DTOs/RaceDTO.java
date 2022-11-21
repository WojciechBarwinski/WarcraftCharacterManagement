package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RaceDTO {

    private String name;
    private String description;
}
