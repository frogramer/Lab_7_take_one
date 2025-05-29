package org.example.EnumClasses;

import java.io.Serializable;

/**Class, that has all the usable countries
 */
public enum Country implements Serializable {
    USA,
    CHINA,
    VATICAN,
    JAPAN;
    /** Method, that returns Country object, corresponding with entered String
     * @param country has Country name, that needs to be defined
     * @return Country object
     */
    public static Country GetNationality(String country) {
        switch (country) {
            case "usa" -> {return Country.USA;}
            case "china" -> {return Country.CHINA;}
            case "japan" -> {return Country.JAPAN;}
            case "vatican" -> {return Country.VATICAN;}
            default -> {return null;}
        }
    }
}