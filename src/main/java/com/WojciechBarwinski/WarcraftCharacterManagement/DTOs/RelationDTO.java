package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationDTO {

    private Long heroId;
    private String heroFirstName;
    private String heroLastName;
    private String description;
}
