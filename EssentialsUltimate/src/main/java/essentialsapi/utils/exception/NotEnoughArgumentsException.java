package essentialsapi.utils.exception;

public class NotEnoughArgumentsException extends Exception {

    public NotEnoughArgumentsException() {
        super("");
    }

    public NotEnoughArgumentsException(final String string) {
        super(string);
    }

    public NotEnoughArgumentsException(final Throwable ex) {
        super("", ex);
    }
}