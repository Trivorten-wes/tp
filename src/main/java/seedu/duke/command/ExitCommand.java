package seedu.duke.command;

public class ExitCommand extends Command {
    private static final String HELP_DESCRIPTION = "format: exit\n" +
            "Exits the program";

    public ExitCommand() {
        super(HELP_DESCRIPTION);
    }

    @Override
    public void execute() {
        System.out.println("exit command executed");
    }
}
