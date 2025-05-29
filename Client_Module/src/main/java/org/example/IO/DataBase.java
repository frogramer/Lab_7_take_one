package org.example.IO;

import org.example.Commands.ShowCommand;
import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.postgresql.util.PGobject;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.*;
import java.util.Map;

public class DataBase {
    public static int notYetSighed = 0;
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
        String response = null;
        Dragon.dragons.entrySet().stream().map(x -> {
            try {
                String sql = "INSERT INTO dragon (id, name, coordinates, creation_date, color, age, character, type, killer," +
                        "owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PGobject coordinates = new PGobject();
                coordinates.setType("coordinate");
                coordinates.setValue(x.getValue().GetCoordinates().GetXCoordinate().toString() + ", " +
                        x.getValue().GetCoordinates().GetYCoordinate().toString());
                PGobject killer = new PGobject();
                killer.setType("person");
                killer.setValue(x.getValue().GetDragonKiller().GetPersonName().toString() + ", " +
                        x.getValue().GetDragonKiller().GetBirthdayDate().toString() + ", " +
                        x.getValue().GetDragonKiller().GetPersonEyeColor().toString() + " " +
                        x.getValue().GetDragonKiller().GetNationality().toString());
                preparedStatement.setInt(1, x.getKey());
                preparedStatement.setString(2, x.getValue().GetName());
                preparedStatement.setObject(3, coordinates);
                preparedStatement.setObject(4, x.getValue().GetDragonCreationDate());
                preparedStatement.setString(5, x.getValue().GetDragonColor().toString());
                preparedStatement.setLong(6, x.getValue().GetDragonAge());
                preparedStatement.setString(7, x.getValue().GetDragonCharacter().toString());
                preparedStatement.setString(8, x.getValue().GetDragonType().toString());
                preparedStatement.setObject(9, killer);
                preparedStatement.setInt(10, client.getId());
                preparedStatement.executeUpdate();
                return "it's fine";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "all done";
        }).forEach(System.out::println);
    }
    public static String registerNewClient(Client client, Connection connection)
    {
        String response = null;
        try {
            String sql = "INSERT INTO clients (login, password, salt) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, client.getHashedPassword());
            preparedStatement.setString(3, client.getSalt());
            preparedStatement.executeUpdate();
            response = "You are registered and signed in successfully";
            notYetSighed--;
        } catch (SQLException e)
        {
            response = "Sorry, this name is already taken. Please, choose another one";
            notYetSighed++;
        }
        return response;
    }
    public static String signInClient(Client client, Connection connection)
    {
        String response = null;
        try {
            String sql = "SELECT password, salt FROM clients WHERE login = ?";
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
                    response = "You are now signed in";
                    notYetSighed--;
                }
                else {
                    response = "Sorry, there is now no client with this login and password";
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return response;
    }
    public static void start_registration(DatagramChannel datagramChannel, Connection connection, SocketAddress clientAddress)
    {
        String response = "There are this dragons in collection:\n" + new ShowCommand().Execute(datagramChannel, clientAddress);
        //Server.sendResponse(response, clientAddress, datagramChannel);
    }
    public static void end_registration(DatagramChannel datagramChannel, Connection connection, SocketAddress clientAddress)
    {

    }
    public static boolean loginExists(Connection conn, String username, String password)
    {
        return false;
    }
}
