package seedu.duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Duke {
    /**
     * Main entry-point for the Crypto1010 application.
     */
    public static void main(String[] args) {
        CryptoApp app = new CryptoApp();
        try {
            app.load();
        } catch (StorageException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Starting with empty in-memory data.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Crypto1010 started. Type 'help' to see available commands.");

        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error: Failed to read input.");
                break;
            }
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            try {
                boolean shouldExit = app.handleCommand(line);
                if (shouldExit) {
                    break;
                }
            } catch (CryptoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: Unexpected failure.");
            }
        }
    }
}
