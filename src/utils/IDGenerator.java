package utils;

import java.util.Random;

public final class IDGenerator {
    private IDGenerator() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static Random random = new Random();

    public static String generateID() {
        return String.valueOf(random.nextInt(999999));
    }
}
