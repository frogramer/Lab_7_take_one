package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Class, that clears the collection when corresponding command is called
 */
public class ShowCommand implements DefaultCommand {
    /** Method, that shows all elements from collection
     */
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
    private String ShowCollection()
    {
        if (Dragon.dragons.isEmpty())
        {
            return("Collection is empty");
        }
        else {
            String response = "All the dragons: \n";
            response += Dragon.dragons.entrySet().stream().
                    map(x -> x.getValue().toString()).collect(Collectors.toCollection(ArrayList::new)).toString();
            response = response.replace("[", "");
            response = response.replace("]", "");
            response = response.replace(", ", "\n");
            if (this.client != null)
            {
                response += "\nYour dragons:\n";
                response += Dragon.dragons.entrySet().stream().filter(x ->
                {
                    if (x.getValue().getOwnerId().equals(this.client.getId()))
                    {
                        return true;
                    }
                    return false;
                }).map(x -> x.getValue().toString()).collect(Collectors.toCollection(ArrayList::new)).toString();
                response = response.replace("[", "");
                response= response.replace("]", "");
                response = response.replace(", ", "\n");
            }
            return(response);
            /*for (Map.Entry<Integer, Dragon> dragon : Dragon.dragons.entrySet()) {
                int id = dragon.getKey();
                System.out.println(Dragon.dragons.get(id).toString());
                System.out.println();
            }*/
        }
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in) {

        System.out.println( ShowCollection());
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        return ShowCollection();
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "show";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new ShowCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        return ShowCollection();
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "show : see elements of the collection \n";
    }
}
