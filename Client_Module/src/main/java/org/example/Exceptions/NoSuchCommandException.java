package org.example.Exceptions;
/** Exception that is thrown in case entered command's name is invalid
 */
public class NoSuchCommandException extends RuntimeException {
    /** Constructor, that shows the message in case of exception (command's name is invalid)
     */
    public NoSuchCommandException()
    {
        super("There is no such command. Please try again.");
    }
}
