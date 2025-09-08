package waguri;

/**
 * Represents a custom exception specific to the Waguri application.
 * This exception is thrown when application-specific errors occur that
 * require special handling beyond standard Java exceptions.
 *
 * @author Joseph Kwok
 */
public class WaguriException extends Exception {

    /**
     * Constructs a new WaguriException with the specified detail message.
     * The message should provide meaningful information about the error
     * that occurred within the Waguri application context.
     *
     * @param message the detail message that describes the reason for the exception.
     *
     */
    public WaguriException(String message) {
        super(message);
    }
}
