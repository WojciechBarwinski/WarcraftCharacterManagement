package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Setter
@Getter
public class RelationKey implements Serializable {

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Hero owner;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Hero other;
}
