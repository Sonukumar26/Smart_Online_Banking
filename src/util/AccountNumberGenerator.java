package util;

import java.util.Random;

public class AccountNumberGenerator {

    public static long generateAccountNumber() {
        Random r = new Random();
        long number = 100000000000L + (long)(r.nextDouble() * 900000000000L);
        return number; 
    }
}
