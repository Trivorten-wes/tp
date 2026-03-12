package seedu.duke.command;

public abstract class Command {
    protected String helpDescription;
    public abstract void execute(String description);

    Command(String helpDescription) {
        this.helpDescription = helpDescription;
    }

    public void displayHelpDescription() {
        System.out.println(helpDescription);
    }
}
