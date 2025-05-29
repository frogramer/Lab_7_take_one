package org.example.Commands;

import org.example.CustomClasses.Dragon;
import org.example.CustomClasses.Person;
import org.example.Exceptions.InvalidVariableValueException;
import org.example.IO.DataBase;
import org.example.Serializers.DragonDeserializer;
import org.example.Serializers.PersonDeserializer;
import com.google.gson.*;

import java.io.*;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.time.DateTimeException;
import java.util.ArrayList;

/** Class, that reads json file with saved dragon collection and adds elements to collection
 */
public class ReadCollection implements Serializable{
    public ArrayList<Dragon> dragons;
    public static void readDataBase(Connection connection)
    {
        ArrayList<Dragon> dragons = DataBase.getDragons(connection);
        dragons.stream().forEach(x -> Dragon.dragons.put(x.GetDragonid(), x));
    }
    public ReadCollection(ArrayList<Dragon> dragons) {
        this.dragons = dragons;
    }
    /** Method, that realises reading and saving dragons from file to collection
     * @param filename - name of .json file that needs to be read
     * @throws UnsupportedOperationException - thrown in case data in needed file is written incorrectly
     * @throws NullPointerException - thrown in case needed data is missing
     * @throws ArrayIndexOutOfBoundsException - thrown in case error occurs within data from needed file
     * @throws DateTimeException - thrown in case date data from needed file is invalid
     * @throws InvalidVariableValueException - thrown in case any data from needed file is invalid
     * @throws JsonSyntaxException - thrown in case needed json file has critical mistakes in writing
     */
    public static void readCollection(String filename, DatagramChannel datagramChannel) throws Exception {
        //try {
            InputStream input = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Gson gson = new GsonBuilder().registerTypeAdapter(Dragon.class, new DragonDeserializer()).
                    registerTypeAdapter(Person.class, new PersonDeserializer()).create();
            JsonObject[] jsonArray = gson.fromJson(reader, JsonObject[].class);
            //Arrays.stream(jsonArray).forEach(x -> InsertCommand.InsertDragon(gson.fromJson(x, Dragon.class)));
            ArrayList<Dragon> dragons = new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                Dragon dragon = gson.fromJson(jsonElement, Dragon.class);
                dragons.add(dragon);
                InsertCommand.InsertDragon(dragon);
            }
            //new SaveCommand().Execute(new Scanner(System.in));
            //new ClearCommand().Execute(new Scanner(System.in));
            /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(dragons);
            objectOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            datagramChannel.write(buffer);*/
            //buffer.clear();
        /*}catch(UnsupportedOperationException | NullPointerException | ArrayIndexOutOfBoundsException | DateTimeException | InvalidVariableValueException | NumberFormatException |
               JsonSyntaxException e) {
            System.out.println("File " + filename + " has wrong data. Please, redact it and try again.");
        } catch (FileNotFoundException e) {
            System.out.println("It seems file could not be found. Please, try again later");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    public static ReadCollection createCollection(String filename) {
        try {
            InputStream input = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Gson gson = new GsonBuilder().registerTypeAdapter(Dragon.class, new DragonDeserializer()).
                    registerTypeAdapter(Person.class, new PersonDeserializer()).create();
            JsonObject[] jsonArray = gson.fromJson(reader, JsonObject[].class);
            //Arrays.stream(jsonArray).forEach(x -> InsertCommand.InsertDragon(gson.fromJson(x, Dragon.class)));
            ArrayList<Dragon> dragons = new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                dragons.add(gson.fromJson(jsonElement, Dragon.class));
            }
            //JsonObject[] jsonArray = gson.fromJson(reader, JsonObject[].class);
            return new ReadCollection(dragons);
        }catch(UnsupportedOperationException | NullPointerException | ArrayIndexOutOfBoundsException | DateTimeException | InvalidVariableValueException | NumberFormatException |
               JsonSyntaxException e) {
            System.out.println("File " + filename + " has wrong data. Please, redact it and try again.");
        } catch (FileNotFoundException e) {
            System.out.println("It seems file could not be found. Please, try again later");
        }
        return null;
    }
}