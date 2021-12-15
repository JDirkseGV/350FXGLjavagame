package spacegame.app;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceManager {
    /**
     * Handles saving game score and loading
     * @param data
     * @param fileName
     * @throws Exception
     */
    public static void save(Serializable data, String fileName) throws Exception {
        /*
          Saves player data
         */

        // try to create ObjectOutputStream
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            // Write save data to file
            oos.writeObject(data);
        }
    }

    public static Object load(String fileName) throws Exception {
        /*
          Loads player data from file
         */
        // Attempts to open save data
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            // Loads save data
            return ois.readObject();
        }
    }



}
