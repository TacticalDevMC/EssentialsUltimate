package nl.tacticaldev.essentials.managers.warp.exceptions;

public class WarpNotFoundException extends Exception {

    public WarpNotFoundException() {
        super("Warp does not exists.");
    }

    public WarpNotFoundException(String message) {
        super(message);
    }
}