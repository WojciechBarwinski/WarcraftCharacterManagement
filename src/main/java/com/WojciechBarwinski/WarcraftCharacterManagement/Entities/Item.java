package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne()
    private Character owner;
    private String description;
}
