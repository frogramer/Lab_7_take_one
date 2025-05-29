package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.EnumClasses.Color;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/** Class, that shows all Dragons colors from collection in descending order when corresponding command is called
 */
public class PrintFieldDescendingColorCommand implements DefaultCommand{
    /** Method, that shows color if needed color is found
     * @param color - has needed color
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
    private void FindColor(Color color)
    {
        for (Map.Entry<Integer, Dragon> dragon : Dragon.dragons.entrySet()) {
            if (dragon.getValue().GetDragonColor() == color) {
                System.out.print(color.toString() + " ");
            }
        }
    }
    /** Method, that seeks for all colors in collection
     */
    private String ShowColor()
    {
        String response = "";
        Color[] colors = Color.values();
        response += Arrays.stream(colors).filter(x ->
        {Dragon.dragons.entrySet().stream().filter(w ->
        {
            if (w.getValue().getOwnerId().equals(this.client.getId()))
            {
                return true;
            }
            return false;
        }).filter(y -> y.getValue().GetDragonColor() == x);
            return true;
        }).collect(Collectors.toCollection(ArrayList::new));
        String[] responses = response.split(", ");
        response = responses.toString();
        response = response.replace("[", "");
        response = response.replace("]", "");
        return response;
        /*Arrays.stream(colors).forEach(x ->
                {Dragon.dragons.entrySet().stream().filter(y -> y.getValue().GetDragonColor() == x);
                System.out.println(x + " ");});*/

        /*
        Arrays.stream(colors).forEach(x ->
                {Dragon.dragons.entrySet().stream().filter(y -> y.getValue().GetDragonColor() == x);
                System.out.println(x + " ");});
         */
        //Dragon.dragons.entrySet().stream().filter(x -> Arrays.stream(colors).filter(y -> y == x)).;
        /*Dragon.dragons.entrySet().stream().filter(x -> x.getValue().GetDragonColor() == Color.WHITE).
                forEach(x -> System.out.println(Color.WHITE.toString()));
        Dragon.dragons.entrySet().stream().filter(x -> x.getValue().GetDragonColor() == Color.ORANGE).
                forEach(x -> System.out.println(Color.ORANGE.toString()));
        Dragon.dragons.entrySet().stream().filter(x -> x.getValue().GetDragonColor() == Color.RED).
                forEach(x -> System.out.println(Color.RED.toString()));
        Dragon.dragons.entrySet().stream().filter(x -> x.getValue().GetDragonColor() == Color.YELLOW).
                forEach(x -> System.out.println(Color.YELLOW.toString()));
        Dragon.dragons.entrySet().stream().filter(x -> x.getValue().GetDragonColor() == Color.BLACK).
                forEach(x -> System.out.println(Color.BLACK.toString()));
        /*FindColor(Color.WHITE);
        FindColor(Color.ORANGE);
        FindColor(Color.RED);
        FindColor(Color.YELLOW);
        FindColor(Color.BLACK);*/
        //System.out.println();
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in) {
        if (!Dragon.dragons.isEmpty())
        {
            ShowColor();
        }
        else {
            System.out.println("Collection is empty");
        }
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        if (!Dragon.dragons.isEmpty())
        {
            return ShowColor();
        }
        return "Collection is empty";
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "Print_field_descending_color";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new PrintFieldDescendingColorCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        if (!Dragon.dragons.isEmpty())
        {
            return ShowColor();
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
        return "print_field_descending_color : see all element's color in descending order \n";
    }
}
