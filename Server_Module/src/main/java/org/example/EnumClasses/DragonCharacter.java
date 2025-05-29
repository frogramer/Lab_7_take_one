package org.example.EnumClasses;

import java.io.Serializable;

/**Class, that has all the usable Dragon Characters
 */
public enum DragonCharacter implements Serializable {
    EVIL,
    GOOD,
    CHAOTIC,
    CHAOTIC_EVIL;
    /** Method, that returns DragonCharacter object, corresponding with entered String
     * @param character- has character name, that needs to be defined
     * @return - DragonCharacter object
     */
    public static DragonCharacter GetCharacter(String character) {
        switch (character) {
            case "evil" -> {return DragonCharacter.EVIL;}
            case "good" -> {return DragonCharacter.GOOD;}
            case "chaotic" -> {return DragonCharacter.CHAOTIC;}
            case "chaotic_evil" -> {return DragonCharacter.CHAOTIC_EVIL;}
            default -> {return null;}
        }
    }
}