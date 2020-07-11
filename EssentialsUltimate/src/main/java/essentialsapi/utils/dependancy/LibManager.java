package essentialsapi.utils.dependancy;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

public class LibManager {

    public static void loadLibrary(Plugin plugin, Dependency dependency){
        try {
            File libFolder = new File(plugin.getDataFolder() + File.separator + "Librarys");
            if (!libFolder.exists()){
                libFolder.mkdir();
            }
            File jarFile = new File(libFolder + File.separator + dependency.jarName + ".jar");
            if (!jarFile.exists()){
                plugin.getLogger().info("Downloading library " + dependency.jarName + " from " + dependency.jarURL);
                FileUtils.copyURLToFile(new URL(dependency.jarURL), jarFile, 10000, 10000);
                if (!jarFile.exists() || jarFile.getTotalSpace() == 0){
                    plugin.getLogger().info("Failed to download " + dependency.jarName + " from " + dependency.jarURL);
                    return;
                } else {
                    plugin.getLogger().info(dependency.jarName + " download succesfull!");
                }
            }
            URL jarURL = jarFile.toURI().toURL();
            URLClassLoader urlClassLoader = (URLClassLoader) getClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(urlClassLoader, jarURL);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}