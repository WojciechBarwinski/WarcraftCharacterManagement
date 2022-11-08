package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Setter
@Getter
public class RelationKey implements Serializable {

    @ManyToOne
    private Hero owner;

    @ManyToOne
    private Hero other;
}
