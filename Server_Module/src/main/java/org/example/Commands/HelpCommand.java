package org.example.Commands;

import org.example.CustomClasses.Client;
import org.example.Exceptions.*;

import java.io.BufferedReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/** Class, that shows all the available commands when corresponding command is called
 */
public class HelpCommand implements DefaultCommand {
    /** Method, that returns this command's name
     */
    @Override
    public String toString()
    {
        return "help";
    }
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
    @Override
    public DefaultCommand createCommand(Scanner in) throws InvalidVariableValueException {
        return new HelpCommand();
    }

    @Override
    public String Execute(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        return PrintCommands();
    }

    /** Method, that returns this command's name and description
     */
    @Override
    public String toStringtoHelp()
    {
        return "help : see all the usable commands \n";
    }
    /** Method, that executes when command is called in console
     */
    @Override
    public void Execute(Scanner in)
    {
        System.out.println(PrintCommands());
    }
    /** Method, that executes when command is called in another file
     */
    @Override
    public String Execute(BufferedReader reader)
    {
        return PrintCommands();
    }
    /** Method, that shows all the available commands and their description
     */
    private String PrintCommands()
    {
        DefaultCommand[] commands = new DefaultCommand[] {new ClearCommand(), new ExecuteScriptCommand(),
        new ExitCommand(), new HelpCommand(), new HistoryCommand(), new InfoCommand(), new InsertCommand(),
                new MaxByTypeCommand(), new MinByCoordinatesCommand(), new PrintFieldDescendingColorCommand(),
                new RemoveKeyCommand(), new ReplaceIfLowerCommand(), new ReplaceIfGreaterCommand(), new ReplaceIfLowerCommand(),
                /*new SaveCommand(),*/ new ShowCommand(), new UpdateCommand()
        };
        String response = "";
        response = Arrays.stream(commands).map(x -> x.toStringtoHelp()).collect(Collectors.joining());
        return response;
        //Arrays.stream(commands).forEach(command -> System.out.println(command.toStringtoHelp()));
        //Stream<HashMap> streamOfhashmap = Collection;
        //new Thread(() -> commands);
        //Class InfoCommandClass = InfoCommand.class;
        //InfoCommandClass.
        /*System.out.println(InfoCommand.toStringtoHelp());
        System.out.println(HelpCommand.toStringtoHelp());
        System.out.println(HistoryCommand.toStringtoHelp());
        System.out.println(InsertCommand.toStringtoHelp());
        System.out.println(RemoveKeyCommand.toStringtoHelp());
        System.out.println(ShowCommand.toStringtoHelp());
        System.out.println(UpdateCommand.toStringtoHelp());
        System.out.println(ClearCommand.toStringtoHelp());
        System.out.println(ReplaceIfLowerCommand.toStringtoHelp());
        System.out.println(ReplaceIfGreaterCommand.toStringtoHelp());
        System.out.println(MinByCoordinatesCommand.toStringtoHelp());
        System.out.println(MaxByTypeCommand.toStringtoHelp());
        System.out.println(PrintFieldDescendingColorCommand.toStringtoHelp());
        System.out.println(SaveCommand.toStringtoHelp());
        System.out.println(ExecuteScriptCommand.toStringtoHelp());
        System.out.println(ExitCommand.toStringtoHelp());*/
    }
    /*System.out.println("help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции\n" +
            "insert : добавить новый элемент\n" +
            "update {id} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_key {id} : удалить элемент из коллекции по его ключу\n" +
            "clear : очистить коллекцию\n" +
            "save : сохранить коллекцию в файл\n" +
            "execute_script {file_name} : считать и исполнить скрипт из указанного файла\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "history : вывести последние 10 команд (без их аргументов)\n" +
            "replace_if_greater null {number} : заменить значение по ключу, если новое значение больше старого\n" +
            "replace_if_lower null {number} : заменить значение по ключу, если новое значение меньше старого\n" +
            "min_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является минимальным\n" +
            "max_by_type : вывести любой объект из коллекции, значение поля type которого является максимальным\n" +
            "print_field_descending_color : вывести значения поля color всех элементов в порядке убывания");*/
}
