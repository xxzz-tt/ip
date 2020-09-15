import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private File file;
    private Path filepath;

    /**
     * Constructor for Storage.
     * @param filepath
     */
    public Storage(String filepath) {
        this.filepath = Paths.get(filepath);
        this.file = this.filepath.toAbsolutePath().toFile();

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * Load all the tasks from the files.
     * @return an arraylist of all the tasks
     * @throws FileNotFoundException
     */
    public ArrayList<Task> loadData() throws FileNotFoundException, IncorrectInputException {
        try (BufferedReader reader = Files.newBufferedReader(this.filepath)) {
            ArrayList<Task> list = new ArrayList<>();
            String data;
            reader.readLine();
            while ((data = reader.readLine()) != null) {
//                String data = reader.nextLine();
                boolean isDone = data.contains("| 1 |") ? true : false;
                String[] arr = data.split(" \\| ", 4);
                String description = arr[2];
                if (data.startsWith("T")) {
                    ToDo toDo = new ToDo(description);
                    if (isDone) {
                        toDo.markAsDone();
                    }
                    list.add(toDo);
                } else if (data.startsWith("D")) {
                    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                    DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(arr[3], outputFormat);
                    dateTime.format(inputFormat);
                    Deadline deadline = new Deadline(description, dateTime);
                    if (isDone) {
                        deadline.markAsDone();
                    }
                    list.add(deadline);
                } else if (data.startsWith("E")) {
                    Event event = new Event(description, arr[3]);
                    if (isDone) {
                        event.markAsDone();
                    }
                    list.add(event);
                }
            }
            return list;
        } catch (IOException e) {
            throw new IncorrectInputException("erm temp to change later");
        }
    }

    /**
     * Save new task to the file.
     * @param task
     * @throws IOException
     */
    public void saveTask(Task task) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(
                String.valueOf(filepath), true));
        file.newLine();
        file.write(task.toSave());
        file.close();
    }
}
