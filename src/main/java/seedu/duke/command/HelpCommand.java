package seedu.duke.command;

public class HelpCommand extends Command {
    private static final String HELP_DESCRIPTION = "format: help [COMMAND]\n" +
            "COMMAND is optional\n" +
            "If no COMMAND is given: lists all the available commands\n" +
            "If a COMMAND is given: displays details regarding that command";

    public HelpCommand() {
        super(HELP_DESCRIPTION);
    }

    @Override
    public void execute(String description) {
        for (CommandWord c : CommandWord.values()) {
            System.out.print("  ");
            System.out.print(c.getCommand());
            for (int i = 0; i < 12 - c.getCommand().length(); i++) {
                System.out.print(" ");
            }
            System.out.println(c.getDescription());
        }

        System.out.println("help command executed");
    }
}
