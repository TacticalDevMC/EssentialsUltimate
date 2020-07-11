package essentialsapi.utils.logger;


import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import essentialsapi.utils.logger.Logger;

public class Debugger extends Logger {

    public Debugger() {
        super("[DEBUG]");
    }

    @Override
    public void log(String message) {
        IEssentials ess = Essentials.getInstance();

        boolean shouldDebug = ess.getSettings().isDebug();

        if (shouldDebug) {
            System.out.println("[EssentialsUltimate] " + prefix + " " + message);
        }
    }

}
