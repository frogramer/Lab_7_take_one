package org.example.CustomClasses;

import java.io.Serializable;

/** Class, that saves pair of coordinates
 */
public class Coordinates implements Serializable {
    public static double mincoordinates = -1D;
    public static int minid = 0;
    private Float x; //Поле не может быть null
    private Integer y;//Максимальное значение поля: 233, Поле не может быть null
    /** Constructor, that creates Coordinates class object with entered data
     * @param x - has needed x-coordinate
     * @param y - has needed y-coordinate
     */
    public Coordinates(Float x, Integer y)
    {
        this.x = x;
        this.y = y;
    }
    /** Method, that changes coordinates with entered data
     * @param x - has needed x-coordinate
     * @param y - has needed y-coordinate
     */
    public void SetCoordinates(Float x, Integer y)
    {
        this.x = x;
        this.y = y;
    }
    /** Method, that returns x-coordinate
     * @return - this object x-coordinate
     */
    public Float GetXCoordinate()
    {
        return this.x;
    }
    /** Method, that returns y-coordinate
     * @return - this object y-coordinate
     */
    public Integer GetYCoordinate() {
        return this.y;
    }
    /** Method, that returns string description of Coordinates class object
     * @return - String, that describes coordinates
     */
    public String toString()
    {
        return this.GetXCoordinate() + " " + this.GetYCoordinate();
    }
}