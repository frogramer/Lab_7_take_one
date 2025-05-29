package org.example.IO;

import org.example.CustomClasses.Coordinates;
import org.example.Exceptions.InvalidVariableValueException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
/** Class, that has all the methods for entering to console characteristics (Dragon's name, Person's birthday date etc.)
 */
public class EnterClass {
    /** Method, that gets the Dragon character description
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to Dragon character
     * @return - description of needed Dragon character
     */
    public static String EnterDragonCharacter()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please, enter Dragon's Character:");
        System.out.println("Dragons can have these characters: \ngood \nevil \nchaotic \nchaotic_evil");
        String dragoncharacter = null;
        boolean done = false;
        while (done == false) {
            try {
                dragoncharacter = in.next();
                if (!(dragoncharacter.equals("good") | dragoncharacter.equals("evil") |
                        dragoncharacter.equals("chaotic_evil") | dragoncharacter.equals("chaotic"))) {
                    throw new InvalidVariableValueException();
                }
                done = true;
            } catch (InvalidVariableValueException e) {
                done = false;
                System.out.println("Please, enter Dragon's Character correctly:");
                System.out.println("Dragons can have these characters: \ngood \nevil \nchaotic \nchaotic_evil");
            }
        }
        return dragoncharacter;
    }
    /** Method, that gets the Dragon type description
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to Dragon type
     * @return - description of needed Dragon type
     */
    public static String EnterDragonType()
    {
        Scanner in = new Scanner(System.in);
        String dragontype = null;
        boolean done = false;
        System.out.println("Please, enter Dragon's type:");
        System.out.println("Dragons can have these types: \nwater \nunderground \nair \nfire");
        System.out.println("Dragons also can have no type. In this case press \"enter\"");
        while (done == false) {
            try {
                dragontype = in.nextLine();
                done = true;
                if (!(dragontype.equals("water") | dragontype.equals("underground") |
                        dragontype.equals("air") | dragontype.equals("fire") | dragontype.isEmpty())){
                    throw new InvalidVariableValueException();
                }
            } catch (InvalidVariableValueException e) {
                done = false;
                System.out.println("Please enter Dragon's type correctly: ");
                System.out.println("Dragons can have these types: \nwater \nunderground \nair \nfire");
                System.out.println("Dragons also can have no type");
            }
        }
        return dragontype;
    }
    /** Method, that gets the Dragon color description
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to Dragon color
     * @return - description of needed Dragon color
     */
    public static String EnterDragonColor()
    {
        Scanner in = new Scanner(System.in);
        String dragoncolor = "";
        boolean done = false;
        System.out.println("Please, enter Dragon's color:");
        System.out.println("Dragons can be these colors: \nred \nblack " +
                "\nyellow \norange \nwhite");
        while (done == false) {
            try {
                dragoncolor = in.next();
                if (!(dragoncolor.equals("red") || dragoncolor.equals("black") ||
                        dragoncolor.equals("yellow") || dragoncolor.equals("orange") || dragoncolor.equals("white"))){
                    throw new InvalidVariableValueException();
                }
                done = true;
            } catch (InvalidVariableValueException e) {
                done = false;
                System.out.println("Please enter Dragon's color correctly: ");
                System.out.println("Dragons can be these colors: \nred \nblack " +
                        "\nyellow \norange \nwhite");
            }
        }
        return dragoncolor;
    }
    /** Method, that gets the Dragon character description
     * @throws InputMismatchException - throws in case entered data is not whole integer
     * @throws NumberFormatException - throws in case entered data is not number
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to Dragon character
     * @return - description of needed Dragon character
     */
    public static long EnterDragonAge()
    {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        long age  = -1L;
        System.out.println("Please, enter Dragon's age:");
        while (done == false) {
            try {
                String testage = in.next();
                age = Long.parseLong(testage);
                if (age < 0 | testage.contains(".") | testage.contains(",")) {
                    throw new InvalidVariableValueException();
                }
                done = true;
            } catch (NumberFormatException | InvalidVariableValueException | InputMismatchException e) {
                done = false;
                System.out.println("Please enter Dragon's age correctly: ");
            }
        }
        return age;
    }
    /** Method, that gets the Dragon coordinates description
     * @throws ArrayIndexOutOfBoundsException - throws in case less than needed data was entered
     * @throws NumberFormatException - throws in case entered data is not number
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to Dragon coordinates
     * @return - Corresponding Coordinates class object
     */
    public static Coordinates EnterDragonCoordinates()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter Dragon's coordinates " +
                "(firstly, x-coordinate, secondly, y-coordinate): ");
        System.out.println("Y-coordinate can not be a non-integer number or be more than 233");
        Float xcoordinate = null;
        Integer ycoordinate = null;
        boolean done = false;
        while (done == false) {
            try {
                String[] testcoordinates = in.nextLine().split(" ");
                xcoordinate = Float.valueOf(testcoordinates[0]);
                ycoordinate = Integer.parseInt(testcoordinates[1]);
                if (ycoordinate > 233 | testcoordinates[1].contains(".") | testcoordinates[1].contains(","))
                {
                    throw new InvalidVariableValueException();
                }
                done = true;
            } catch (NumberFormatException | InvalidVariableValueException |
                     ArrayIndexOutOfBoundsException e) {
                done = false;
                System.out.println("Coordinates value is invalid. " +
                        "Please enter Dragon's coordinates correctly: ");
            }
        }
        Coordinates coordinates = new Coordinates(xcoordinate, ycoordinate);
        return coordinates;
    }
    /** Method, that gets the Dragon name description
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to Dragon name
     * @return - entered Dragon name
     */
    public static String EnterDragonName()
    {
        System.out.println("Please enter Dragon's name: ");
        Scanner in = new Scanner(System.in);
        String name = in.next();
        while (name == null || name.isEmpty())
        {
            System.out.println("Dragon's name cannot be empty");
            System.out.println("Please enter Dragon's name correctly: ");
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
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to person nationality
     * @return - description of needed person's nationality
     */
    public static String EnterPersonNationality()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter Dragon killer's country. It can be: \nusa \nchina \nvatican \njapan");
        String personnationality = null;
        boolean done = false;
        while (done == false) {
            try {
                personnationality = in.next();
                if (!(personnationality.equals("usa") | personnationality.equals("china") |
                        personnationality.equals("vatican") | personnationality.equals("japan"))){
                    throw new InvalidVariableValueException();
                }
                done = true;
            } catch (InvalidVariableValueException e) {
                done = false;
                System.out.println("Please enter Dragon killer's country correctly: ");
                System.out.println("Please enter Dragon killer's country. It can be: \nusa \nchina \nvatican \njapan");
            }
        }
        return personnationality;
    }
    /** Method, that gets the Dragon eye color description
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to person's eye color
     * @return - description of needed person eye color
     */
    public static String EnterPersonEyeColor()
    {
        Scanner in = new Scanner(System.in);
        String personeyercolor = null;
        boolean done = false;
        System.out.println("Please, enter Dragon killer's eye color");
        System.out.println("People eyes can be these colors: \nred \nblack " +
                "\nyellow \norange \nwhite");
        System.out.println("Eyes also may have no color. In this case, press \"enter\"");
        while (done == false) {
            try {
                personeyercolor = in.nextLine();
                if (!(personeyercolor.equals("red") | personeyercolor.equals("black") |
                        personeyercolor.equals("yellow") | personeyercolor.equals("orange") |
                        personeyercolor.equals("white") | personeyercolor.isEmpty())) {
                    throw new InvalidVariableValueException();
                }
                done = true;
            } catch (InvalidVariableValueException e) {
                done = false;
                System.out.println("Please enter Dragon killer's eye's color correctly: ");
                System.out.println("People eyes can be these colors: \nred \nblack " +
                        "\nyellow \norange \nwhite");
            }
        }
        return personeyercolor;
    }
    /** Method, that gets the Dragon eye color description
     * @throws InvalidVariableValueException - throws in case entered data can't be applied to person's birthday date
     * @throws DateTimeException - throws in case entered date doesn't exist (31 february etc.)
     * @throws NumberFormatException - throws in case entered data is invalid (day has letters etc.)
     * @throws ArrayIndexOutOfBoundsException - throws in case entered date is invalid (doesn't have year, month or day)
     * @return - description of needed birthday date
     */
    public static String EnterPersonBirthdayDate()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter Dragon killer's birthday date. Write it like in example: 20.02.2020");
        System.out.println("Person's birthday date also may be unknown. In this case, press \"enter\"");
        boolean done = false;
        String personbithdaydate = null;
        while (done == false) {
            try {
                personbithdaydate = in.nextLine();
                if (personbithdaydate.isEmpty()) {
                    personbithdaydate = null;
                    break;
                } else {
                    String[] words = personbithdaydate.split("\\.");
                    int day = Integer.parseInt(words[0]);
                    int month = Integer.parseInt(words[1]);
                    int year = Integer.parseInt(words[2]);
                    if (year < 0 | words.length > 3)
                    {
                        throw new InvalidVariableValueException();
                    }
                    LocalDate.of(year, month, day);
                    done = true;
                }
            } catch (DateTimeException | InvalidVariableValueException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                done = false;
                System.out.println("Please enter Dragon killer's birthday date correctly. " +
                        "Write it like in example: 20.02.2020");
            }
        }
        return personbithdaydate;
    }
    /** Method, that gets person's name from console
     * @return - description of person name
     */
    public static String EnterPersonName()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter Dragon killer's name: ");
        String personname = in.nextLine();
        if (personname.trim().length() == 0)
        {
            return null;
        }
        return personname;
    }
}
