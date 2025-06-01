
package org.example.IO;

import org.example.ClientApp;
import org.example.Commands.DefaultCommand;
import org.example.Commands.ReadCollection;
import org.example.CustomClasses.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    private static final String SERVER_ADDRESS = "localhost"; //helios.cs.ifmo.ru
    private static final int SERVER_PORT = 4782;
    boolean have_been_connected = false;
    public static DatagramChannel connect() {
        boolean connected = false;
        DatagramChannel datagramChannel = null;

        ByteBuffer readBuffer = ByteBuffer.wrap(new byte[1024]);
        //readBuffer.clear();
        readBuffer.flip();
        boolean shown = false;
        try {
            while (!connected) {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            //InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(Server.getServerAddress()), Server.getPort());
            InetSocketAddress socketAddress = new InetSocketAddress(Server.getServerAddress(), Server.getPort());
            datagramChannel = datagramChannel.connect(socketAddress);
            ByteBuffer buffer = ByteBuffer.wrap("question".getBytes());
            //datagramChannel = datagramChannel.connect(socketAddress);
                //datagramChannel.connect(socketAddress);
                try {
                    //datagramChannel = datagramChannel.connect(socketAddress);
                    datagramChannel.write(buffer);
                    Thread.sleep(1000);
                    //datagramChannel.send(buffer, socketAddress);

                    /*if (datagramChannel != null && datagramChannel.isConnected() /*&& datagramChannel.isRegistered()
                            && datagramChannel.isOpen()) {*/
                        try {
                            if (datagramChannel.receive(readBuffer) != null) {
                                connected = true;
                                //System.out.println("connected");
                                Thread.sleep(100);
                                //System.out.println("now catching dragons");
                                System.out.println(ClientApp.catchResponse(datagramChannel));
                                return datagramChannel;
                            }
                        } catch (PortUnreachableException e)
                        {
                            if (shown == false)
                            {
                                System.out.println("App is currently trying to connect to the server. Please, wait...");
                                shown = true;
                            }
                            //datagramChannel.connect(socketAddress);
                        }
                //}
                } catch (IOException | AlreadyConnectedException e) {
                    //e.printStackTrace();
                    //datagramChannel.connect(socketAddress);
                    //e.printStackTrace();
                /*System.out.println("IO.Server is currently unavailable");
               System.exit(1);
               return null;*/
                    //System.out.println("Server connection failed");
                   // e.printStackTrace();
                }
                datagramChannel.close();
                //datagramChannel.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return datagramChannel;
    }
    public static String signIn(DatagramChannel datagramChannel)
    {
        Client client = null;
        boolean done = false;
        while (!done) {
            try {
                client = EnterClass.enteringAPP();
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOut);
                objectOutputStream.writeObject(client);
                objectOutputStream.flush();
                byte[] bytes = byteOut.toByteArray();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                datagramChannel.write(buffer);
                Thread.sleep(3000);
                ByteBuffer readBuffer = ByteBuffer.allocate(4096);
                //readBuffer.flip();
                /*datagramChannel.receive(readBuffer);
                String response = new String(readBuffer.array(), StandardCharsets.UTF_8);*/
                String response = ClientApp.catchResponse(datagramChannel);

                Thread.sleep(100);
                String id = ClientApp.catchResponse(datagramChannel);
                try
                {
                    Integer.parseInt(response);
                    System.out.println(id);

                }
                catch (NumberFormatException e)
                {
                    System.out.println(response);
                }
                if (response.contains("Sorry")  || id.contains("Sorry") || response == null || response.equals(""))
                {
                    done = false;
                }
                else {
                    done = true;
                    //ClientApp.catchResponse(datagramChannel);
                    try {
                        //System.out.println("parsed" + Integer.parseInt(response));
                        //Client.getCurClient() = client;
                        Client.setCurClient(client);
                        Client.setCurId(Integer.parseInt(response));
                        //Client.curClient.setId(Integer.parseInt(response));
                    } catch (NumberFormatException e) {
                        //System.out.println("parsed" + Integer.parseInt(id));
                        Client.setCurClient(client);
                        Client.setCurId(Integer.parseInt(id));
                        //Client.curClient.setId(Integer.parseInt(id));
                        //Client.curClient.
                    }
                    return response;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();

                done = false;
            }
        }
        //System.out.println(client.toString());
        ClientApp.client = client;
        //System.out.println(ClientApp.client.toString());
        return "";
    }
    public static DatagramChannel reconnect() {
        boolean connected = false;
        DatagramChannel datagramChannel = null;

        ByteBuffer readBuffer = ByteBuffer.wrap(new byte[1024]);
        //readBuffer.clear();
        readBuffer.flip();
        boolean shown = false;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            //InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(Server.getServerAddress()), Server.getPort());
            InetSocketAddress socketAddress = new InetSocketAddress(Server.getServerAddress(), Server.getPort());
            datagramChannel = datagramChannel.connect(socketAddress);
            /*while (!connected) {
                datagramChannel = DatagramChannel.open();
                datagramChannel.configureBlocking(false);
                //InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(Server.getServerAddress()), Server.getPort());
                InetSocketAddress socketAddress = new InetSocketAddress(Server.getServerAddress(), Server.getPort());
                datagramChannel = datagramChannel.connect(socketAddress);
                //String check_connection = "question" + ClientApp.login;
                //ByteBuffer buffer = ByteBuffer.wrap(check_connection.getBytes());
                //datagramChannel = datagramChannel.connect(socketAddress);
                //datagramChannel.connect(socketAddress);
               /* try {
                    //datagramChannel = datagramChannel.connect(socketAddress);
                    datagramChannel.write(buffer);
                    Thread.sleep(1000);
                    //datagramChannel.send(buffer, socketAddress);

                    /*if (datagramChannel != null && datagramChannel.isConnected() /*&& datagramChannel.isRegistered()
                            && datagramChannel.isOpen()) {*/
                    /*try {
                        if (datagramChannel.receive(readBuffer) != null) {
                            connected = true;
                            //System.out.println("connected");
                            return datagramChannel;
                        }
                    } catch (PortUnreachableException e)
                    {
                        if (shown == false)
                        {
                            System.out.println("App is currently trying to connect to the server. Please, wait...");
                            shown = true;
                        }
                        //datagramChannel.connect(socketAddress);
                    }
                    //}
                } catch (IOException | AlreadyConnectedException e) {

                    //datagramChannel.connect(socketAddress);
                    //e.printStackTrace();
                /*System.out.println("IO.Server is currently unavailable");
               System.exit(1);
               return null;*/
                    //System.out.println("Server connection failed");
                    // e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datagramChannel;
    }
    public static void sendReadCollection(DatagramChannel datagramChannel, ReadCollection readCollection) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOut);
            objectOutputStream.writeObject(readCollection);
            objectOutputStream.flush();
            byte[] bytes = byteOut.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            datagramChannel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendResponse(String response)
    {
    }
    public static void sendObject(SocketChannel channel, DefaultCommand command) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
            objOut.writeObject(command);
        }
        byte[] bytes = byteStream.toByteArray();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4096);
        //lengthBuffer.flip();
        lengthBuffer.putInt(bytes.length);
        lengthBuffer.flip();
        while (lengthBuffer.hasRemaining()) {
            channel.write(lengthBuffer);
        }

        ByteBuffer dataBuffer = ByteBuffer.wrap(bytes);
        while (dataBuffer.hasRemaining()) {
            channel.write(dataBuffer);
        }
        System.out.println("IO.Server sent " + bytes.length + " bytes");
    }
    public static int getPort()
    {
        return SERVER_PORT;
    }
    public static String getServerAddress()
    {
        return SERVER_ADDRESS;
    }
}
