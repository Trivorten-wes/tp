package seedu.duke.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wallet {
    private final String name;
    private final List<String> transactionHistory;

    public Wallet(String name) {
        this.name = Objects.requireNonNull(name).trim();
        this.transactionHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTransaction(String transactionEntry) {
        transactionHistory.add(Objects.requireNonNull(transactionEntry).trim());
    }

    public List<String> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
}
