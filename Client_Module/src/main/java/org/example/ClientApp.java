package org.example;

import org.example.Commands.*;
import org.example.CustomClasses.Client;
import org.example.IO.DataBase;
import org.example.IO.EnterClass;
import org.example.IO.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** ClientApp class, that starts with program start
 */
public class ClientApp {
    /** ClientApp method, that manages commands
     * @param args - equals null
     */
    public static Client client;
    public static void main(String[] args) throws IOException {

        //DatagramChannel datagramChannel = initialize();
        //Server.sendReadCollection(datagramChannel, ReadCollection.createCollection(System.getenv("Default_Dragons")));
        //ReadCollection.readCollection(System.getenv("Default_Dragons"), datagramChannel);
        //System.out.println(client.toString());
        sendingCommandsloop(initialize());
    }
    private static void sendingCommandsloop(DatagramChannel datagramChannel) {
        System.out.println(Client.curClient.getId());
        while (true) {
            //(DatagramChannel datagramChannel = Server.reconnect())
            try {
                /*datagramChannel.write(ByteBuffer.wrap("question".getBytes()));
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                Thread.sleep(500);
                datagramChannel.receive(readBuffer);*/
                DefaultCommand command = InputCommand();
                command.setClient(Client.curClient);
                System.out.println(command.getClient().getId());
                //command.setClient(Client.curClient);
                //command.setCurClient(client);
                sendCommand(command, datagramChannel);
                if (command.getClass() == UpdateCommand.class)
                {
                    UpdateCommand.Update(datagramChannel);
                }
                Thread.sleep(500);
                //System.out.println("Starting saving");
                SaveCommand.catchSave(datagramChannel);
                Thread.sleep(500);
                String response = catchResponse(datagramChannel);
                if (response != null)
                {
                    System.out.println(response);
                }
                else {
                    System.out.println("No response received");
                }
                //catchResponse(datagramChannel);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //System.out.println("pizda thread");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    String response = catchResponse(datagramChannel);
                    System.out.println(response);
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("There is no such command. Please, try again. You can see all the commands, using \"help\" command");
            }
        }
    }
    private static void sendCommand(DefaultCommand command, DatagramChannel datagramChannel) {

        try {
            if (command != null) {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOut);
                objectOutputStream.writeObject(command);
                objectOutputStream.flush();
                byte[] bytes = byteOut.toByteArray();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                datagramChannel.write(buffer);
                buffer.clear();
                //System.out.println("IO.Server sent: " + command);
                if (command.toString() == "exit") {
                    System.exit(0);
                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static String catchResponse(DatagramChannel datagramChannel) throws IOException {
        String response = "";
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(32768);
            ByteBuffer zerobuffer = ByteBuffer.allocate(4096);
            boolean done = false;
            while (/*response == "" || response == null ||*/
                /*byteBuffer.equals(zerobuffer) ||*/ byteBuffer.hasRemaining() /*|| byteBuffer.get() == 0*/) {
                datagramChannel.receive(byteBuffer);
                byteBuffer.flip();
                response = new String(byteBuffer.array(), StandardCharsets.UTF_8);
                response = response.trim();
                if (response.length() != 0 && done == false) {
                    /*response.replace("[", "");
                    response.replace("]", "");*/
                    //response.split(", ");
                    //System.out.println(response);
                    done = true;
                    //System.out.println("");
                }
                return response;
                //System.out.println("wait");
            }
        } catch (PortUnreachableException e) {
            System.out.println("Sorry, port is currently unreachable. Please, try again later");
            //System.exit(1);
        }
        return response;
    }

    public static DefaultCommand InputCommand()
    {
        HashMap<String, DefaultCommand> commands = new HashMap<>();
        ExecuteScriptCommand.PutCommands(commands);
        Scanner in = new Scanner(System.in);
        String current_command = "";
        System.out.println("Please enter a command");
        try{
            current_command = in.next();
            DefaultCommand sending_command = commands.get(current_command).createCommand(in);
            return sending_command;
            //HistoryCommand.AddCommand(commands.get(current_command));
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            //System.out.println("There is no such command. Please try again. You can see all the commands, using \"help\" command");
            return null;
        } catch (NoSuchElementException e) {
            commands.get("save").Execute(in);
            System.out.println("Now exiting the program");
            System.exit(0);
            return null;
        }
    }
    private static DatagramChannel initialize() {
        //IO.Server.connect();
        try {
            System.out.println("Hello and Welcome!\nYou are now using Dragon collection management app");
            /*System.out.println("What would you like to do?\nApp can execute follow commands:");
            new HelpCommand().Execute(new Scanner(System.in));*/
            DatagramChannel datagramChannel = Server.connect();
            Server.signIn(datagramChannel);
            //Client.curClient.setId(Server.signIn(datagramChannel));
            //Client.setCurClient(Server.signIn(datagramChannel));
            //System.out.println(client.getLogin() + client.getPassword());
            return datagramChannel;
            /*return Server.connect();\
            DatagramChannel datagramChannel = DatagramChannel.open();
            return datagramChannel;*/
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("There happened an error in connecting to server. Please, try again later");
            System.exit(0);
            return null;
        }
    }
}
