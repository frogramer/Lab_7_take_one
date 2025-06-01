
package org.example.IO;

import org.example.Commands.*;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.CustomClasses.Person;
import org.example.Serializers.DragonDeserializer;
import org.example.Serializers.PersonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server {
    private static final int PORT = 4782;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static String checkNewClient(DatagramChannel datagramChannel, SocketAddress clientAddress, Connection connection)
    {
        System.out.println("Checking in process");
        String response = null;
        try {

            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            datagramChannel.receive(readBuffer);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(readBuffer.array(), 0, readBuffer.limit());
            ObjectInputStream objectIn  = new ObjectInputStream(byteIn);
            System.out.println("Checking in process");
            Client client = (Client) objectIn.readObject();
            if (client.isSignedIn())
            {
                response = DataBase.signInClient(client, connection);
            }
            else
            {
                response = DataBase.registerNewClient(client, connection);
            }
            System.out.println(response);
            Server.sendResponse(response, clientAddress, datagramChannel);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String checkNewClient(Client client, DatagramChannel datagramChannel, SocketAddress clientAddress, Connection connection)
    {
        System.out.println("Checking in process");
        String response = null;
        try {
            System.out.println(client.getLogin1() + "\n"+ client.getPassword());
            if (client.isSignedIn())
            {
                response = DataBase.signInClient(client, connection);
            }
            else
            {
                response = DataBase.registerNewClient(client, connection);
            }
            System.out.println(response + "hui");
            Server.sendResponse(response, clientAddress, datagramChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void readDefaultCollection(DatagramChannel datagramChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        SocketAddress clientAddress = datagramChannel.receive(buffer);
        if (clientAddress != null) {
            buffer.flip();
            ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream objectIn  = new ObjectInputStream(byteIn);
            Gson gson = new GsonBuilder().registerTypeAdapter(Dragon.class, new DragonDeserializer()).
                    registerTypeAdapter(Person.class, new PersonDeserializer()).create();
            try {
                while (objectIn != null && buffer.hasRemaining()) {
                    ArrayList<Dragon> dragons  = (ArrayList<Dragon>) objectIn.readObject();
                    //dragons.stream().map(x -> InsertCommand.InsertDragon((Dragon) x));
                    Dragon[] ddragons = new Dragon[dragons.size()];
                    dragons.toArray(ddragons);
                    Arrays.stream(ddragons).forEach(x -> InsertCommand.InsertDragon(x));
                    /*ArrayList<Dragon> dragons = objectIn.readObject();
                    for (Dragon dragon : readCollection.dragons) {
                        InsertCommand.InsertDragon(dragon);
                    }*/
                    //new SaveCommand().Execute(datagramChannel, clientAddress);
                }
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (EOFException e)
            {

            } catch (StreamCorruptedException e) {
                new ClearCommand().Execute(new Scanner(System.in));
            }
        }
    }
    public static void sendResponse(String response, SocketAddress socketAddress, DatagramChannel datagramChannel)
    {
        threadPool.execute(() -> {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
            try {
                outputStreamWriter.write(response);
                outputStreamWriter.flush();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                datagramChannel.send(byteBuffer, socketAddress);
                //System.out.println("Response sent");
            } catch (IOException e) {
                System.out.println("Error in sending response");
            }
        });
    }
    public static DatagramChannel connect() throws IOException {
        DatagramChannel datagramChannel;
        try
        {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            datagramChannel = datagramChannel.bind(new InetSocketAddress(Server.getServerPort()));
            //System.out.println("Listening on port " + Server.getServerPort());
            checkConnection(datagramChannel);
            //ReadCollection.readCollection(System.getenv("Default_Dragons"));

            return datagramChannel;
        }catch (IOException e) {
            System.out.println("Error in start of server");
            return null;
        }
    }
    public static void checkConnection(DatagramChannel datagramChannel) {
        boolean connected = false;
        while (!connected) {
            try{Thread.sleep(1000);
                SocketAddress socketAddress = datagramChannel.receive(ByteBuffer.allocate(1024));
                Thread.sleep(10);
                if (socketAddress != null) {
                    connected = true;
                    System.out.println("connected");
                    datagramChannel.send(ByteBuffer.wrap("response".getBytes()), socketAddress);
                    Thread.sleep(100);

                    sendResponse(new ShowCommand().Execute(datagramChannel, socketAddress), socketAddress, datagramChannel);
                }
                else {
                    /*datagramChannel.close();
                    datagramChannel = DatagramChannel.open();
                    datagramChannel.configureBlocking(false);
                    datagramChannel = datagramChannel.bind(new InetSocketAddress(Server.getServerPort()));*/
                }
            } catch (IOException | NullPointerException | InterruptedException e)
            {

            }
        }
    }
    public static ByteArrayInputStream readSocket(DatagramChannel datagramChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        SocketAddress clientAddress = datagramChannel.receive(buffer);
        if (clientAddress != null) {
            buffer.flip();
            ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            return byteIn;
        }
        return null;
    }
    public static int getServerPort()
    {
        return PORT;
    }
}
