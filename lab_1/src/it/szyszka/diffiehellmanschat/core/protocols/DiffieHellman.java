package it.szyszka.diffiehellmanschat.core.protocols;

import java.math.BigInteger;

public class DiffieHellman {

    // TODO: 11.10.17 read them from properties?!
    public static final int PRIMAL = 23;
    public static final int BASE = 5;

    public static BigInteger generateA(Integer userChoice) {
        BigInteger A = BigInteger.valueOf((long)Math.pow(BASE, userChoice));
        return A;
    }

}
