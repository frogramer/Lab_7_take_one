package org.example.Commands;

//import org.example.ClientApp;

import org.example.ClientApp;
import org.example.CustomClasses.Client;
import org.example.Exceptions.InvalidVariableValueException;
import org.example.Exceptions.NoSuchCommandException;
import org.example.Exceptions.NotUniqueIDException;

import java.io.*;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Class, that executes the commands script from needed file when corresponding command is called
 */
public class ExecuteScriptCommand implements DefaultCommand{
    static ArrayList<String> currently_executing = new ArrayList<>();
    static String[] current_command;
    String file_name;
    File sending_file;
    HashMap<String,String> files;
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
    public ExecuteScriptCommand(){}
    public ExecuteScriptCommand(String test_file_name, HashMap<String,String> files) {
        this.file_name = test_file_name;
        this.files = files;
    }
    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        setClient(ClientApp.client);
        String test_file_name = in.next();
        File test_file = new File(test_file_name);
        HashMap<String,String> files = new HashMap<>();
        try {
            InputStream inputStream = new FileInputStream(test_file);
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            currently_executing.add(file_name);
            addFile(test_file_name, bufferedReader, files);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        } catch (InvalidVariableValueException e) {
            System.out.println("Entered scripts have invalid data. Please, redact it and try again");
            return null;
        }
        return new ExecuteScriptCommand(test_file_name, files);
    }
    private void addFile(String file_name, BufferedReader reader, HashMap<String,String> files) throws FileNotFoundException, InvalidVariableValueException {
        String script = "";
        try {
            String line;
            while (true) {
                line = reader.readLine();
                if (line.contains("execute_script")) {
                    String[] line_contents = line.split(" ");
                    File file = new File(line_contents[1]);
                    if (!file.exists()) {
                        throw new FileNotFoundException();
                    }
                    else {
                        script += "execute_script " + this.client.getLogin() + "\\\\" +
                                line_contents[1].split("\\\\")[line_contents[1].split("\\\\").length - 1] + "\n";
                        InputStream inputStream = new FileInputStream(line_contents[1]);
                        Reader helpful_reader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(helpful_reader);
                        if (!currently_executing.contains(file_name)) {
                            currently_executing.add(file_name);
                            addFile(line_contents[1], bufferedReader, files);
                        }
                        else {
                            throw new InvalidVariableValueException();
                        }
                    }
                }
                else {
                    script += line + "\n";
                }
            }
        } catch (NullPointerException e)
        {
            System.out.println(script);
            files.put(file_name.split("\\\\")[file_name.split("\\\\").length - 1], script);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress)
    {
        HashMap<String,File> files_to_execute = new HashMap<>();

        Path path = Paths.get(client.getLogin());
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.files.entrySet().stream().forEach(x -> {
                try{
                    new File(path.toString() + "\\\\" + x.getKey()).createNewFile();
                    OutputStream outputStream = new FileOutputStream(x.getKey());
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.write(x.getValue());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    files_to_execute.put(x.getKey(), new File(x.getKey()));
                } catch (IOException e) {}
            });
        String response = Execute(this.file_name);
        response += "\nall done";
        this.files.entrySet().stream().forEach(x -> {new File(path.toString() + "\\\\" + x.getKey()).delete();});
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (response);
    }
    /** Method, that executes commands from needed file
     * @param filename - has file's name that needs to be executed
     * @throws FileNotFoundException - throws in case no file with {@param fileName} name exists
     * @throws IOException - throws in case file is not found, unavailable etc.
     * @throws NullPointerException - throws in case no value for {@param filename} or {@param input} exists
     */
    public String Execute (String filename)
    {
        String response = "";
        boolean can_execute = true;
        for (String test_filename : currently_executing)
        {
            if (test_filename.contains(filename) || filename.contains(test_filename))
            {
                can_execute = false;
            }
        }
        if (can_execute)
        {
            currently_executing.add(filename);
            HashMap<String, DefaultCommand> commands = new HashMap<>();
            PutCommands(commands);
            commands.entrySet().stream().forEach(x -> x.getValue().setClient(this.client));
            try (InputStream input = new FileInputStream(filename))
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                current_command = reader.readLine().split(" ");
                while (!current_command[0].isEmpty()) {
                    String current_response = commands.get(current_command[0]).Execute(reader);
                    if (current_response != "") {
                        response += "\n" + commands.get(current_command[0]).Execute(reader);
                    }
                    current_command = reader.readLine().split(" ");
                }
            } catch (NullPointerException e)
            {
            }
            catch(ArrayIndexOutOfBoundsException | InvalidVariableValueException |
                   DateTimeException | NoSuchCommandException | NotUniqueIDException |
                   NoSuchElementException | NotSerializableException | NumberFormatException e)
            {
                response = "File's data is invalid. Please redact it and try again";
                e.printStackTrace();
            } catch (FileNotFoundException e)
            {
                response = "File not found. Please try again.";
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            currently_executing.remove(0);
        }
        else{
            response = ("It seems, you are calling infinite recursion with executing scripts. Please, redact files and try again.");
        }
        /*String[] responses = response.split("\n");
        response = responses.toString();
        response.replace("[", "");
        response.replace("]", "");
        response.replace(", ", "\n");*/
        return response;
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute (Scanner in)
    {
        String fileName = in.next();
        Execute(fileName);
    }
    /** Method, that executes when command is called in another file
     * @throws ArrayIndexOutOfBoundsException - throws in case no file name has been found in file
     */
    @Override
    public String Execute (BufferedReader reader) {
        try{
            String fileName = current_command[1];
            String response = Execute(fileName);
            /*Dragon.dragons.entrySet().stream().forEach(x -> {if (x.getValue().getOwnerId() == null)
            {
                x.getValue().setOwnerId(this.client.getId());
            }
            });*/
            return response;
        }catch(ArrayIndexOutOfBoundsException e) {
            return ("File's data is invalid. Please redact file and try again/");
        }
    }
    /** Method, that fills the commands list with corresponding commands classes objects
     * @param commands - saves maps of all commands class objects and their inputs
     */
    public static void PutCommands(HashMap <String, DefaultCommand> commands)
    {
        commands.put("info", new InfoCommand());
        commands.put("help", new HelpCommand());
        commands.put("show", new ShowCommand());
        commands.put("insert", new InsertCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_key", new RemoveKeyCommand());
        commands.put("clear", new ClearCommand());
        commands.put("history", new HistoryCommand());
        commands.put("replace_if_greater", new ReplaceIfGreaterCommand());
        commands.put("replace_if_lower", new ReplaceIfLowerCommand());
        commands.put("min_by_coordinates", new MinByCoordinatesCommand());
        commands.put("max_by_type", new MaxByTypeCommand());
        commands.put("print_field_descending_color", new PrintFieldDescendingColorCommand());
        //commands.put("save", new SaveCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("exit", new ExitCommand());
    }
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "execute script";
    }



    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "execute_script {file name}: execute commands from file \n";
    }
}
