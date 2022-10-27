package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class RelationKey implements Serializable {

    private Long heroToHim;
    private Long heroWith;
}
