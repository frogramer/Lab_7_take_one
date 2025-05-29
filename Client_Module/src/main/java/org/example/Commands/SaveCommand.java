package org.example.Commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.CustomClasses.Person;
import org.example.Exceptions.InvalidVariableValueException;
import org.example.IO.DataBase;
import org.example.Serializers.DragonSerializer;
import org.example.Serializers.PersonSerializer;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
/**Class, that saves the collection to Dragons.json file when corresponding command is called
 */
public class  SaveCommand implements DefaultCommand {
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
    public static void catchSave(DatagramChannel datagramChannel)
    {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        Gson gson = new GsonBuilder().setPrettyPrinting().
                registerTypeAdapter(Dragon.class, new DragonSerializer()).
                registerTypeAdapter(Person.class, new PersonSerializer()).create();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\vanob\\IdeaProjects\\Lab_6_take_one\\Dragons.json"))) {
            datagramChannel.receive(buffer);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream objectIn  = new ObjectInputStream(byteIn);
            buffer.flip();
            ArrayList<Dragon> dragons = new ArrayList<>();
            boolean done = false;
            while (buffer.hasRemaining() && !done) {
                try {
                    dragons = (ArrayList<Dragon>) objectIn.readObject();
                } catch (StreamCorruptedException e) {
                    done = true;
                }
            }
            //dragons.add();
            //String dragons = new String(buffer.array(), StandardCharsets.UTF_8);
            Dragon[] dragonArray = dragons.toArray(new Dragon[0]);
            //System.out.println(Arrays.toString(dragonArray));
            bufferedWriter.write(gson.toJson(dragonArray));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            //e.printStackTrace();
            /*try {
                String dragons = new String(buffer.array(), StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Dragons.json"));
                bufferedWriter.write(gson.toJson(dragons));
                bufferedWriter.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }*/
        } catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static void executeSaveDatabase(DatagramChannel datagramChannel, Connection connection, Client client)
    {
        DataBase.saveDragons(connection, client);
    }
    private void ExecuteSave(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        /** Method, that executes save to a file
         * @param dragon - map of Dragon and it's id
         * @param curdragon - currently analysing Dragon
         * @param dragon_list - saves all the Dragons  from collection
         * @param gson - is used to transform {@param dragon_list} ro Json class object
         * @param writer - writes json data to file
         * @throws IOException - throws in case file is not found, unavailable etc.
         */
        Dragon curdragon;
        Gson gson = new GsonBuilder().setPrettyPrinting().
                registerTypeAdapter(Dragon.class, new DragonSerializer()).
                registerTypeAdapter(Person.class, new PersonSerializer()).create();
        ArrayList<Dragon> dragon_list = new ArrayList();
        File file = new File("Dragons.json");
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Dragon.dragons.entrySet().stream().forEach(x -> dragon_list.add(x.getValue()));
            /*for (Map.Entry<Integer, Dragon> dragon : Dragon.dragons.entrySet()) {
                curdragon = dragon.getValue();
                dragon_list.add(curdragon);
            }*/
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(dragon_list);
            ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            datagramChannel.send(byteBuffer, socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in) {
        //ExecuteSave();
        System.out.println("Collection has been saved to file");
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        //ExecuteSave();
        return "";
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString() {
        return "save";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new SaveCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress clientAddress) {
        ExecuteSave(datagramChannel, clientAddress);
        return "Changes has been successfully saved to file";
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp() {
        return "save : save collection to json file \n";
    }
}
/** Method, that executes save to a file
 * @param dragon - map of Dragon and it's id
 * @param curdragon - currently analysing Dragon
 * @param dragon_list - saves all the Dragons  from collection
 * @param gson - is used to transform {@param dragon_list} ro Json class object
 * @param writer - writes json data to file
 * @throws IOException - throws in case file is not found, unavailable etc.
 */