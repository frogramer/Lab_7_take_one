package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
/**Class, that removes the Dragon by its id from collection when corresponding command is called
 */
public class RemoveKeyCommand implements DefaultCommand {
    /** Method, that executes when command is called in console
     */
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
    public RemoveKeyCommand() {}
    public RemoveKeyCommand(Integer id)
    {
        this.id = id;
    }
    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        try {
            String test_id = in.next();
            Integer id = Integer.parseInt(test_id);
            if (test_id.contains(".") | test_id.contains(",")) {
                throw new InvalidVariableValueException();
            }
            //RemoveDragon(id);
            //System.out.println("Dragon was successfully removed from the collection");
            return new RemoveKeyCommand(id);
        } catch (NumberFormatException | InvalidVariableValueException e) {
            System.out.println("Entered key is invalid. Please try again. You can see all dragons " +
                    "in collection using \"show\" command");
            return null;
        }
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        if (Dragon.dragons.containsKey(id)) {
            if (Dragon.dragons.get(id).getOwnerId().equals(this.id)) {
                RemoveDragon(this.id);
                return "Dragon has been successfully removed";
            }
            else {
                return "This dragon isn't yours, so you can't delete it";
            }
        }
        else
        {
            return "There is no Dragon with the id " + id + ". Please try again.";
        }
    }

    @Override
    public void Execute(Scanner in) {
        {
            try {
                Integer id = Integer.valueOf(in.next());
                if (!Dragon.dragons.containsKey(id)) {
                    throw new InvalidVariableValueException();
                }
                RemoveDragon(id);
                System.out.println("Dragon was successfully removed from the collection");
            } catch (NumberFormatException | InvalidVariableValueException e) {
                System.out.println("Entered key is invalid. Please try again. You can see all dragons " +
                        "in collection using \"show\" command");
            }
        }
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        {
            //try {
                Integer id = Integer.valueOf(ExecuteScriptCommand.current_command[1]);
                if (!Dragon.dragons.containsKey(id)) {
                    throw new InvalidVariableValueException();
                }
                if (Dragon.dragons.get(id).getOwnerId().equals(this.client.getId())) {
                    RemoveDragon(id);
                    return "";
                }
                return "This dragon doesn't belong to you, so you can't delete it";
            /*} catch (NumberFormatException | InvalidVariableValueException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("File's data is invalid. Please redact it and try again.");
            }*/ /*catch (IOException e)
            {
                e.printStackTrace();
            }*/
        }
    }

    /** Method, that removes needed dragon from collection by id
     * @param id - has id of dragon that needs to be removed from collection
     */
    public static void RemoveDragon(int id) {
        Dragon.dragons.remove(id);
    }
    public static void RemoveDragon(Dragon dragon) {
        Dragon.dragons.remove(dragon.GetDragonid());
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString() {
        return "remove_key";
    }



    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp() {
        return "remove_key {id} : remove the dragon with entered id from the collection \n";
    }
}
