package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "relations")
public class Relation {

    @EmbeddedId
    private RelationKey key;

    @NotNull
    private String description;
}
