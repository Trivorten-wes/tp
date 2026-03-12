package seedu.duke.command;

public class SendCommand extends Command {
    @Override
    public void execute(String description) {
        System.out.println("send command executed");
    }
}
