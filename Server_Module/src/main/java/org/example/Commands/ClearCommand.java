package org.example.Commands;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/** Class, that clears the collection when corresponding command is called
 */
public class ClearCommand implements DefaultCommand {
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
    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new ClearCommand();
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress)
    {
        //new Thread(() -> Dragon.dragons.remove(0)).start();
        /*Stream stream = Dragon.dragons.entrySet().stream();
        stream.forEach(x -> Dragon.dragons.remove(((Dragon) x).GetDragonid()));*/
        if (Dragon.dragons.isEmpty())
        {
            return "Collection is empty";
        }
        else {
            new ArrayList<>(Dragon.dragons.keySet()).stream().filter(x ->
                    {
                        if (Dragon.dragons.get(x).getOwnerId().equals(this.client.getId()))
                        {
                            return true;
                        }
                        return false;
                    })
                    .forEach(key -> Dragon.dragons.remove(key));
            return "Collection has been successfully cleared";
        }
    }
    @Override
    public void Execute(Scanner in) {
        if (Dragon.dragons.isEmpty())
        {
            System.out.println("Collection is already empty");
        }
        else {
            Set<Integer> keysToRemove = Dragon.dragons.entrySet().stream().filter(x ->
                    {
                        if (x.getValue().getOwnerId().equals(this.client.getId()))
                        {
                            return true;
                        }
                        return false;
                    })
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            Dragon.dragons.keySet().removeAll(keysToRemove);
            if (keysToRemove.size() == 0)
            {
                //System.out.println("Keystoremove is empty");
            }
            else {
                //System.out.println("Keystoremove is not empty");
            }
            //Dragon.dragons.clear();
            //System.out.println("Collection has been successfully cleared");
        }
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader){
        if (Dragon.dragons.isEmpty())
        {
            return "Collection is empty";
        }
        else {
            new ArrayList<>(Dragon.dragons.keySet()).stream().filter(x ->
                    {
                        if (Dragon.dragons.get(x).getOwnerId().equals(this.client.getId()))
                        {
                            return true;
                        }
                        return false;
                    })
                    .forEach(key -> Dragon.dragons.remove(key));
            return "Collection has been successfully cleared";
        }
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "clear";
    }
    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "clear : clear the collection \n";
    }
}
