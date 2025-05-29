package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.CustomClasses.Dragon;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

import static java.lang.Math.sqrt;

/** Class, that searches the Dragon with minimum coordinates in collection when corresponding command is called
 */
public class MinByCoordinatesCommand implements DefaultCommand{
    /** Method, that seeks for and shows Dragon with minimum coordinates in collection
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
    private String ShowMin()
    {
        /*Comparator<Dragon> comparator = new Comparator<Dragon>() {
            @Override
            public int compare(Dragon o1, Dragon o2) {
                Integer y1 = o1.GetCoordinates().GetYCoordinate();
                Float x1 = o1.GetCoordinates().GetXCoordinate();
                Integer y2 = o2.GetCoordinates().GetYCoordinate();
                Float x2 = o2.GetCoordinates().GetXCoordinate();
                Double coordinates1 = sqrt(y1*y1 + x1*x1);
                Double coordinates2 = sqrt(y2*y2 + x2*x2);
                if (coordinates1 > coordinates2)
                {
                    return 1;
                }
                else if (coordinates1 < coordinates2)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        };*/
        Dragon.dragons.values().stream();
        Dragon closest = Dragon.dragons.values().stream().filter(x ->
        {
            if (x.getOwnerId().equals(this.client.getId()))
            {
                return true;
            }
            return false;
        }).min((o1, o2) -> {Integer y1 = o1.GetCoordinates().GetYCoordinate();
            Float x1 = o1.GetCoordinates().GetXCoordinate();
            Integer y2 = o2.GetCoordinates().GetYCoordinate();
            Float x2 = o2.GetCoordinates().GetXCoordinate();
            Double coordinates1 = sqrt(y1*y1 + x1*x1);
            Double coordinates2 = sqrt(y2*y2 + x2*x2);
            if (coordinates1 > coordinates2)
            {
                return 1;
            }
            else if (coordinates1 < coordinates2)
            {
                return -1;
            }
            else
            {
                return 0;
            }}).get();
        return ("Min_by_coordinates dragon is:\n" + closest.toString());
        /*Dragon curdragon = null;
        int curid;
        for (Map.Entry<Integer, Dragon> dragon : Dragon.dragons.entrySet()) {
            curdragon = dragon.getValue();
            curid = dragon.getKey();
            double position = sqrt((pow(curdragon.GetCoordinates().GetXCoordinate(), 2) + pow(curdragon.GetCoordinates().GetYCoordinate(), 2)));
            if (Coordinates.mincoordinates == -1)
            {
                Coordinates.minid = curid;
                Coordinates.mincoordinates = position;
            } else if (position < Coordinates.mincoordinates)
            {
                Coordinates.minid = curid;
                Coordinates.mincoordinates = position;
            }
        }*/
        //System.out.println("Min_by_coordinates dragon is:\n" + Dragon.dragons.get(Coordinates.minid).toString());
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in) {
        if (!Dragon.dragons.isEmpty()) {
            ShowMin();
        }
        else {
            System.out.println("Collection is empty");
        }
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader) {
        if (!Dragon.dragons.isEmpty()) {
            return ShowMin();
        }
        return "Collection is empty";

    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "min_by_coordinates";
    }

    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new MinByCoordinatesCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        if (!Dragon.dragons.isEmpty()) {
            return ShowMin();
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
        return "min_by_coordinates : show any element that has minimum value in coordinates \n";
    }
}
