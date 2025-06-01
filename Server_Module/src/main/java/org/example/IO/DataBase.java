package org.example.IO;

import org.example.Commands.ShowCommand;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Coordinates;
import org.example.CustomClasses.Dragon;
import org.example.CustomClasses.Person;
import org.example.EnumClasses.Color;
import org.example.EnumClasses.Country;
import org.example.EnumClasses.DragonCharacter;
import org.example.EnumClasses.DragonType;
import org.postgresql.util.PGobject;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
public class DataBase {
    public static int notYetSighed = 0;
    private static final ReentrantLock lock = new ReentrantLock(true);
    public static Connection connect()
    {
        Connection connection = null;
        try{
        String url = "jdbc:postgresql://localhost:4782/dragons_data_base";
        String user = "postgres";
        String password = "361080";
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Подключение к базе данных успешно!");
        return connection;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }
    public static void saveDragons(Connection connection, Client client)
    {
        lock.lock();
        String response = null;
        clearDataBase(connection, client);
        Dragon.dragons.entrySet().stream().filter(x -> {
            if (x.getValue().getOwnerId() == null)
            {
                System.out.println("pizdec");
            }
            if (x.getValue().getOwnerId().equals(client.getId()))
            {
                return true;
            }
            return false;
        }
        ).map(x -> {
            try {
                String sql = "INSERT INTO dragon (id, name, coordinates, creation_date, color, age, character, type, killer," +
                        "owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PGobject coordinates = new PGobject();
                coordinates.setType("coordinates");
                coordinates.setValue("(" + x.getValue().GetCoordinates().GetXCoordinate().toString() + "," +
                        x.getValue().GetCoordinates().GetYCoordinate().toString() + ")");
                preparedStatement.setObject(3, coordinates);
                String fin = "";
                PGobject killer = new PGobject();
                killer.setType("person");
                if (x.getValue().GetDragonKiller() != null) {
                    String name = x.getValue().GetDragonKiller().GetPersonName();
                    String birthday = null;
                    String eyeColor = null;
                    String nationality = null;
                    fin = "(" + name;
                    if (x.getValue().GetDragonKiller().GetBirthdayDate() != null)
                    {
                        birthday = x.getValue().GetDragonKiller().GetBirthdayDate().toString();
                        fin += "," + birthday;
                    }
                    else {
                        fin += ",";
                    }
                    if (x.getValue().GetDragonKiller().GetPersonEyeColor() != null)
                    {
                        eyeColor = x.getValue().GetDragonKiller().GetPersonEyeColor().toString();
                        fin += "," + eyeColor;
                    }
                    else {
                        fin += ",";
                    }
                    if (x.getValue().GetDragonKiller().GetNationality() != null)
                    {
                        nationality = x.getValue().GetDragonKiller().GetNationality().toString();
                        fin += "," + nationality;
                    }
                    else {
                        fin += ",";
                    }
                    fin += ")";
                    System.out.println(fin);
                    killer.setValue(fin);
                    /*if (x.getValue().GetDragonKiller().GetBirthdayDate() != null) {
                        if (x.getValue().GetDragonKiller().GetPersonEyeColor() != null) {
                            killer.setValue("(" + x.getValue().GetDragonKiller().GetPersonName() + "," +
                                    x.getValue().GetDragonKiller().GetBirthdayDate().toString() + "," +
                                    x.getValue().GetDragonKiller().GetPersonEyeColor().toString() + "," +
                                    x.getValue().GetDragonKiller().GetNationality().toString() + ")");
                        }
                        else {
                            killer.setValue("(" + x.getValue().GetDragonKiller().GetPersonName() + "," +
                                    x.getValue().GetDragonKiller().GetBirthdayDate().toString() + "," +
                                    x.getValue().GetDragonKiller().GetNationality().toString() + ")");
                        }
                    }
                    else if (x.getValue().GetDragonKiller().GetPersonEyeColor() != null){
                        killer.setValue("(" + x.getValue().GetDragonKiller().GetPersonName() + ","+
                                x.getValue().GetDragonKiller().GetPersonEyeColor().toString() + "," +
                                x.getValue().GetDragonKiller().GetNationality().toString() + ")");
                    }
                    else
                    {
                        killer.setValue("(" + x.getValue().GetDragonKiller().GetPersonName() + "," +
                                x.getValue().GetDragonKiller().GetPersonEyeColor().toString() + ")");
                    }*/
                }
                else {
                    fin = ",,,";
                }
                preparedStatement.setObject(9, killer);
                PGobject color = new PGobject();
                color.setType("color");
                color.setValue(x.getValue().GetDragonColor().toString());
                PGobject character = new PGobject();
                character.setType("dragon_character");
                character.setValue(x.getValue().GetDragonCharacter().toString());
                PGobject type = new PGobject();
                type.setType("dragon_type");
                if (x.getValue().GetDragonType() != null) {
                    type.setValue(x.getValue().GetDragonType().toString());
                    preparedStatement.setObject(8, type);
                }
                else {
                    type.setValue("");
                    //preparedStatement.setObject(8, type);
                    preparedStatement.setNull(8, Types.OTHER);
                }
                preparedStatement.setInt(1, x.getKey());
                preparedStatement.setString(2, x.getValue().GetName());
                preparedStatement.setObject(4, x.getValue().GetDragonCreationDate());
                preparedStatement.setObject(5, color);
                preparedStatement.setLong(6, x.getValue().GetDragonAge());
                preparedStatement.setObject(7, character);
                preparedStatement.setInt(10, client.getId());
                preparedStatement.executeUpdate();
                return "it's fine";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "all done";
        }).forEach(System.out::println);
        lock.unlock();
    }
    public static ArrayList<Dragon> getDragons(Connection connection)
    {
        lock.lock();
        ArrayList<Dragon> dragons = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dragon";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //Dragon dragon = new Dragon();
                //String password = resultSet.getString("password");
                //String salt = resultSet.getString("salt");
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer ownerId = resultSet.getInt("owner_id");
                PGobject pgObj = (PGobject) resultSet.getObject("coordinates");
                String value = pgObj.getValue();
                String[] parts = value.replaceAll("[()]", "").split(",");
                Float x = Float.parseFloat(parts[0]);
                Integer y = Integer.parseInt(parts[1]);
                Coordinates coordinates = new Coordinates(x, y);
                java.time.LocalDate creationDate = java.time.LocalDate.parse(resultSet.getString("creation_date"));
                Color color = Color.valueOf(resultSet.getString("color"));
                DragonType type = null;
                try{
                    type = DragonType.valueOf(resultSet.getString("type"));
                } catch (Exception e){
                    type = null;
                }
                DragonCharacter character = DragonCharacter.valueOf(resultSet.getString("character"));
                Integer age = resultSet.getInt("age");
                PGobject pgObj2 = (PGobject) resultSet.getObject("killer");
                Person person = null;
                if (pgObj2 != null) {
                    String value2 = pgObj2.getValue();
                    String[] parts2 = value2.replaceAll("[()]", "").replace("[", "")
                            .replace("]", "").split(",");
                    if (parts2.length == 4) {
                        parts2[1] = parts2[1].replaceAll("-", ".");
                        String[] dateparts = parts2[1].split("[.]");

                        parts2[1] = dateparts[2] +"."+ dateparts[1] +"."+ dateparts[0];
                        //System.out.println(parts2[1]);
                        person = new Person(parts2[0], parts2[1], parts2[2], parts2[3]);
                    } else if (parts2.length == 2) {
                        String personName = parts2[0];
                        person = new Person(parts2[0], null, "", parts2[1]);
                    } else {
                        if (parts2.length == 1) {
                            person = new Person(parts2[0], null, "", "");
                        } else {
                            if (parts2[1].contains("-")) {
                                parts2[1] = parts2[1].replaceAll("-", ".");
                                String[] dateparts = parts2[1].split("[.]");
                                parts2[1] = dateparts[2] +"."+ dateparts[1] +"."+ dateparts[0];
                                person = new Person(parts2[0], parts2[1], "", parts2[2]);
                            } else {
                                person = new Person(parts2[0], null, parts2[1], parts2[2]);
                            }
                        }

                    }
                }
                Dragon dragon = null;
                if (type != null) {
                    dragon = new Dragon(name, coordinates, age, color.toString().toLowerCase(),
                            type.toString().toLowerCase(), character.toString().toLowerCase(), person);
                }
                else {
                    dragon = new Dragon(name, coordinates, age, color.toString().toLowerCase(),
                            "", character.toString().toLowerCase(), person);
                }
                dragon.setCreationDate(creationDate.toString());
                dragon.Setid(id);
                dragon.setOwnerId(ownerId);
                dragons.add(dragon);
                //String coordinates = resultSet.get("coordinates");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        //return id;
        lock.unlock();
        return dragons;
    }
    private static void clearDataBase(Connection connection, Client client)
    {
        lock.lock();
        String sql = "delete from dragon where owner_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, client.getId());
            preparedStatement.executeUpdate();
            //preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
    public static Integer getId(Client client, Connection connection)
    {
        lock.lock();
        Integer id = null;
        try {
            String sql = "SELECT id FROM clients WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getLogin());
            //System.out.println(cli`ent.getLogin() + " " + client.getId() + " that's all");
            ResultSet resultSet = preparedStatement.executeQuery();
            //id = resultSet.getInt("id");
            if (resultSet.next()) {
                //String password = resultSet.getString("password");
                //String salt = resultSet.getString("salt");
                id = resultSet.getInt("id");
            }
            System.out.println(id + " - this is id of " + client.getLogin());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        lock.unlock();
        return id;
    }
    public static String registerNewClient(Client client, Connection connection)
    {
        lock.lock();
        String response = null;
        try {
            String sql = "INSERT INTO clients (login, password, salt) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, client.getHashedPassword());
            preparedStatement.setString(3, client.getSalt());
            preparedStatement.executeUpdate();
            response = String.valueOf(getId(client, connection));
            notYetSighed--;
        } catch (SQLException e)
        {
            response = "Sorry, this name is already taken. Please, choose another one";
            notYetSighed++;
        }
        lock.unlock();
        return response;
    }
    public static String signInClient(Client client, Connection connection)
    {
        lock.lock();
        String response = null;
        try {
            String sql = "SELECT id, password, salt FROM clients WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                System.out.println(password);
                System.out.println(client.getHashedPassword());
                if (password.equals(client.getHashedPassword()))
                {
                    response = resultSet.getString("id");
                    notYetSighed--;
                }
                else {
                    response = "Sorry, there is now no client with this login and password";
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            response = "Sorry, there is now no client with this login and password";
        }
        lock.unlock();
        return response;
    }
    public static void start_registration(DatagramChannel datagramChannel, Connection connection, SocketAddress clientAddress)
    {
        String response = "There are this dragons in collection:\n" + new ShowCommand().Execute(datagramChannel, clientAddress);
        Server.sendResponse(response, clientAddress, datagramChannel);
    }
    public static void end_registration(DatagramChannel datagramChannel, Connection connection, SocketAddress clientAddress)
    {

    }
    public static boolean loginExists(Connection conn, String username, String password)
    {
        return false;
    }
}
