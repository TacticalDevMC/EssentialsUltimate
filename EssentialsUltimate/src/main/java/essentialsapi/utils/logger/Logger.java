package essentialsapi.utils.logger;

import essentialsapi.utils.logger.Debugger;
import essentialsapi.utils.logger.Developer;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.sql.SQLException;

import static essentialsapi.utils.Utils.replaceColor;

public class Logger {

    public static final Logger DEBUG = new Debugger();
    public static final Logger DEVELOPER = new Developer();
    public static final Logger INFO = new Logger("[INFO]");
    public static final Logger ERROR = new Logger("[ERROR]");
    public static final Logger SAVING = new Logger("[SAVING]");
    public static final Logger WARNING = new Logger("[WARNING]");
    public static final Logger CRITICAL = new Logger("[CRITICAL]");
    public static final Logger BROADCAST = new Logger("[BROADCAST]");
    public static final Logger DATABASE = new Logger("[DATABASE]");
    public static final Logger FILE = new Logger("[FILE]");
    public static final Logger UTILS = new Logger("[UTILITY]");
    public static final Logger LISTENERS = new Logger("[LISTENERS]");
    public static final Logger HANDLER = new Logger("[HANDLER]");
    public static final Logger CONFIG = new Logger("[CONFIG]");
    public static final Logger MYSQL = new Logger("[MYSQL]");

    protected static String prefix;

    public Logger(String prefix) {
        this.prefix = prefix;
    }

    public void log(String message) {
        System.out.println("[EssentialsUltimate] " + prefix + " " + message);
    }

    public void sendConsoleLog(String message) {
        Bukkit.getConsoleSender().sendMessage(replaceColor("[EssentialsUltimate] " + prefix + " " + message));
    }

    public void log(IOException ex) {
        System.out.println("[EssentialsUltimate] " + prefix);
        Logger.WARNING.log("Discovered EssentialsUltimate Exception!");
        Logger.ERROR.log("---------------------------");
        Logger.ERROR.log("Exception: " + ex.toString());
        for (StackTraceElement s : ex.getStackTrace()) {
            Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
                    + "] [" + s.getFileName() + "]");
        }
        Logger.ERROR.log("---------------------------");
    }

    public void log(Exception ex) {
        System.out.println("[EssentialsUltimate] " + prefix);
        Logger.WARNING.log("Discovered EssentialsUltimate Exception!");
        Logger.ERROR.log("---------------------------");
        Logger.ERROR.log("Exception: " + ex.toString());
        for (StackTraceElement s : ex.getStackTrace()) {
            Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
                    + "] [" + s.getFileName() + "]");
        }
        Logger.ERROR.log("---------------------------");
    }

    public void log(SQLException ex) {
        System.out.println("[EssentialsUltimate] " + prefix);
        Logger.WARNING.log("Discovered EssentialsUltimate Exception!");
        Logger.ERROR.log("---------------------------");
        Logger.ERROR.log("Exception: " + ex.toString());
        for (StackTraceElement s : ex.getStackTrace()) {
            Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
                    + "] [" + s.getFileName() + "]");
        }
        Logger.ERROR.log("---------------------------");
    }
}
