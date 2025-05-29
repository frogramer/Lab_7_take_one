package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.CustomClasses.Person;
import org.example.EnumClasses.Color;
import org.example.EnumClasses.Country;
import org.example.EnumClasses.DragonCharacter;
import org.example.EnumClasses.DragonType;
import org.example.Exceptions.InvalidVariableValueException;
import org.example.IO.EnterClass;
import org.example.IO.ReadClass;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
/** Class, that updates the Dragon's characteristics by id when corresponding command is called
 */
public class UpdateCommand implements DefaultCommand {
    public static int updatecomands = 0;
    Integer id;
    Client client;
    @Override
    public Client getClient()
    {
        return this.client;
    }
    @Override
    public void setClient(Client client)
    {
        this.client = client;
    }
    //Dragon dragon;
    public UpdateCommand() {}
    public UpdateCommand(Integer id/*, Dragon dragon*/) {
        this.id = id;
        //this.dragon = dragon;
    }
    public static void Update(DatagramChannel datagramChannel)
    {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        Dragon dragon = catchSendeddragon(datagramChannel);
        if (dragon != null) {
            Dragon.dragons.put(dragon.GetDragonid(), dragon);
            Execute(dragon.GetDragonid());
            sendChangeddragon(dragon, datagramChannel);
            Dragon.dragons.remove(dragon.GetDragonid());
        }
        /*else {
            sendChangeddragon(dragon, datagramChannel);
        }*/
    }
    public static Dragon catchSendeddragon(DatagramChannel datagramChannel) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (byteBuffer.hasRemaining()) {
            try {
                datagramChannel.receive(byteBuffer);
                byteBuffer.flip();
                ByteArrayInputStream byteIn = new ByteArrayInputStream(byteBuffer.array(), 0, byteBuffer.limit());
                ObjectInputStream objectIn = new ObjectInputStream(byteIn);
                if (objectIn != null && byteBuffer.hasRemaining()) {
                    Dragon dragon = (Dragon) objectIn.readObject();
                    if (dragon.GetDragonid() == -1)
                    {
                        return null;
                    }
                    else {
                        return dragon;
                    }
                }
            }catch (IOException | ClassNotFoundException e) {
                //e.printStackTrace();
            }
        }
        return null;
    }
    public static void sendChangeddragon(Dragon dragon, DatagramChannel datagramChannel) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOut);
            objectOutputStream.writeObject(dragon);
            objectOutputStream.flush();
            byte[] bytes = byteOut.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            datagramChannel.write(buffer);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        try {
            String test_id = in.next();
            Integer id = Integer.parseInt(test_id);
            /*if (!Dragon.dragons.containsKey(id) | test_id.contains(".") | test_id.contains(",")) {
                throw new InvalidVariableValueException();
            }
            Dragon dragon = Execute(id)*/
            return new UpdateCommand(id/*, dragon*/);
        } catch(NumberFormatException | InvalidVariableValueException e)
        {
            System.out.println("Entered ID is invalid. Please, try again");
            return null;
        }
    }
    public String Execute(DatagramChannel datagramChannel) {
        /*if (Dragon.dragons.containsKey(this.id))
        {
            sendDragontoChange(this.id, datagramChannel);
        }
        else {
            sendDragontoChange(-1, datagramChannel);
        }
        RemoveKeyCommand.RemoveDragon(this.id);*/
        return "Dragon has been successfully updated";
        //InsertCommand.InsertDragon(this.dragon);
    }
    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress)
    {
        if (Dragon.dragons.containsKey(this.id))
        {
            if (Dragon.dragons.get(this.id).getOwnerId().equals(this.client.getId())) {
                sendDragontoChange(this.id, datagramChannel, socketAddress);
            }
            else {
                sendDragontoChange(-1, datagramChannel, socketAddress);
                return "This dragon doesn't belong to you, so you can't change it";
            }
        }
        else {
            sendDragontoChange(-1, datagramChannel, socketAddress);
            return "There is no dragon with this id";
        }
        return "";
    }
    public static String updateChangedDragon (Dragon dragon, DatagramChannel datagramChannel)
    {
        //Dragon dragon = catchSendeddragon(datagramChannel);
            if (Dragon.dragons.containsKey(dragon.GetDragonid())) {
                RemoveKeyCommand.RemoveDragon(dragon.GetDragonid());
                InsertCommand.InsertDragon(dragon);
                return "Dragon has been successfully updated";
            } else {
                return "It seems dragon has been removed by another client";
            }
    }
    public void sendDragontoChange(int id, DatagramChannel datagramChannel, SocketAddress socketAddress) {
        try {
            Dragon dragon;
            if (id == -1)
            {
                dragon = null;
            }
            else {
                dragon = Dragon.dragons.get(id);
            }
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOut);
            objectOutputStream.writeObject(dragon);
            objectOutputStream.flush();
            byte[] bytes = byteOut.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            datagramChannel.send(buffer, socketAddress);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public Dragon catchChangeddragon(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        return null;
    }
    public static Dragon Execute(Integer id) throws InvalidVariableValueException {
        try {
            Scanner in = new Scanner(System.in);
            String current_command = "";
            System.out.println("What would you like to do to this dragon: " + Dragon.dragons.get(id).GetName() + "?");
            System.out.println("You can change follow characteristics:\nname" + "\ncoordinates" +
                    "\ncreation date\nage\ncolor\ntype\ncharacter\ndragon_killer");
            System.out.println("Please, enter characteristic you would like to change");
            System.out.println("If you have made all the changes you wanted to, enter \"done\"");
            while (!(current_command.equals("done"))) {
                System.out.println("You are now changing this Dragon characteristics: " + Dragon.dragons.get(id).GetName());
                System.out.println("If you have made all the changes you wanted to, enter \"done\"");
                current_command = in.next();
                if (current_command.equals("done")) {
                    System.out.println("All the changes have been saved");
                    return Dragon.dragons.get(id);
                }
                switch (current_command) {
                    case "help" -> {
                        System.out.println("You can change follow characteristics:\nname" + "\ncoordinates" +
                                "\ncreation date\nage\ncolor\ntype\ncharacter\ndragon_killer");
                        System.out.println("Please, enter characteristic you would like to change");
                        System.out.println("If you have made all the changes you wanted to, enter \"done\"");
                    }
                    case "name" -> {
                        Dragon.dragons.get(id).SetDragonName(EnterClass.EnterDragonName());
                        System.out.println("Dragon's name has been changed successfully");
                    }
                    case "coordinates" -> {
                        Dragon.dragons.get(id).SetDragonCoordinates(EnterClass.EnterDragonCoordinates());
                        System.out.println("Dragon's coordinates has been changed successfully");
                    }
                    case "age" -> {
                        Dragon.dragons.get(id).SetDragonAge(EnterClass.EnterDragonAge());
                        System.out.println("Dragon's age has been changed successfully");
                    }
                    case "color" -> {
                        Dragon.dragons.get(id).SetDragonColor(Color.GetColor(EnterClass.EnterDragonColor()));
                        System.out.println("Dragon's color has been changed successfully");
                    }
                    case "type" -> {
                        Dragon.dragons.get(id).SetDragonType(DragonType.GetType(EnterClass.EnterDragonType()));
                        System.out.println("Dragon's type has been changed successfully");
                    }
                    case "character" -> {
                        Dragon.dragons.get(id).SetDragonCharacter(DragonCharacter.GetCharacter(EnterClass.EnterDragonCharacter()));
                        System.out.println("Dragon's character has been changed successfully");
                    }
                    case "dragon_killer" -> {
                        if (Dragon.dragons.get(id).GetDragonKiller() == null) {
                            System.out.println("This dragon doesn't have killer" +
                                    "\nWould you like to add one? (yes/no)");
                            String answer = in.next();
                            boolean done = false;
                            while (!done) {
                                try {
                                    if (answer.equals("yes")) {
                                        Dragon.dragons.get(id).SetDragonKiller(Person.NewPerson());
                                        System.out.println("Dragon killer's status has been changed successfully");
                                        done = true;
                                    } else {
                                        if (answer.equals("no")) {
                                            done = true;
                                            System.out.println("Dragon killer's status hasn't been changed");
                                            continue;
                                        } else {
                                            throw new InvalidVariableValueException();
                                        }
                                    }
                                } catch (InvalidVariableValueException e) {
                                    System.out.println("Please, enter \"yes\" or \"no\"");
                                    done = false;
                                }
                            }
                        } else {
                            UpdatePerson(Dragon.dragons.get(id).GetDragonKiller());
                        }
                    }
                    default -> System.out.println("Please enter characteristic you want to change correctly");
                }
            }
        } catch (NumberFormatException | InvalidVariableValueException e) {
            System.out.println("Entered key is invalid. Please try again. You can see all dragons " +
                    "in collection using \"show\" command");
        }
        return Dragon.dragons.get(id);
    }
    @Override
    public void Execute(Scanner in) {
        /** Method, that executes when command is called in console
         * @param id - saves previous dragon id value, got from console's scanner
         * @param testid - saves previous dragon id value, got from console's scanner
         * @param current_command - saves the entered command (name od characteristics that needs to be changed)
         * @throws InvalidVariableValueException - throws in case entered ids values are invalid (less, than 0 etc.)
         * @throws NumberFormatException - throws in case entered ids values are invalid (has letters in it etc.)
         */
        try {
            String testid = in.next();
            int id = Integer.valueOf(testid);
            if (!Dragon.dragons.containsKey(id) | testid.contains(".") | testid.contains(",")) {
                throw new InvalidVariableValueException();
            }
            String current_command = "";
            System.out.println("What would you like to do to this dragon: " + Dragon.dragons.get(id).GetName() + "?");
            System.out.println("You can change follow characteristics:\nname" + "\ncoordinates" +
                    "\ncreation date\nage\ncolor\ntype\ncharacter\ndragon_killer");
            System.out.println("Please, enter characteristic you would like to change");
            System.out.println("If you have made all the changes you wanted to, enter \"done\"");
            while (!(current_command.equals("done"))) {
                System.out.println("You are now changing this Dragon characteristics: " + Dragon.dragons.get(id).GetName());
                System.out.println("If you have made all the changes you wanted to, enter \"done\"");
                current_command = in.next();
                if (current_command.equals("done")) {
                    System.out.println("All the changes have been saved");
                    break;
                }
                switch (current_command) {
                    case "help" -> {
                        System.out.println("You can change follow characteristics:\nname" + "\ncoordinates" +
                                "\ncreation date\nage\ncolor\ntype\ncharacter\ndragon_killer");
                        System.out.println("Please, enter characteristic you would like to change");
                        System.out.println("If you have made all the changes you wanted to, enter \"done\"");
                    }
                    case "name" -> {
                        Dragon.dragons.get(id).SetDragonName(EnterClass.EnterDragonName());
                        System.out.println("Dragon's name has been changed successfully");
                    }
                    case "coordinates" -> {
                        Dragon.dragons.get(id).SetDragonCoordinates(EnterClass.EnterDragonCoordinates());
                        System.out.println("Dragon's coordinates has been changed successfully");
                    }
                    case "age" -> {
                        Dragon.dragons.get(id).SetDragonAge(EnterClass.EnterDragonAge());
                        System.out.println("Dragon's age has been changed successfully");
                    }
                    case "color" -> {
                        Dragon.dragons.get(id).SetDragonColor(Color.GetColor(EnterClass.EnterDragonColor()));
                        System.out.println("Dragon's color has been changed successfully");
                    }
                    case "type" -> {
                        Dragon.dragons.get(id).SetDragonType(DragonType.GetType(EnterClass.EnterDragonType()));
                        System.out.println("Dragon's type has been changed successfully");
                    }
                    case "character" -> {
                        Dragon.dragons.get(id).SetDragonCharacter(DragonCharacter.GetCharacter(EnterClass.EnterDragonCharacter()));
                        System.out.println("Dragon's character has been changed successfully");
                    }
                    case "dragon_killer" -> {
                        if (Dragon.dragons.get(id).GetDragonKiller() == null) {
                            System.out.println("This dragon doesn't have killer" +
                                    "\nWould you like to add one? (yes/no)");
                            String answer = in.next();
                            boolean done = false;
                            while (!done) {
                                try {
                                    if (answer.equals("yes")) {
                                        Dragon.dragons.get(id).SetDragonKiller(Person.NewPerson());
                                        System.out.println("Dragon killer's status has been changed successfully");
                                        done = true;
                                    } else {
                                        if (answer.equals("no")) {
                                            done = true;
                                            System.out.println("Dragon killer's status hasn't been changed");
                                            continue;
                                        } else {
                                            throw new InvalidVariableValueException();
                                        }
                                    }
                                } catch (InvalidVariableValueException e) {
                                    System.out.println("Please, enter \"yes\" or \"no\"");
                                    done = false;
                                }
                            }
                        } else {
                            UpdatePerson(Dragon.dragons.get(id).GetDragonKiller());
                        }
                    }
                    default -> System.out.println("Please enter characteristic you want to change correctly");
                }
            }
        } catch (NumberFormatException | InvalidVariableValueException e) {
            System.out.println("Entered key is invalid. Please try again. You can see all dragons " +
                    "in collection using \"show\" command");
        }
    }
    @Override
    public String Execute(BufferedReader reader) {
        /** Method, that executes when command is called in another file
         * @param id - saves previous dragon id value, got from console's scanner
         * @param testid - saves previous dragon id value, got from another file
         * @param current_command - saves the entered command (name od characteristics that needs to be changed)
         * @throws InvalidVariableValueException - throws in case entered ids values are invalid (less, than 0 etc.)
         * @throws NumberFormatException - throws in case entered ids values are invalid (has letters in it etc.)
         * @throws IOException - throws in case file is not found, unavailable etc.
         */
        try {
            String testid = ExecuteScriptCommand.current_command[1];
            int id = Integer.valueOf(testid);
            if (!Dragon.dragons.containsKey(id) | testid.contains(".") | testid.contains(",") |
                    !Dragon.dragons.get(id).getOwnerId().equals(this.client.getId())) {
                throw new InvalidVariableValueException();
            }
            String current_command = "";
            while (!(current_command.equals("done"))) {
                current_command = reader.readLine();
                if (current_command.equals("done")) {
                    //System.out.println("All the changes have been saved");
                    break;
                }
                switch (current_command) {
                    case "help" -> {
                        /*System.out.println("You can change follow characteristics:\nname" + "\ncoordinates" +
                                "\ncreation date\nage\ncolor\ntype\ncharacter\ndragon_killer");*/
                    }
                    case "name" -> {
                        Dragon.dragons.get(id).SetDragonName(ReadClass.ReadDragonName(reader));
                        /*System.out.println("Dragon's name has been changed successfully");*/
                    }
                    case "coordinates" -> {
                        Dragon.dragons.get(id).SetDragonCoordinates(ReadClass.ReadDragonCoordinates(reader));
                        /*System.out.println("Dragon's coordinates has been changed successfully");*/
                    }
                    case "age" -> {
                        Dragon.dragons.get(id).SetDragonAge(ReadClass.ReadDragonAge(reader));
                        /*System.out.println("Dragon's age has been changed successfully");*/
                    }
                    case "color" -> {
                        Dragon.dragons.get(id).SetDragonColor(Color.GetColor(ReadClass.ReadDragonColor(reader)));
                        /*System.out.println("Dragon's color has been changed successfully");*/
                    }
                    case "type" -> {
                        Dragon.dragons.get(id).SetDragonType(DragonType.GetType(ReadClass.ReadDragonType(reader)));
                        /*System.out.println("Dragon's type has been changed successfully");*/
                    }
                    case "character" -> {
                        Dragon.dragons.get(id).SetDragonCharacter(DragonCharacter.GetCharacter(ReadClass.ReadDragonCharacter(reader)));
                        /*System.out.println("Dragon's character has been changed successfully");*/
                    }
                    case "dragon_killer" -> {
                        if (Dragon.dragons.get(id).GetDragonKiller() == null) {
                            String answer = reader.readLine();
                            boolean done = false;
                            while (!done) {
                                //try {
                                    if (answer.equals("yes")) {
                                        Dragon.dragons.get(id).SetDragonKiller(Person.NewPerson(reader));
                                        /*System.out.println("Dragon killer's status has been changed successfully");*/
                                        done = true;
                                    } else {
                                        if (answer.equals("no")) {
                                            done = true;
                                            /*System.out.println("Dragon killer's status hasn't been changed");*/
                                            continue;
                                        } else {
                                            throw new InvalidVariableValueException();
                                        }
                                    }
                                /*} catch (InvalidVariableValueException e) {
                                    done = false;
                                }*/
                            }
                        } else {
                            UpdatePerson(Dragon.dragons.get(id).GetDragonKiller(), reader);
                        }
                    }
                    default -> throw new InvalidVariableValueException();
                }
            }
        /*} /*catch (NumberFormatException | InvalidVariableValueException e) {
            System.out.println("File's data is invalid. Please redact it and try again");*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void UpdatePerson(Person person) {
        /** Method, that executes when command is called in console
         * @param current_command - saves the entered command (name of characteristics that needs to be changed)
         */
        Scanner in = new Scanner(System.in);
        System.out.println("What would you like to do to this Person: " + person.GetPersonName() + "?");
        String current_command = "";
        System.out.println("You can change follow characteristics:\nname" +
                "\nbirthday_date\neye_color\nnationality");
        System.out.println("Please, enter characteristic you would like to change");
        System.out.println("If you have made all the changes you wanted to, enter \"done\"");
        while (!(current_command.equals("done"))) {
            System.out.println("You are now changing this person characteristics: " + person.GetPersonName());
            current_command = in.next();
            if (current_command.equals("done")) {
                System.out.println("All the changes have been saved");
                break;
            }
            switch (current_command) {
                case "help" -> {
                    System.out.println("You can change follow characteristics:\nname" +
                            "\nbirthday date\neye color\nnationality");
                    System.out.println("Please, enter characteristic you would like to change");
                    System.out.println("If you have made all the changes you wanted to, enter \"done\"");
                }
                case "name" -> {
                    person.SetPersonName(EnterClass.EnterPersonName());
                    System.out.println("Person's name has been changed successfully");
                }
                case "birthday_date" -> {
                    person.SetBirthdayDate(Person.GetDate(EnterClass.EnterPersonBirthdayDate()));
                    System.out.println("Person's birthday date has been changed successfully");
                }
                case "eye_color" -> {
                    person.SetPersonEyeColor(Color.GetColor(EnterClass.EnterPersonEyeColor()));
                    System.out.println("Person's eye color has been changed successfully");
                }
                case "nationality" -> {
                    person.SetNationality(Country.GetNationality(EnterClass.EnterPersonNationality()));
                    System.out.println("Person's nationality has been changed successfully");
                }
                default -> System.out.println("Person's please enter characteristic you want to change correctly");
            }
        }
    }
    public static void UpdatePerson (Person person, BufferedReader reader)
    {
        /** Method, that executes when command is called in another file
         * @param current_command - saves the entered command (name od characteristics that needs to be changed)
         * @throws IOException - throws in case file is not found, unavailable etc.
         */
        String current_command = "";
        try {
            while (!(current_command.equals("done"))) {
                current_command = reader.readLine();
                if (current_command.equals("done")) {
                    System.out.println("All the changes have been saved");
                    break;
                }
                switch (current_command) {
                    case "help" -> {
                        System.out.println("You can change follow characteristics:\nname" +
                                "\nbirthday date\neye color\nnationality");
                        System.out.println("Please, enter characteristic you would like to change");
                        System.out.println("If you have made all the changes you wanted to, enter \"done\"");
                    }
                    case "name" -> {
                        person.SetPersonName(ReadClass.ReadPersonName(reader));
                        System.out.println("Person's name has been changed successfully");
                    }
                    case "birthday_date" -> {
                        person.SetBirthdayDate(Person.GetDate(ReadClass.ReadPersonBirthdayDate(reader)));
                        System.out.println("Person's birthday date has been changed successfully");
                    }
                    case "eye_color" -> {
                        person.SetPersonEyeColor(Color.GetColor(ReadClass.ReadPersonEyeColor(reader)));
                        System.out.println("Person's eye color has been changed successfully");
                    }
                    case "nationality" -> {
                        person.SetNationality(Country.GetNationality(ReadClass.ReadPersonNationality(reader)));
                        System.out.println("Person's nationality has been changed successfully");
                    }
                    default -> System.out.println("Person's please enter characteristic you want to change correctly");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString() {
        return "update";
    }



    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp() {
        return "update {id} : update element of collection with entered id \n";
    }
}
