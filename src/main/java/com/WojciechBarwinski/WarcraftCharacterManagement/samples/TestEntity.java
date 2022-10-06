package com.WojciechBarwinski.WarcraftCharacterManagement.samples;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String faction;

    public TestEntity(String name, String surname, String faction) {
        this.name = name;
        this.surname = surname;
        this.faction = faction;
    }
}
