package org.example.Commands;
import org.example.CustomClasses.Client;
import org.example.Exceptions.InvalidVariableValueException;

import java.io.BufferedReader;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
/** Interface, that is superior to all commands classes
 */
public interface DefaultCommand extends Serializable {
    Client client = null;
    Client getClient();
    void setClient(Client client);
    String toString();
    String toStringtoHelp();
    DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException;
    String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress);
    void Execute(Scanner in);
    String Execute (BufferedReader reader);

}
