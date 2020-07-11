package essentialsapi.utils.logger;

import essentialsapi.utils.logger.Logger;

public class Developer extends Logger {

    public Developer() {
        super("[DEVELOPER]");
    }

    @Override
    public void log(String message) {
        System.out.println("[EssentialsUltimate] " + prefix + " " + message);
    }

}
