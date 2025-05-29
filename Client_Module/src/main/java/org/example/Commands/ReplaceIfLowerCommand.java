package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;
import org.example.Exceptions.NotUniqueIDException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
/** Class, that replaces the dragon by id with new one if new id is lower than previous, when corresponding command is called
 */
public class ReplaceIfLowerCommand implements DefaultCommand{
    /** Method, that executes when command is called in console
     * @throws NumberFormatException - throws in case entered ids values are invalid (has letters in it etc.)
     * @throws InvalidVariableValueException - throws in case entered ids values are invalid (less, than 0 etc.)
     * @throws NotUniqueIDException - throws in case {@param newid} is already in use
     */
    Integer id;
    Integer new_id;
    Dragon new_dragon;
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
    public ReplaceIfLowerCommand() {}
    public ReplaceIfLowerCommand(Integer id, Integer new_id, Dragon new_dragon) {
        this.id = id;
        this.new_id = new_id;
        this.new_dragon = new_dragon;
    }
    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        try {
            String test_id = in.next();
            String test_new_id = in.next();
            Integer id = Integer.valueOf(test_id);
            Integer new_id = Integer.valueOf(test_new_id);
            if (id < 0 | new_id < 0 | test_id.contains(".") | test_id.contains(",") |
                    test_new_id.contains(".") | test_new_id.contains(",")) {
                throw new InvalidVariableValueException();
            } else {
                if (Dragon.dragons.containsKey(new_id)) {
                    throw new NotUniqueIDException();
                }
            }
            Dragon dragon = Dragon.NewDragon();
            dragon.Setid(new_id);
            /*if (new_id < id)
            {
                dragon = Dragon.NewDragon();
            }*/
            ReplaceIfLowerCommand command = new ReplaceIfLowerCommand(id, new_id, dragon);
            return command;
        }catch(NumberFormatException | InvalidVariableValueException e){
            System.out.println("Entered keys are invalid. Please try again. You can see all dragons " +
                    "in collection using \"show\" command");}catch(NotUniqueIDException e)
        {System.out.println("Entered ID is already in use. Please enter another ID");}
        return null;
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        if (this.new_id < this.id)
        {
            if (Dragon.dragons.containsKey(new_id))
            {
                return ("Id you are trying to use is already in use. Please try another one");
            }
            else {
                if (Dragon.dragons.get(this.id).getOwnerId().equals(this.client.getId())) {
                    InsertCommand.InsertDragon(this.new_dragon);
                    RemoveKeyCommand.RemoveDragon(this.id);
                    return ("Previous Dragon has been successfully replaced with new one");
                }
                else
                {
                    return "This dragon doesn't belong to you, so you can't delete it";
                }
            }
        }
        else {
            return("Entered id is bigger, than previous. No changes has been done");
        }
    }

    @Override
    public void Execute(Scanner in)
    {
        int id = 0;
        int newid = 0;
        {try{

            String testid = in.next();
            id = Integer.valueOf(testid);
            String testnewid = in.next();
            newid = Integer.valueOf(testnewid);
            if (!Dragon.dragons.containsKey(id) | newid < 0 | testid.contains(".") | testid.contains(",") |
                    testnewid.contains(".") | testnewid.contains(","))
            {throw new InvalidVariableValueException();}
            else {if (Dragon.dragons.containsKey(newid)) {throw new NotUniqueIDException();}}
            if (newid < id)
            {
                Dragon newdragon = Dragon.NewDragon();
                newdragon.Setid(newid);
                InsertCommand.InsertDragon(newdragon);
                RemoveKeyCommand.RemoveDragon(id);
                System.out.println("Previous Dragon has been successfully replaced with new one");
            }
            else {
                System.out.println("Entered id is greater, than previous. No changes has been done");
            }}catch(NumberFormatException | InvalidVariableValueException e){
            System.out.println("Entered keys are invalid. Please try again. You can see all dragons " +
                    "in collection using \"show\" command");}catch(NotUniqueIDException e)
        {System.out.println("Entered ID is already in use. Please enter another ID");}}
    }
    /** Method, that executes when command is called in another file
     * @throws NumberFormatException - throws in case entered ids values are invalid (has letters in it etc.)
     * @throws InvalidVariableValueException - throws in case entered ids values are invalid (less, than 0 etc.)
     * @throws NotUniqueIDException - throws in case {@param newid} is already in use
     */
    @Override
    public String Execute(BufferedReader reader)
    {
        int id = 0;
        int newid = 0;
        //{try{
            String testid = ExecuteScriptCommand.current_command[1];
            id = Integer.valueOf(testid);
            String testnewid = ExecuteScriptCommand.current_command[2];
            newid = Integer.valueOf(testnewid);
            if (!(Dragon.dragons.containsKey(id)) | newid < 0 | testid.contains(".") | testid.contains(",") |
                    testnewid.contains(".") | testnewid.contains(","))
            {throw new InvalidVariableValueException();}
            else {if (Dragon.dragons.containsKey(newid)) {throw new NotUniqueIDException();}}
            if (newid < id)
            {
                if (Dragon.dragons.get(this.id).getOwnerId().equals(this.client.getId())) {
                    Dragon newdragon = Dragon.NewDragon(reader);
                    newdragon.Setid(newid);
                    InsertCommand.InsertDragon(newdragon);
                    RemoveKeyCommand.RemoveDragon(id);
                    //System.out.println("Previous Dragon has been successfully replaced with new one");
                }
            }
            else {
                //System.out.println("Entered id is greater, than previous. No changes has been done");
            }
            return "";
        /*}catch(NumberFormatException | InvalidVariableValueException | NotUniqueIDException | ArrayIndexOutOfBoundsException e){
            System.out.println("File's data is invalid. Please redact it and try again");}
        }*/
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "replace_if_lower";
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "replace_if_lower {previous id} {new id} - delete dragon with previous id from collection and" +
                " add dragon with new entered id in case new id is lower than previous \n";
    }
}
