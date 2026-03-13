package seedu.duke.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.duke.model.Blockchain;
import seedu.duke.model.WalletManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class SendCommandTest {
    @Test
    void execute_validSend_printsSuccess() {
        Blockchain blockchain = Blockchain.createDefault();
        WalletManager walletManager = new WalletManager();
        walletManager.createWallet("bob"); // bob has balance 10 from default blockchain
        SendCommand command = new SendCommand("w/bob to/alice amt/5", walletManager);

        String output = runCommand(command, blockchain);

        String expected = "Send command validated successfully." + System.lineSeparator()
                + "Wallet: bob" + System.lineSeparator()
                + "To: alice" + System.lineSeparator()
                + "Amount: 5.0" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void execute_insufficientBalance_printsError() {
        Blockchain blockchain = Blockchain.createDefault();
        WalletManager walletManager = new WalletManager();
        walletManager.createWallet("alice"); // alice has balance -10
        SendCommand command = new SendCommand("w/alice to/bob amt/1", walletManager);

        String output = runCommand(command, blockchain);

        assertEquals("Error: Insufficient balance." + System.lineSeparator(), output);
    }

    @Test
    void execute_walletNotFound_printsError() {
        Blockchain blockchain = Blockchain.createDefault();
        WalletManager walletManager = new WalletManager();
        SendCommand command = new SendCommand("w/nonexistent to/alice amt/1", walletManager);

        String output = runCommand(command, blockchain);

        assertEquals("Error: Wallet not found." + System.lineSeparator(), output);
    }

    @Test
    void execute_invalidAmount_printsError() {
        Blockchain blockchain = Blockchain.createDefault();
        WalletManager walletManager = new WalletManager();
        walletManager.createWallet("bob");
        SendCommand command = new SendCommand("w/bob to/alice amt/-5", walletManager);

        String output = runCommand(command, blockchain);

        assertEquals("Error: Amount must be a positive number." + System.lineSeparator(), output);
    }

    @Test
    void execute_invalidFormat_printsError() {
        Blockchain blockchain = Blockchain.createDefault();
        WalletManager walletManager = new WalletManager();
        SendCommand command = new SendCommand("invalid", walletManager);

        String output = runCommand(command, blockchain);

        assertEquals("Error: Invalid send format. Use: send w/WALLET to/ADDR amt/AMT" + System.lineSeparator(), output);
    }

    private String runCommand(Command command, Blockchain blockchain) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        try {
            command.execute(blockchain);
        } finally {
            System.setOut(originalOut);
        }
        return outputStream.toString();
    }
}


