package org.example.Commands;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
/** Class, that inserts new Dragon in collection when corresponding command is called
 */
public class InsertCommand implements DefaultCommand {
    /** Method, that executes when command is called in console
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
    Dragon dragon;
    public InsertCommand() {
    }
    public InsertCommand(Dragon dragon) {
        this.dragon = dragon;
    }
    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        Dragon dragon = Dragon.NewDragon();
        return new InsertCommand(dragon);
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress){
        InsertDragon(this.dragon);
        return("New Dragon has been inserted successfully");
    }

    @Override
    public void Execute(Scanner in) {
        InsertDragon(Dragon.NewDragon());
        System.out.println("New Dragon has been inserted successfully");
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        try {
            Dragon dragon = Dragon.NewDragon(reader);
            dragon.setOwnerId(this.client.getId());
            InsertDragon(dragon);
            if (dragon.getOwnerId().equals(this.client.getId())) {
                System.out.println(this.client.getId());
            }
            System.out.println(this.client.getId() + " " + dragon.getOwnerId());
            //System.out.println();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /** Method, that inserts needed Dragon in collection
     * @param dragon - dragon, that needs to be inserts in collection
     */
    public static void InsertDragon(Dragon dragon)
    {

        //new Thread(() -> Dragon.dragons.put(dragon.GetDragonid(), dragon)).start();
        Dragon.dragons.put(dragon.GetDragonid(), dragon);
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString() {
        return "insert";
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp() {
        return "insert : add new element \n";}
}

