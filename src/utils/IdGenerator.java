package utils;

import java.util.Random;

public final class IdGenerator {
    private IdGenerator() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static Random random = new Random();

    public static String generateId() {
        return String.valueOf(random.nextInt(999999));
    }
}
