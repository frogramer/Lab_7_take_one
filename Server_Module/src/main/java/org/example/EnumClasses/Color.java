package org.example.EnumClasses;

import java.io.Serializable;

/**Class, that has all the usable colors
 */
public enum Color implements Serializable {
    WHITE,
    ORANGE,
    RED,
    YELLOW,
    BLACK;
    /** Method, that returns Color object, corresponding with entered String
     * @param color - has color name, that needs to be defined
     * @return - Color object
     */
    public static Color GetColor(String color) {
        switch (color) {
            case "red" -> {return Color.RED;}
            case "black" -> {return Color.BLACK;}
            case "yellow" -> {return Color.YELLOW;}
            case "orange" -> {return Color.ORANGE;}
            case "white" -> {return Color.WHITE;}
            default -> {return null;}
        }
    }
}

