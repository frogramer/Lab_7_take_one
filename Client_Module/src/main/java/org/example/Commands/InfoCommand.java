package org.example.Commands;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;
/** Class, that show the collection information when corresponding command is called
 */
public class InfoCommand implements DefaultCommand {
    Date date;
    /** Constructor, that creates InfoCommand class object with current {@param date}
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
    public InfoCommand() {
        this.date = new Date();
    }
    /** Method, that shows collection information
     */
    private String ShowInfo()
    {
        long size = Dragon.dragons.entrySet().stream().filter(x ->
        {
            if (x.getValue().getOwnerId().equals(this.client.getId()))
            {
                return true;
            }
            return false;
        }).count();
        return ("Creation date: " + this.date + "\nType: Dragon" + "\nCollection size: " + Dragon.dragons.size() +
                "\nYour collection size: " + size + "\n");
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in)
    {
        ShowInfo();
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader)
    {
        return ShowInfo();
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString() {
        return "info";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new InfoCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) throws InvalidVariableValueException {
        return ShowInfo();
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "info : see collection's information (type, date of creation and size) \n";
    }
}
