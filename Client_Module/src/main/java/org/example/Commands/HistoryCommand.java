package org.example.Commands;
import org.example.CustomClasses.Client;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Class, that clears the collection when corresponding command is called
 */
public class HistoryCommand implements DefaultCommand{
    static ArrayList<DefaultCommand> history = new ArrayList<>();
    static HashMap<Integer, ArrayList<DefaultCommand>> historyMap = new HashMap<>();
    /**Method, that adds new command to history of last used commands
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
    public static void AddCommand(DefaultCommand command, Client client) {
        if (history.size() >= 10) {
            history.remove(0);
            System.gc();
        }
        history.add(command);
        if (historyMap.containsKey(client.getId())) {
            historyMap.get(client.getId()).add(command);
        }
        else {
            historyMap.put(client.getId(), new ArrayList<DefaultCommand>());
        }
        //historyMap.put(command.getClient().getId(), history);
    }
    /** Method, that shows history of last used commands
     */
    private String ShowHistory() {
        if (history.isEmpty()) {
            return("History is empty");
        }
        else {
            String response;
            Stream stream = history.stream();
            response = stream.collect(Collectors.toCollection(ArrayList::new)).toString();
            response = historyMap.entrySet().stream().filter(x ->
            {
                if (x.getKey().equals(this.getClient().getId()))
                {
                    return true;
                }
                return false;
            }).map(x -> x.getValue()).collect(Collectors.toCollection(ArrayList::new)).toString();
            response = response.replace("[", "");
            response = response.replace("]", "");
            response = response.replace(", ", "\n");
            return response;
            /*for (int i = 0; i < history.size(); i++) {
                System.out.print((i + 1) + ") " + history.get(i).toString() + " ");
            }
            System.out.println();*/
        }
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in)
    {
        ShowHistory();
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader)
    {
        return ShowHistory();
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "history";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new HistoryCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress){
        return ShowHistory();
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp() {
        return "history : see last ten commands (without the arguments) \n";
    }
}
