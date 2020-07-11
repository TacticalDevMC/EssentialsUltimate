package essentialsapi.utils.handlers;


import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface Handlers {

    public String getName();

    public void execute(Player player);

    public Listener getListener();

}
