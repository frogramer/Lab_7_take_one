package org.example;

import ch.qos.logback.classic.Logger;
import org.example.Commands.*;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.IO.DataBase;
import org.example.IO.Logging;
import org.example.IO.Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class ServerApp {
    private static final Logger logger = Logging.getLogger(ServerApp.class);
    private static int notYetSighed = 0;
    public static void main(String[] args)  throws InterruptedException {
        boolean connected = false;
        /*Integer attempts_to_connect = 0;
        try {
            DatagramChannel datagramChannel = Server.connect();
        while (!connected && attempts_to_connect < 10) {
            try {
                connected = true;
                logger.info("New client connected to the server");
                listenLoop(datagramChannel);
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
                //System.out.println("Error in start of server");
                //attempts_to_connect++;
            }
        }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }*/
        try {
            listenLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    public static void listenLoop(/*DatagramChannel datagramChannel*/) throws IOException, InterruptedException {
        //Thread.sleep(1000);
        /*while(Dragon.dragons.isEmpty()) {
            Server.readDefaultCollection(datagramChannel);
        }*/
            //logger.info("Default collection read");
            //new ShowCommand().Execute(new Scanner(System.in));
        /*DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel = datagramChannel.bind(new InetSocketAddress(Server.getServerPort()));
        System.out.println("Listening on port " + Server.getServerPort());*/
        Connection connection = DataBase.connect();
        ReadCollection.readDataBase(connection);
        DatagramChannel datagramChannel = Server.connect();
        DataBase.notYetSighed++;
        /*try {
            ReadCollection.readCollection(System.getenv("Default_Dragons"), datagramChannel);
        } catch (Exception e) {
            logger.debug("Can't read default collection");
        }*/


        //logger.info("New client connected");
        boolean first = false;
            while (true) {
                try {
                    //Server.checkConnection(datagramChannel);

                    /*ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    ByteBuffer writeBuffer = ByteBuffer.wrap("response".getBytes(StandardCharsets.UTF_8));
                    SocketAddress socketAddress = datagramChannel.receive(readBuffer);
                    if (socketAddress != null) {
                        datagramChannel.send(writeBuffer, socketAddress);
                        Thread.sleep(500);*/
                        readCommand(datagramChannel, connection);


                       //datagramChannel.close();
                    //}
                } catch (ClassCastException e) {
                    logger.debug(e.getMessage());
                    continue;
                } catch (IOException | ClassNotFoundException e) {
                    //System.out.println("Error in work of the server");
                    logger.debug(e.getMessage());
                }
                /*DefaultCommand command = readCommand(datagramChannel);
                if (command != null) {
                    command.Execute(datagramChannel);
                    //String response = command.toString() + " is done successfully";
                    //Server.sendResponse(response, datagramChannel.getRemoteAddress(), datagramChannel);
                    HistoryCommand.AddCommand(command);
                    new SaveCommand().Execute(datagramChannel);
                }*/
            }
    }
    private static void readCommand(DatagramChannel datagramChannel, Connection connection) throws IOException, ClassNotFoundException, ClassCastException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        SocketAddress clientAddress = datagramChannel.receive(buffer);
        /*try {
            if (!Logging.addresses.contains(clientAddress) && clientAddress != null) {

            }
        } catch (NullPointerException e) {
            Logging.addresses.add(clientAddress);
            logger.info("New client connected: " + clientAddress);
        }*/
        if (clientAddress != null) {
            buffer.flip();
            String question = new String(buffer.array(), StandardCharsets.UTF_8);

            //System.out.println(question.trim() + "hehe");
            if (question.trim().equals("question")) {
                DataBase.notYetSighed++;
                ///return;
                buffer.clear();
                buffer = ByteBuffer.wrap("response".getBytes(StandardCharsets.UTF_8));
                /*try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                datagramChannel.send(buffer, clientAddress);
                System.out.println("Now checking client in");
                Server.checkNewClient(datagramChannel, clientAddress, connection);
                buffer.clear();
                clientAddress = datagramChannel.receive(buffer);
                Logging.addresses.add(clientAddress);
                logger.info("New client connected: " + clientAddress);
                //DataBase.start_registration(datagramChannel, connection, clientAddress);
                //return;
                //logger.info("New client connected");
                ///if (question = "question1")
            /// send response go on
            }
            ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream objectIn  = new ObjectInputStream(byteIn);
            DefaultCommand command = null;
            if (UpdateCommand.updatecomands > 0)
            {
                ByteBuffer buffer2 = buffer;
                ByteArrayInputStream byteIn2 = new ByteArrayInputStream(buffer2.array(), 0, buffer2.limit());
                ObjectInputStream objectIn2  = new ObjectInputStream(byteIn2);
                try {
                    Dragon dragon = (Dragon) objectIn2.readObject();
                    if (dragon != null)
                    {
                        UpdateCommand.updateChangedDragon(dragon, datagramChannel);
                        UpdateCommand.updatecomands--;
                    }
                } catch (ClassCastException e) {
                }
            }
            if (DataBase.notYetSighed > 0)
            {
                System.out.println("Checking if signed");
                ByteBuffer buffer2 = buffer;
                ByteArrayInputStream byteIn2 = new ByteArrayInputStream(buffer2.array(), 0, buffer2.limit());
                ObjectInputStream objectIn2  = new ObjectInputStream(byteIn2);
                /*System.out.println(buffer2.equals(null));
                System.out.println(byteIn2.equals(null));
                System.out.println(objectIn2.equals(null));*/
                try {
                    //System.out.println("Checking if signed1");
                    //System.out.println(objectIn2.readObject() + "hehe");
                    Client client = (Client) objectIn2.readObject();
                    //System.out.println("Checking if signed12");
                    if (client != null)
                    {
                       // System.out.println("Checking if signed2");
                        Server.checkNewClient(client, datagramChannel, clientAddress, connection);
                        //System.out.println("Checking if signed3");
                        //notYetSighed--;
                    }
                    else {
                        System.out.println("client is hull");
                    }
                    //System.out.println("Checking if signed3");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (objectIn != null && buffer.hasRemaining()) {
                command = (DefaultCommand) objectIn.readObject();
                //command.getClient().setId(DataBase.getId(command.getClient(), connection));
                //Client.setClient(command.getClient());
                if (command.getClass() == UpdateCommand.class) {
                    UpdateCommand.updatecomands += 1;
                }
                HistoryCommand.AddCommand(command, command.getClient());
                /*if (command.getClass() == UpdateCommand.class)
                {
                    UpdateCommand updateCommand = (UpdateCommand) command;
                    updateCommand.Execute(datagramChannel, clientAddress);

                }
                else {*/
                    String response = command.Execute(datagramChannel, clientAddress);
                    //System.out.println("Command executed");
                    logger.info("Command received and executed: " + command.toString());
                    new SaveCommand().Execute(datagramChannel, clientAddress);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.debug(e.getMessage());
                    }
                    Server.sendResponse(response, clientAddress, datagramChannel);
                    logger.info("Response sent");
                    System.out.println(command.getClient().getId());
                    SaveCommand.executeSaveDatabase(datagramChannel, connection, command.getClient());
                    /*ByteBuffer responseBuffer = ByteBuffer.wrap("response".getBytes(StandardCharsets.UTF_8));
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    datagramChannel.send(responseBuffer, clientAddress);*/
               // }
                //Server.sendResponse(response, clientAddress, datagramChannel);
                //return command;
            }
        }
        //return null;
    }

}
