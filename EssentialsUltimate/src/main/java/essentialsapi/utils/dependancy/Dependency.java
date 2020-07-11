package essentialsapi.utils.dependancy;

import lombok.Getter;

public enum Dependency {

    MONGO(
            "mongo-java-driver-3.9.1",
            "https://content.daangrave.nl/mongo-java-driver-3.9.1.jar"
    ),
    DISCORDJDA(
            "JDA-3.6.0_362-withDependencies",
            "https://content.daangrave.nl/JDA-3.6.0_362-withDependencies.jar"
    ),
    CRAFTBUKKIT(
            "craftbukkit-1.12.2",
            "https://getbukkit.org/get/V1F1tpQFW3asBTkOpDS4tVQGGovdZhGv"
    );

    public @Getter
    String jarName, jarURL;

    Dependency(String jarName, String jarURL){
        this.jarName = jarName;
        this.jarURL = jarURL;
    }

}