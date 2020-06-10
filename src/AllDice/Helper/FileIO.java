package AllDice.Helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileIO {

    public static void serializeToFile(String filename, Object object) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(object);

            writeAllText(filename, json);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static <T>T deserializeFromFile(String filename, Class<T> classOfT)
    {
        try {
            String json = readAllText(filename);
            Gson gson = new Gson();
            return gson.fromJson(json, classOfT);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static void writeAllLines(String filename, ArrayList<String> lines){
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            Files.write(path, lines);
        } catch (Exception ex) {
            System.out.println("Error in FileIO.writeAllLines: " + ex);
        }
    }

    public static void writeAllText(String filename, String text){
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            FileWriter writer = new FileWriter(path.toString());
            writer.write(text);
            writer.close();
        } catch (Exception ex) {
            System.out.println("Error in FileIO.writeAllText: " + ex);
        }
    }
    public static String readAllText(String filename) {
        String text = "";
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            if (Files.exists(path)){
                text = String.join(" ", Files.readAllLines(path));
            }
        } catch (Exception ex) {
            System.out.println("Error in FileIO.readAllText: " + ex);
        }

        return text;
    }


    public static ArrayList<String> readAllLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            if (Files.exists(path)){
                lines = (ArrayList<String>) Files.readAllLines(path);
            }
        } catch (Exception ex) {
            System.out.println("Error in FileIO.readFileAsList: " + ex);
        }

        return lines;
    }

    public static void deleteFile(String filename) {
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            if (Files.exists(path)){
                Files.delete(path);
            }
        } catch (Exception ex) {
            System.out.println("Error in FileIO.deleteFile: " + ex);
        }
    }

    public static void appendAllLinesToFile(String filename, ArrayList<String> configBlanc) {
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            Files.write(path, configBlanc);
        } catch (Exception ex) {
            System.out.println("Error in FileIO.appendAllLinesToFile: " + ex);
        }
    }

    public static Path getFilePath(String filename){
        Path path = null;
        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }
        } catch (Exception ex) {
            System.out.println("Error in FileIO.getFilePath: " + ex);
        }

        return path;
    }
}
