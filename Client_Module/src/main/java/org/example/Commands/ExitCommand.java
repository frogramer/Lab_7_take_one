package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/** Class, that exits the app when corresponding command is called
 */
public class ExitCommand implements DefaultCommand {
    /** Method, that exits the program when command is called from console
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
    @Override
    public void Execute(Scanner in){
        in.close();
        System.exit(0);
    }
    /** Method, that exits the program when command is called from another file
     */
    @Override
    public String Execute(BufferedReader reader){
        try {
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.exit(0);
        return "";
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "exit";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new ExitCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        //new Thread(() -> System.exit(0)).start();
        System.exit(0);
        return null;
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "exit: end session, close app with saving the collection" +
                "\nCTRL+D: save file to collection, close app \n";
    }
}
