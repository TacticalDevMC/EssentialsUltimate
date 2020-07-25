package essentialsapi.utils.exception;

public class NoChargeException extends Exception {
    public NoChargeException() {
        super("Will charge later");
    }
}