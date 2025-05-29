package org.example.CustomClasses;

import org.example.IO.EnterClass;
import org.example.IO.ReadClass;
import org.example.EnumClasses.Color;
import org.example.EnumClasses.Country;

import java.io.BufferedReader;
import java.io.Serializable;
import java.time.LocalDate;
/** Class, that saves person characteristics
 */
public class Person implements Serializable {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDate birthday; //Поле может быть null
    private Color eyeColor; //Поле может быть null
    private Country nationality; //Поле не может быть null
    /** Constructor, that creates empty person
     */
    public Person() {}
    /** Constructor, that creates person with needed characteristics
     * @param name - name, that needs to be applied with person
     * @param birthday - date, that needs to be applied with person's birthday
     * @param eyecolor - color, that needs to be applied with person
     * @param nationality - nationality, that needs to be applied with person
     */
    public Person(String name, String birthday, String eyecolor, String nationality) {
        this.name = name;
        this.birthday = GetDate(birthday);
        this.eyeColor = Color.GetColor(eyecolor);
        this.nationality =  Country.GetNationality(nationality);
    }
    /** Method, that creates (or not in case {@param personname} is null) person with data read from file
     * @return - if {@param personname} equals null - null. Else - Person class object, created with read data
     */
    public static Person NewPerson(BufferedReader reader)
    {
        String personname = ReadClass.ReadPersonName(reader);
        if (personname != null) {
            String personbirthday = ReadClass.ReadPersonBirthdayDate(reader);
            String personeyercolor = ReadClass.ReadPersonEyeColor(reader);
            String personnationality = ReadClass.ReadPersonNationality(reader);
            return new Person(personname, personbirthday, personeyercolor, personnationality);
        }
        return null;
    }
    /** Method, that creates (or not in case {@param personname} is null) person with data entered to console
     * @return - if {@param personname} equals null - null. Else - Person class object, created with data entered to console
     */
    public static Person NewPerson()
    {
        String personname = EnterClass.EnterPersonName();
        if (personname != null) {
            String personbirthday = EnterClass.EnterPersonBirthdayDate();
            String personeyercolor = EnterClass.EnterPersonEyeColor();
            String personnationality = EnterClass.EnterPersonNationality();
            return new Person(personname, personbirthday, personeyercolor, personnationality);
        }
        return null;
    }
    /** Method, that returns data corresponding to needed String data
     * @param date - date, that needs to be transformed to java.LocalDate class object
     * @return - if {@param date} equals null - null, else - java.LocalDate class object, created with needed data
     */
    public static LocalDate GetDate(String date) {
        if (date != null) {
            String[] dateParts = date.split("\\.");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            return LocalDate.of(year, month, day);
        }
        return null;
    }
    /**Method, that returns String description of Person class object
     * @return - String class object, that describes all the characteristic's values
     */
    public String toString()
    {
        String answer = "    Person's name: " + this.name + "\n";
        try {
            answer += "    Person's birthday date: " + this.birthday.toString() + "\n";
        }catch(NullPointerException e)
        {
            answer += "    Person's birthday date: unknown\n";
        }
        try
        {
            answer += "    Person's eye color: " + this.eyeColor.toString() + "\n";
        }catch (NullPointerException e)
        {
            answer += "    Person's eye color: no color \n";
        }
        answer += "    Person's nationality: " + this.nationality + "\n";
        return answer;
    }
    /** Method, that returns Person class object's {@param name}
     * @return - this Person class object's {@param name}
     */
    public String GetPersonName()
    {
        return this.name;
    }
    /** Method, that returns Person class object's {@param birthday}
     * @return - this Person class object's {@param birthday}
     */
    public LocalDate GetBirthdayDate()
    {
        return this.birthday;
    }
    /** Method, that returns Person class object's {@param eyeColor}
     * @return - this Person class object's {@param eyeColor}
     */
    public Color GetPersonEyeColor()
    {
        return this.eyeColor;
    }
    /** Method, that returns Person class object's {@param nationality}
     * @return - this Person class object's {@param nationality}
     */
    public Country GetNationality() {
        return nationality;
    }
    /** Method, that sets Person class object's {@param name} to needed value
     * @param name - has needed {@param name} value
     */
    public void SetPersonName(String name)
    {
        this.name = name;
    }
    /** Method, that sets Person class object's {@param birthday} to needed value
     * @param birthday - has needed {@param birthday} value
     */
    public void SetBirthdayDate(LocalDate birthday)
    {
        this.birthday = birthday;
    }
    /** Method, that sets Person class object's {@param eyeColor} to needed value
     * @param eyecolor - has needed {@param eyeColor} value
     */
    public void SetPersonEyeColor(Color eyecolor)
    {
        this.eyeColor = eyecolor;
    }
    /** Method, that sets Person class object's {@param nationality} to needed value
     * @param nationality - needed {@param nationality} value
     */
    public void SetNationality(Country nationality) {
        this.nationality = nationality;
    }
}