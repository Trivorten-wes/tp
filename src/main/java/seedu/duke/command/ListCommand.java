package seedu.duke.command;

public class ListCommand extends Command {
    private static final String HELP_DESCRIPTION = "format: list\n" +
            "lists all the available wallets";

    public ListCommand() {
        super(HELP_DESCRIPTION);
    }

    @Override
    public void execute(String description) {
        System.out.println("list command executed");
    }
}
