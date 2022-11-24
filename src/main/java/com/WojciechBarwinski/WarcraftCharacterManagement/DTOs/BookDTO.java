package com.WojciechBarwinski.WarcraftCharacterManagement.DTOs;


import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Lob;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements DTOFlag{



    private Long id;
    private String title;
    private String series;
    private String description;
    private String author;
    private Set<String> heroes;





    @Override
    public String getId() {
        return id.toString();
    }
}
