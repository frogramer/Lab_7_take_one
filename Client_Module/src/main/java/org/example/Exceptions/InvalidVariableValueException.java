package org.example.Exceptions;
/** Exception that is thrown in case any entered or read value cant be applied to needed variable
 */
public class InvalidVariableValueException extends RuntimeException {
    /** Constructor, that shows the message in case of exception (entered or read value cant be applied to needed variable)
     */
    public InvalidVariableValueException() {
        super("Entered value is invalid. Please try again.");
    }
}
