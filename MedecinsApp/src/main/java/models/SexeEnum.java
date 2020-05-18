/*
 *  Copyright (R) Groupe Idyal 2016
 */
package models;

/**
 *
 * @author cdiagne
 */
public enum SexeEnum {

    MASCULIN("MASCULIN"),
    FEMININ("FEMININ");

    private final String name;

    private SexeEnum(String sexe) {
        this.name = sexe;
    }

    public String getName() {
        return name;
    }
}
