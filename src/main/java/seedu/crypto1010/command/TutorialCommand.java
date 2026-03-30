package seedu.crypto1010.command;

import seedu.crypto1010.Crypto1010;
import seedu.crypto1010.Parser;
import seedu.crypto1010.exceptions.Crypto1010Exception;
import seedu.crypto1010.model.Blockchain;
import seedu.crypto1010.model.WalletManager;
import seedu.crypto1010.storage.WalletStorage;

import java.util.Scanner;

public class TutorialCommand extends Command {
    private static final String HELP_DESCRIPTION = """
            Format: help [c/COMMAND]
            Example: help c/list
            
            COMMAND is optional
            If no valid COMMAND is given: lists all the available commands
            If a valid COMMAND is given: displays details regarding that command
            """;

    String[] instructions = {
            "create w/alice",
            "create w/bob",
            "list",
            "keygen w/alice",
            "keygen w/bob",
            "list",
            "balance w/alice",
            "balance w/bob",
            "help c/send",
            "send w/bob amt/3 to/",
            "balance w/alice",
            "balance w/bob",
            "tutorial exit"
    };

    String[] tutorialMessages = {
            "First, let's start by creating a new wallet called \"alice\"",
            "Next, we let's create another wallet called \"bob\"",
            "Let's look at the wallets that we have created",
            "Notice that both wallets do not have addresses yet!\n" +
                    "Let's first generate a key pair for alice",
            "Now we do the same for bob",
            "Now let's list our wallets again\n" +
                    "Notice that both wallets have addresses now, we will use these addresses later",
            "Now let us see how much alice has in her wallet",
            "We do the same for bob",
            "Remember the amount of money that each wallet have before the transaction\n" +
                    "Before we send money, let's use the help command to learn how to send money",
            "Now we are ready to send money!\n" +
                    "Let's get bob to send 3 dollars to alice\n" +
                    "For the destination, remember to use the address of alice's wallet we obtained from earlier",
            "Now that the transaction is successful, let's check the balance of the wallets again starting with alice",
            "And now bob",
            "Notice how there was a fee deducted from bob's wallet in addition to the amount that he sent to alice\n" +
                    "We can also view the transaction that was just made",
            "Congrats! You made it to the end of the tutorial!\n" +
                    "You are now ready to start your own simulated crypto blockchain!"
    };


    private static final String ERROR_MESSAGE = "Please input the given command to continue\n" +
            "If you want to exit tutorial mode, type: tutorial exit";
    public TutorialCommand() {
        super(HELP_DESCRIPTION);
    }

    public void execute(String description, Blockchain blockchain) {
        Scanner in = new Scanner(System.in);
        WalletStorage walletStorage = new WalletStorage(Crypto1010.class);
        WalletManager walletManager = new WalletManager();
        Parser parser = new Parser(walletManager);

        int index = 0;

        while (true) {
            System.out.println("Enter the following command:");
            System.out.println(instructions[index]);
            String input = in.nextLine().strip();
            if (input.equals("tutorial exit")) {
                return;
            } else if (input.equals(instructions[index])) {
                Command c = parser.parse(input);
                String[] components = input.split("\\s+", 2);
                String descriptions = components.length > 1 ? components[1] : "";
                try {
                    c.execute(descriptions, blockchain);
                    index++;
                } catch (Crypto1010Exception e) {
                    System.out.println(ERROR_MESSAGE);
                }
            } else if (input.equals("exit")) {
                return;
            } else {
                System.out.println("That was not the given instruction");
                System.out.println("If you wish to exit type: exit()");
            }
        }
    }
}
