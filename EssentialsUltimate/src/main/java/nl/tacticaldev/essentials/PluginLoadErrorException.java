package nl.tacticaldev.essentials;

public class PluginLoadErrorException extends Exception {

    public PluginLoadErrorException(final String message) {
        super(message);
    }

    public PluginLoadErrorException() {
        super();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
