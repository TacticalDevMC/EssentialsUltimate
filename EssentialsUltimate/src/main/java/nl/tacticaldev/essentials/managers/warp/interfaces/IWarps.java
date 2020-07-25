package nl.tacticaldev.essentials.managers.warp.interfaces;

import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidNameException;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidWorldException;
import nl.tacticaldev.essentials.managers.warp.exceptions.WarpNotFoundException;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Location;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public interface IWarps extends IConf {

    /**
     * Get a warp by name
     *
     * @param warp - Warp name
     * @return - Location the warp is set to
     * @throws WarpNotFoundException When the warp is not found
     * @throws InvalidWorldException When the world the warp is in is not found
     */
    Location getWarp(String warp) throws WarpNotFoundException, InvalidWorldException;

    /**
     * Gets a list of warps
     *
     * @return - A {@link Collection} of warps
     */
    Collection<String> getList();

    /**
     * Gets the number of warps
     *
     * @return the size of the list of warps
     */
    int getCount();

    /**
     * Delete the warp from the warp DB
     *
     * @param name - Name of warp
     * @throws Exception
     */
    void removeWarp(String name) throws Exception;

    /**
     * Set a warp
     *
     * @param name - Name of warp
     * @param loc  - Location of warp
     * @throws Exception
     */
    void setWarp(String name, Location loc) throws Exception;

    /**
     * Set a warp
     *
     * @param base - User of warp
     * @param name - Name of warp
     * @param loc  - Location of warp
     * @throws Exception
     */
    void setWarp(EssentialsPlayer base, String name, Location loc) throws Exception;

    /**
     * Gets the Lastowner UUID
     *
     * @param warp - Name of warp
     * @return
     * @throws WarpNotFoundException
     */
    UUID getLastOwner(String warp) throws WarpNotFoundException;

    /**
     * Check to see if the file is empty
     *
     * @return
     */
    boolean isEmpty();

    /**
     * Get a warp file note: this is not het implemented, as 3.x uses different storage methods
     *
     * @param name - name of file
     * @return - an instance of the file
     * @throws InvalidNameException - When the file is not found
     */
    File getWarpFile(String name) throws InvalidNameException;
}
