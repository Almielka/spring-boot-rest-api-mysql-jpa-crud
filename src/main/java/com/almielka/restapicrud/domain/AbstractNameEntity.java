package com.almielka.restapicrud.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Simple JavaBean domain object adds a name property {@code name} to {@link AbstractIdEntity}.
 * Used as a base abstract class for objects needing these properties.
 *
 * @author Anna S. Almielka
 */

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractNameEntity extends AbstractIdEntity {

    @Column
    @NotEmpty
    @Size(min = 2, max = 32)
    private String name;

    @Override
    public String toString() {
        return name;
    }
}


