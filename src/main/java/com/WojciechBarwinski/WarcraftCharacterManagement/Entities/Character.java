package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import com.WojciechBarwinski.WarcraftCharacterManagement.Race;

import javax.persistence.*;
import java.util.List;

@Entity
public class Character {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @OneToOne()
        private Item item;

        @Enumerated(EnumType.STRING)
        private Race race;
        //private List<Character> ally;
        //private List<Character> enemy;
        private Boolean isAlive;

}
