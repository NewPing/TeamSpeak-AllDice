package AllDice.Helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
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
            LogManager.log(ex.toString());
        }
    }

    public static <T>T deserializeFromFile(String filename, Class<T> classOfT)
    {
        try {
            String json = readAllText(filename);
            Gson gson = new Gson();
            return gson.fromJson(json, classOfT);
        } catch (Exception ex) {
            LogManager.log(ex.toString());
        }
        return null;
    }

    public static boolean exists(String subPath){
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + subPath).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + subPath).replace("\\", "/")));
            }

            return Files.exists(path);
        } catch (Exception ex) {
            LogManager.log("Error in FileIO.exists: " + ex);
            throw ex;
        }
    }

    public static void createDirectory(String dirPath){
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + dirPath).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + dirPath).replace("\\", "/")));
            }

            Files.createDirectory(path);
        } catch (Exception ex) {
            LogManager.log("Error in FileIO.createDirectory: " + ex);
        }
    }

    public static void fileMove(String filename, String newFilename){
        Path path = null;
        Path path2 = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path2 = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + newFilename).replace("\\", "/")));
            } else {
                path2 = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + newFilename).replace("\\", "/")));
            }

            Files.move(path, path2);
        } catch (Exception ex) {
            LogManager.log("Error in FileIO.fileMove: " + ex);
        }
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
            LogManager.log("Error in FileIO.writeAllLines: " + ex);
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
            LogManager.log("Error in FileIO.writeAllText: " + ex);
        }
    }

    public static void appendAllText(String filename, String text){
        Path path = null;

        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
                path = Paths.get(URI.create("file:/" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            } else {
                path = Paths.get(URI.create("file:" + (System.getProperty("user.dir") + "\\" + filename).replace("\\", "/")));
            }

            FileWriter writer = new FileWriter(path.toString(), true);
            writer.write(text + "\n");
            writer.close();
        } catch (Exception ex) {
            LogManager.log("Error in FileIO.appendAllText: " + ex);
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
            LogManager.log("Error in FileIO.readAllText: " + ex);
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
            LogManager.log("Error in FileIO.readAllLines: " + ex);
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
            LogManager.log("Error in FileIO.deleteFile: " + ex);
        }
    }

    public static void runJavaProcess(String filename) throws Exception {
        String path = null;

        try {
            ProcessBuilder proc = new ProcessBuilder("java -jar " + filename);

            Process process = proc.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //failure
            }

        } catch (Exception ex){
            throw ex;
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
            LogManager.log("Error in FileIO.getFilePath: " + ex);
        }

        return path;
    }
}
