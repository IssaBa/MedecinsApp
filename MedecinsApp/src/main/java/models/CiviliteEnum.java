/*
 *  Copyright (R) Groupe Idyal 2016
 */
package models;

/**
 *
 * @author cdiagne
 */
public enum CiviliteEnum {

    MADAME("MADAME"),
    MADEMOISELLE("MADEMOISELLE"),
    MONSIEUR("MONSIEUR");

    private final String name;

    private CiviliteEnum(String sexe) {
        this.name = sexe;
    }

    public String getName() {
        return name;
    }
}
