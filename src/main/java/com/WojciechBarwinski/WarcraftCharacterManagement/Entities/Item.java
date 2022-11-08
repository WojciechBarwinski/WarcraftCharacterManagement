package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Hero owner;

    @Lob
    private String description;

    public void setOwner(Hero owner) {
        this.owner = owner;
    }
}
