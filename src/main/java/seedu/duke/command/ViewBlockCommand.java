package seedu.duke.command;

public class ViewBlockCommand extends Command {
    @Override
    public void execute(String description) {
        System.out.println("view block command executed");
    }
}
