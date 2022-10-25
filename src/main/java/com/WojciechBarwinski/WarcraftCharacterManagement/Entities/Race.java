package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "races")
public class Race {

    @Id
    private String name;

    @Lob
    @NotNull
    private String description;
}
