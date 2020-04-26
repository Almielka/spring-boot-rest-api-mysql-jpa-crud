package com.almielka.restapicrud.domain;

/**
 * Enum: type of the {@link Company}
 * easily add/delete of the type
 *
 * @author Anna S. Almielka
 */

public enum CompanyType {
    TYPE1,
    TYPE2,
    TYPE3;

    @Override
    public String toString() {
        return name();
    }
}
