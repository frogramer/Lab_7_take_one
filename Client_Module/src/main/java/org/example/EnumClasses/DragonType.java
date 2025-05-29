package org.example.EnumClasses;

import java.io.Serializable;

/**Class, that has all the usable Dragon types
 */
public enum DragonType implements Serializable {
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;
    public static DragonType maxtype = null;
    public static int maxid = 10000;
    /** Method, that returns DragonType object, corresponding with entered String
     * @param  type- has type name, that needs to be defined
     * @return - DragonType object
     */
    public static DragonType GetType(String type) {
        switch (type) {
            case "water" -> {return DragonType.WATER;}
            case "underground" -> {return DragonType.UNDERGROUND;}
            case "air" -> {return DragonType.AIR;}
            case "fire" -> {return DragonType.FIRE;}
            default -> {return null;}
        }
    }
    /** Method, that returns int value dependent on if this object is bigger than one it's compared to
     * @param type - has type, that this type is compared to
     * @return - 1 in case this is bigger than {@param type}, 0 in case equals, -1 in case smaller
     */
    public int CompareTo(DragonType type)
    {
        return this.toString().length() - type.toString().length();
    }
}