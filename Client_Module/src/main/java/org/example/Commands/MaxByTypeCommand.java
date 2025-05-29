package org.example.Commands;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
/** Class, that searches the Dragon with maximum type in collection when corresponding command is called
 */
public class MaxByTypeCommand implements DefaultCommand{
    /** Method, that seeks for and shows Dragon with maximum type in collection
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
    private String ShowMax()
    {
        /*Comparator<Dragon> comparator = new Comparator<Dragon>() {
            @Override
            public int compare(Dragon o1, Dragon o2) {
                if (o1.GetDragonType() == null)
                {
                    if (o2.GetDragonType() == null)
                        return 0;
                    else
                    {
                        return -1;
                    }
                }
                else {
                    if (o2.GetDragonType() == null)
                    {
                        return 1;
                    }
                    else {
                        if (o1.GetDragonType().toString().length() > o2.GetDragonType().toString().length()) {
                            return 1;
                        } else if (o1.GetDragonType().toString().length() < o2.GetDragonType().toString().length()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        };*/
        Dragon.dragons.values().stream();
        Dragon strongest = Dragon.dragons.values().stream().filter(x ->
        {
            if (x.getOwnerId().equals(this.client.getId()))
            {
                return true;
            }
            return false;
        }).max((o1, o2) -> {
            if (o1.GetDragonType() == null) {
                return (o2.GetDragonType() == null) ? 0 : -1;
            } else if (o2.GetDragonType() == null) {
                return 1;
            } else {
                return Integer.compare(
                        o1.GetDragonType().toString().length(),
                        o2.GetDragonType().toString().length()
                );
            }
        }).get();
        return("Max by type dragon is:\n" + strongest.toString());
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in) {
        if(!Dragon.dragons.isEmpty())
        {
            ShowMax();
        }
        else {
            System.out.println("Collection is empty");
        }
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        if(!Dragon.dragons.isEmpty())
        {
            return ShowMax();
        }
        return "Collection is empty";
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "max_by_type";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new MaxByTypeCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        if(!Dragon.dragons.isEmpty())
        {
            return ShowMax();
        }
        else {
            return ("Collection is empty");
        }
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "max_by_type : return one element that has maximum value by Dragon's type \n";
    }
}
