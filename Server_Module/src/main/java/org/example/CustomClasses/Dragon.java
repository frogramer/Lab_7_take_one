package org.example.CustomClasses;

import org.example.EnumClasses.Color;
import org.example.EnumClasses.DragonCharacter;
import org.example.EnumClasses.DragonType;
import org.example.Exceptions.InvalidVariableValueException;
import org.example.IO.EnterClass;
import org.example.IO.ReadClass;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeMap;
/** Class, that saves all the needed Dragon's characteristics
 */
public class Dragon implements Serializable {

    public static TreeMap<Integer, Dragon> dragons = new TreeMap<>(Comparator.naturalOrder());
    //private Client owner;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long age; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле не может быть null
    private Person killer; //Поле может быть null
    private Integer owner_id;
    /** Constructor, that creates empty Dragon
     */
    public Dragon() {}
    /** Constructor, that creates Dragon with needed characteristics
     * @param name - has needed Dragon's name
     * @param coordinates - has needed Dragon's coordinates
     * @param age - has needed Dragon's age
     * @param color - has needed Dragon's color
     * @param type - has needed Dragon's type
     * @param character - has needed Dragon's character
     * @param killer - has needed Dragon's killer
     */
    public Dragon(String name, Coordinates coordinates, long age, String color, String type, String character, Person killer) {

        this.id = Setid();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = SetCreationDate();
        this.age = age;
        this.color = Color.GetColor(color);
        this.type = DragonType.GetType(type);
        this.character = DragonCharacter.GetCharacter(character);
        this.killer = killer;
        //this.owner_id = ;
        /*this.owner = Client.getClient();
        this.owner_id = owner.getId();*/
    }
    /** Method, that creates new dragon with data, read from file
     * @param reader - file reader
     * @return - Dragon class object, created with read data
     */
    public static Dragon NewDragon(BufferedReader reader)
    {
        String name = ReadClass.ReadDragonName(reader);
        Coordinates coordinates = ReadClass.ReadDragonCoordinates(reader);
        long age  = ReadClass.ReadDragonAge(reader);
        String dragoncolor = ReadClass.ReadDragonColor(reader);
        String dragontype = ReadClass.ReadDragonType(reader);
        String dragoncharacter = ReadClass.ReadDragonCharacter(reader);
        Person dragonkiller = Person.NewPerson(reader);
        Dragon newdragon = new Dragon(name, coordinates, age, dragoncolor, dragontype, dragoncharacter, dragonkiller);
        //newdragon.setOwnerId(client.getId());
        return newdragon;
    }
    /** Method, that creates dragon class object with data entered by user
     * @return - Dragon class object, created got from console
     */
    public static Dragon NewDragon()
    {
        String name = EnterClass.EnterDragonName();
        Coordinates coordinates = EnterClass.EnterDragonCoordinates();
        long age  = EnterClass.EnterDragonAge();
        String dragoncolor = EnterClass.EnterDragonColor();
        String dragontype = EnterClass.EnterDragonType();
        String dragoncharacter = EnterClass.EnterDragonCharacter();
        System.out.println("Please, enter Dragon killer's characteristics");
        System.out.println("If you would like Dragon to have no killer, when typing name, press \"enter\"");
        Person dragonkiller = Person.NewPerson();
        Dragon NewDragon = new Dragon(name, coordinates, age, dragoncolor, dragontype, dragoncharacter, dragonkiller);
        return NewDragon;
    }
    public Integer getOwnerId() {
        return this.owner_id;
    }
    public void setOwnerId(Integer owner_id) {
        this.owner_id = owner_id;
    }
    /*public Client getOwner() {
        return this.owner;
    }
    public void setOwner(Client owner) {
        this.owner = owner;
    }*/
    /** Method, that returns description of all Dragon's characteristics
     * @return - description of all the Dragon's characteristics {@param answer}
     */
    public String toString()
    {
        String answer = "ID: " + this.id + "\n";
        answer += "Creation date: " + this.creationDate.toString() + "\n";
        answer += "Name: " + this.name + "\n";
        answer += "Age: " + this.age + "\n";
        answer += "Coordinates: " + this.coordinates.toString() + "\n";
        answer += "Color: " + this.color.toString() + "\n";
        try {
            answer += "Type: " + this.type.toString() + "\n";
        }catch (NullPointerException e){
            answer += "Type: no type\n";
        }
        answer += "Character: " + this.character.toString() + "\n";
        try {
            answer += "Dragon killer characteristics: \n" + this.killer.toString();
        }catch (NullPointerException e){
            answer += "Dragon killer characteristics: no killer\n";
        }
        answer += "Owner id: " + getOwnerId() + "\n";
        return answer;
    }
    /** Method, that sets Dragon's id to needed value
     * @param - needed Dragon'ss id value
     */

    public void Setid(int id)
    {
        if (dragons.containsKey(id))
        {
            throw new InvalidVariableValueException();
        }
        else {
            this.id = id;
        }
    }
    /** Method, that generates id for created Dragon
     * @return - unique id value {@param newid}
     */
    private int Setid() {
        int newid = (int) (Math.random() * 1000);
        while (dragons.containsKey(newid) | newid == 0) {
            newid = (int) (Math.random() * 1000);
        }
        return newid;
    }
    /** Method, that defines date of Dragon class object creation
     * @return - java.LocalDate date type
     */
    private java.time.LocalDate SetCreationDate() {
        return(java.time.LocalDate.now());
    }
    /** Method, that sets Dragon's creation date to needed value
     * @param date - has needed value
     */
    public void setCreationDate(String date)
    {
        this.creationDate = java.time.LocalDate.parse(date);
    }
    /** Method, that sets Dragon's name to needed value
     * @param name - has needed value
     */
    public void SetDragonName(String name)
    {
        this.name = name;
    }
    /** Method, that sets Dragon's coordinates to needed value
     * @param coordinates - has needed value
     */
    public void SetDragonCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }
    /** Method, that sets Dragon's age to needed value
     * @param age - has needed value
     */
    public void SetDragonAge(long age)
    {
        this.age = age;
    }
    /** Method, that sets Dragon's color to needed value
     * @param color - has needed value
     */
    public void SetDragonColor(Color color)
    {
        this.color = color;
    }
    /** Method, that sets Dragon's character to needed value
     * @param character - has needed value
     */
    public void SetDragonCharacter(DragonCharacter character)
    {
        this.character = character;
    }
    /** Method, that sets Dragon's type to needed value
     * @param type - has needed value
     */
    public void SetDragonType(DragonType type)
    {
        this.type = type;
    }
    /** Method, that sets Dragon's killer to needed value
     * @param killer - has needed value
     */
    public void SetDragonKiller(Person killer)
    {
        this.killer = killer;
    }
    /** Method, that returns this Dragon's {@param id}
     * @return - {@param id} of this Dragon
     */
    public int GetDragonid()
    {
        return this.id;
    }
    /** Method, that returns this Dragon's {@param creationDate}
     * @return - {@param creationDate} of this Dragon
     */
    public java.time.LocalDate GetDragonCreationDate()
    {
        return this.creationDate;
    }
    /** Method, that returns this Dragon's {@param name}
     * @return - {@param name} of this Dragon
     */
    public String GetName()
    {
        return this.name;
    }
    /** Method, that returns this Dragon's {@param age}
     * @return - {@param age} of this Dragon
     */
    public long GetDragonAge()
    {
        return this.age;
    }
    /** Method, that returns this Dragon's {@param coordinates}
     * @return - {@param coordinates} of this Dragon
     */
    public Coordinates GetCoordinates()
    {
        return this.coordinates;
    }
    /** Method, that returns this Dragon's {@param color}
     * @return - {@param color} of this Dragon
     */
    public Color GetDragonColor()
    {
        return this.color;
    }
    /** Method, that returns this Dragon {@param character}
     * @return - {@param character} of this Dragon
     */
    public DragonCharacter GetDragonCharacter()
    {
        return this.character;
    }
    /** Method, that returns this Dragon {@param type}
     * @return - {@param type} of this Dragon
     */
    public DragonType GetDragonType()
    {
        return this.type;
    }
    /** Method, that returns this Dragon's {@param killer}
     * @return - {@param killer} of this Dragon
     */
    public Person GetDragonKiller()
    {
        return this.killer;
    }
}