package seedu.duke.model;

import java.util.Objects;

public class Wallet {
    private final String name;
    private Key publicKey;
    private Key privateKey;

    public Wallet(String name) {
        this.name = Objects.requireNonNull(name).trim();
    }

    public String getName() {
        return name;
    }

    public void setKeys(Key[] keys) {
        this.publicKey = keys[0];
        this.privateKey = keys[1];
    }
}
