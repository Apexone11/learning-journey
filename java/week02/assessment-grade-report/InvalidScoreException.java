import  java.lang.*;

// TODO: a CHECKED custom exception with two constructors:
public class InvalidScoreException extends Exception {

    public InvalidScoreException(String message) {
        super(message);
    }

    public InvalidScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
