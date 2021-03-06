package Command;

import java.io.IOException;
import java.util.ArrayList;

import Duke.Storage;
import Duke.Ui;
import Friend.FriendList;
import Tasks.Task;
import Tasks.TaskList;

public class FindCommand extends Command {

    private String description;

    /**
     * Constructor for the class.
     * @param description
     */
    public FindCommand(String description) {
        this.description = description;
    }

    /**
     * Execute the command.
     * @param tasks
     * @param ui
     * @param storage
     * @return a String of message.
     * @throws IOException
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task t : tasks.getList()) {
            if (t.getName().contains(description)) {
                foundTasks.add(t);
            }
        }

        return ui.generateList(new TaskList(foundTasks));
    }

    /**
     * Not reference to
     * @param friends
     * @param ui
     * @return null
     * @throws IOException
     */
    @Override
    public String execute(FriendList friends, Ui ui) throws IOException {
        return null;
    }

    /**
     * Check if the command is an exit command.
     * @return false as it is not.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Check if the command is a friend command
     * @return false
     */
    public boolean isFriendCommand() {
        return false;
    }
}
