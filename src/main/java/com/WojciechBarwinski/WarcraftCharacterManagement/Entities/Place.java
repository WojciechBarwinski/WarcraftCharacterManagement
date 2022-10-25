package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
public class Place {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    @ManyToOne
    private Place upperLocation;

    public Place(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
