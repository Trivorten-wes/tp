package seedu.duke.model;

import java.math.BigInteger;
import java.security.SecureRandom;
import seedu.duke.exceptions.Exceptions;

public class Key {
    private static final int KEY_SIZE = 1024;
    private static final BigInteger PUBLIC_EXPONENT = BigInteger.valueOf(65537);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final BigInteger modulus;
    private final BigInteger exponent;
    private final boolean isPublic;
    private final int walletAddress;

    public Key(BigInteger modulus, BigInteger exponent, boolean isPublic) throws Exceptions {
        this.modulus = modulus;
        this.exponent = exponent;
        this.isPublic = isPublic;
        this.walletAddress = isPublic ? deriveAddress(modulus, exponent) : null;
    }

    private static int deriveAddress(BigInteger modulus, BigInteger exponent) {
        return Math.abs((modulus.add(exponent)).hashCode());
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getWalletAddress() {
        return walletAddress;
    }

    public static Key[] generateKeyPair() throws Exceptions {
        // Creates primes
        BigInteger primeP = BigInteger.probablePrime(KEY_SIZE, RANDOM);
        BigInteger primeQ;

        // Recreates primeQ until it is different from primeP
        do {
            primeQ = BigInteger.probablePrime(KEY_SIZE, RANDOM);
        } while (primeQ.equals(primeP));

        // Creates public modulus, Euler's totient, and private exponent for private key
        BigInteger modulus = primeP.multiply(primeQ);
        BigInteger totient = primeP.subtract(BigInteger.ONE).multiply(primeQ.subtract(BigInteger.ONE));
        BigInteger privateExponent = PUBLIC_EXPONENT.modInverse(totient);

        // Ensures math checks out
        BigInteger check = PUBLIC_EXPONENT.multiply(privateExponent).mod(totient);
        if (!check.equals(BigInteger.ONE)) {
            throw new Exceptions("Key pair verification failed.");
        }

        // Creates Keys for key pair
        Key publicKey = new Key(modulus, PUBLIC_EXPONENT, true);
        Key privateKey = new Key(modulus, privateExponent, false);
        return new Key[]{publicKey, privateKey};
    }
}
