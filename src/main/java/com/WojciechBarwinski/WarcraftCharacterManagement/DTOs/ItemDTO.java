package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private String name;
    private String description;
    private Long ownerId;
}
