package org.example.IO;

import org.example.CustomClasses.Coordinates;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
/** Class, that has all the methods for reading from file characteristics (Dragon's name, Person's birthday date etc.)
 */
public class ReadClass {
    /** Method, that gets the Dragon character description
     * @param reader - reads file data
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon character
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @return - description of needed Dragon character
     */
    public static String ReadDragonCharacter(BufferedReader reader)
    {
        String dragoncharacter = null;
        try {
            dragoncharacter = reader.readLine();
            if (!(dragoncharacter.equals("good") | dragoncharacter.equals("evil") |
                    dragoncharacter.equals("chaotic_evil") | dragoncharacter.equals("chaotic"))) {
                throw new InvalidVariableValueException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dragoncharacter;
    }
    /** Method, that gets the Dragon type description
     * @param reader - reads file data
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon character
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @return - description of needed Dragon type
     */
    public static String ReadDragonType(BufferedReader reader)
    {
        String dragontype = null;
        try {
            dragontype = reader.readLine();
            if (!(dragontype.equals("water") | dragontype.equals("underground") |
                    dragontype.equals("air") | dragontype.equals("fire") | dragontype.isEmpty())){
                throw new InvalidVariableValueException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dragontype;
    }
    /** Method, that gets the Dragon color description
     * @param reader - reads file data
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon color
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @return - description of needed Dragon color
     */
    public static String ReadDragonColor(BufferedReader reader)
    {
        String dragoncolor = null;
        try {
            dragoncolor = reader.readLine();
            if (!(dragoncolor.equals("red") || dragoncolor.equals("black") ||
                    dragoncolor.equals("yellow") || dragoncolor.equals("orange") || dragoncolor.equals("white"))){
                throw new InvalidVariableValueException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dragoncolor;
    }
    /** Method, that gets the Dragon character description
     * @param reader - reads file data
     * @throws InputMismatchException - throws in case entered data is not whole integer
     * @throws NumberFormatException - throws in case entered data is not number
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon character
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @return - description of needed Dragon character
     */
    public static long ReadDragonAge(BufferedReader reader)
    {
        boolean done = false;
        long age  = -1L;
        try {
            String testage = reader.readLine();
            age = Long.parseLong(testage);
            if (age < 0 | testage.contains(".") | testage.contains(",")) {
                throw new InvalidVariableValueException();
            }
            done = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return age;
    }
    /** Method, that gets the Dragon coordinates description
     * @param reader - reads file data
     * @throws ArrayIndexOutOfBoundsException - throws in case less than needed data was entered
     * @throws NumberFormatException - throws in case entered data is not number
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon coordinates
     * @return - Corresponding Coordinates class object
     */
    public static Coordinates ReadDragonCoordinates(BufferedReader reader)
    {
        Float xcoordinate = null;
        Integer ycoordinate = null;
        try {
            String[] testcoordinates = reader.readLine().split(" ");
            xcoordinate = Float.valueOf(testcoordinates[0]);
            ycoordinate = Integer.parseInt(testcoordinates[1]);
            if (ycoordinate > 233 | testcoordinates[1].contains(".") | testcoordinates[1].contains(","))
            {
                throw new InvalidVariableValueException();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Coordinates coordinates = new Coordinates(xcoordinate, ycoordinate);
        return coordinates;
    }
    /** Method, that gets the Dragon name description
     * @param reader - reads file data
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon name
     * @return - read Dragon name
     */
    public static String ReadDragonName(BufferedReader reader)
    {
        String name = null;
        try {
            name = reader.readLine();
            if (name.isEmpty() || name.trim().length() == 0)
            {
                throw new InvalidVariableValueException();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return name;
    }
    /*public static int EnterNewDragonID()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter Dragon's ID");
        System.out.println("Dragons ID should be unique integer value that is more than 0");
        String testid = in.next();
        int id = 0;
        boolean done = false;
        while (!done)
        {
            try{id = Integer.parseInt(testid);
            if (testid.contains(".") | testid.contains(",") | id <= 0)
            {
                throw new InvalidVariableValueException();
            }
            if (Dragon.dragons.containsKey(id))
            {
                throw new NotUniqueIDException();
            }
            done = true;
            }catch (InvalidVariableValueException | NumberFormatException e){
                done = false;
                System.out.println("Please enter Dragon's ID correctly");
                System.out.println("Dragons ID should be unique integer value that is more than 0");
            }catch(NotUniqueIDException e)
            {
                done = false;
                System.out.println("Dragon with this ID is already added to collection");
                System.out.println("Dragons ID should be unique integer value that is more than 0");
                System.out.println("Please enter another Dragon's ID");
            }
        }
        return id;
    }*/
    /** Method, that gets the person nationality description
     * @param reader - reads file data
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @throws InvalidVariableValueException - throws in case read data can't be applied to Dragon name
     * @return - description of needed person's nationality
     */
    public static String ReadPersonNationality(BufferedReader reader)
    {
        String personnationality = null;
        try {
            personnationality = reader.readLine();
            if (!(personnationality.equals("usa") | personnationality.equals("china") |
                    personnationality.equals("vatican") | personnationality.equals("japan"))){
                throw new InvalidVariableValueException();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return personnationality;
    }
    /** Method, that gets the Dragon eye color description
     * @param reader - reads file data
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @throws InvalidVariableValueException - throws in case read data can't be applied to person's eye color
     * @return - description of needed person eye color
     */
    public static String ReadPersonEyeColor(BufferedReader reader)
    {
        String personeyercolor = null;
        try {
            personeyercolor = reader.readLine();
            if (!(personeyercolor.equals("red") | personeyercolor.equals("black") |
                    personeyercolor.equals("yellow") | personeyercolor.equals("orange") |
                    personeyercolor.equals("white") | personeyercolor.isEmpty())) {
                throw new InvalidVariableValueException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personeyercolor;
    }
    /** Method, that gets the Dragon eye color description
     * @param reader - reads file data
     * @throws InvalidVariableValueException - throws in case read data can't be applied to person's birthday date
     * @throws DateTimeException - throws in case read date doesn't exist (31 february etc.)
     * @throws NumberFormatException - throws in case read data is invalid (day has letters etc.)
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @throws ArrayIndexOutOfBoundsException - throws in case read date is invalid (doesn't have year, month or day)
     * @return - description of needed birthday date
     */
    public static String ReadPersonBirthdayDate(BufferedReader reader)
    {
        boolean done = false;
        String personbithdaydate = null;
        try {
            personbithdaydate = reader.readLine();
            if (personbithdaydate.isEmpty()) {
                personbithdaydate = null;
            } else {
                String[] words = personbithdaydate.split("\\.");
                int day = Integer.parseInt(words[0]);
                int month = Integer.parseInt(words[1]);
                int year = Integer.parseInt(words[2]);
                if (year < 0)
                {
                    throw new InvalidVariableValueException();
                }
                LocalDate.of(year, month, day);
                done = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personbithdaydate;
    }
    /** Method, that gets person's name from console
     * @param reader - reads file date
     * @throws IOException - throws in case file data is invalid or error with stream occurs
     * @return - description of person name
     */
    public static String ReadPersonName(BufferedReader reader)
    {
        String personname = null;
        try {
            personname = reader.readLine();
            if (personname == null)
            {
                return null;
            }
            else{if (personname.isEmpty()) {
                return null;
            }}
        } catch (IOException e) {
            System.out.println("Person's name is invalid");
            e.printStackTrace();
        }
        return personname;
    }
}
