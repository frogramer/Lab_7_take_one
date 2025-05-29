package org.example.Exceptions;
/** Exception that is thrown in case entered or read id is already in use
 */
public class NotUniqueIDException extends RuntimeException {
    /** Constructor, that shows the message in case of exception (Dragon's id is already in use)
     */
    public NotUniqueIDException() {
        super("Dragon with this ID already exists");
    }
}
