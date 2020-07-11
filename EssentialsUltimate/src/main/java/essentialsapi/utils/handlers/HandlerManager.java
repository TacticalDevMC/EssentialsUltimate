package essentialsapi.utils.handlers;

import essentialsapi.utils.logger.Logger;
import lombok.Getter;
import nl.tacticaldev.essentials.Essentials;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
@Getter
public class HandlerManager {

    private ArrayList<Handlers> handlers;

    private HandlerManager handlerManager;

    public HandlerManager() {
        handlerManager = this;
        handlers = new ArrayList<Handlers>();
    }

    public void addHandler(Handlers handler) {
        if (!handlers.contains(handler)) {
            handlers.add(handler);
            Logger.HANDLER.log("De handler " + handler.getName() + " is geladen!");
        }

        if (handler.getListener() == null) return;

        registerHandlerListeners(handler.getListener());
    }

    public void removeHandler(Handlers handler) {
        if (handlers.contains(handler)) {
            handlers.remove(handler);
            Logger.HANDLER.log("De handler " + handler.getName() + " is verwijderd!");
        }
    }

    public void reloadHandlers() {
        for (int i = 0; i > handlers.size(); i++) {
            if (!handlers.contains(i)) {
                this.handlers.remove(handlers.get(i));
            } else {
                this.handlers.add(handlers.get(i));
            }
            Logger.HANDLER.log("Alle handlers zijn succesvol herladen! Handlers size " + i + ", handler names " + handlers.get(i).getName() + ".");
        }
    }

    public boolean isHandlerLoaded(Handlers handler) {
        if (handlers.contains(handler)) {
            return true;
        }
        return false;
    }

    private static void registerHandlerListeners(Listener... listener) {
        Arrays.stream(listener).forEach(new Consumer<Listener>() {
            @Override
            public void accept(Listener l) {
                Essentials.getInstance().getServer().getPluginManager().registerEvents(l, Essentials.getInstance());
            }
        });
    }

}
