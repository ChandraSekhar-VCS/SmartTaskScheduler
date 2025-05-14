package persistance;

import model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskPersistenceService {
    private static final String FILE_PATH = "src/data/tasks.dat";

    public static void saveTasks(List<Task> tasks) {
        try{
            File file  = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(tasks);
            oos.close();
        }
        catch(IOException e){
            System.out.println("Failed to save Tasks: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static List<Task> loadTasks() {
        File file = new File(FILE_PATH);
        if(!file.exists()){
            return new ArrayList<>();
        }

        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            List<Task> tasks = (List<Task>) ois.readObject();
            ois.close();
            return tasks;
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println("Failed to load Tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
