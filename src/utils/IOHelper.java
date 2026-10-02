package utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Helper class for handling Input/Output operations in the console.
 */
public final class IOHelper {
    private IOHelper() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine();
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Reads a date from the user input in the format yyyy-MM-dd.
     * 
     * @param prompt The prompt message to display to the user.
     * @return The parsed date input.
     */
    public static LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Input tidak valid. Harap masukkan tanggal dalam format yyyy-MM-dd.");
            }
        }
    }

    /**
     * Reads a date from the user input in the specified format.
     * 
     * @param prompt     The prompt message to display to the user.
     * @param dateFormat The expected date format (e.g., "dd/MM/yyyy").
     * @return The parsed date input.
     * 
     * @throws IllegalArgumentException if the provided dateFormat is invalid.
     */
    public static LocalDate readDate(String prompt, String dateFormat) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Input tidak valid. Harap masukkan tanggal dalam format " + dateFormat + ".");
            }
        }
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void printDivider() {
        System.out.println("=".repeat(80));
    }

    public static void printDivider(String divider) {
        System.out.println(divider.repeat(80));
    }

    public static void printCurrency(double amount) {
        System.out.printf("Rp. %,.2f", amount);
    }

    /**
     * Prints a ZonedDateTime in the format "dd MMM yyyy-HH:mm (z)".
     *
     * @param dateTime The ZonedDateTime to be printed.
     */
    public static void printZonedDateTime(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy-HH:mm (z)");

        System.out.print(dateTime.format(formatter));
    }

    /**
     * Prints a ZonedDateTime in the specified format.
     *
     * @param dateTime The ZonedDateTime to be printed.
     * @param format   The desired format for the output (e.g., "dd MMM yyyy HH:mm
     *                 (z)").
     * 
     * @throws IllegalArgumentException if the provided format is invalid.
     */
    public static void printZonedDateTime(ZonedDateTime dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        System.out.print(dateTime.format(formatter));
    }

    public static void printDuration(Duration duration) {
        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();

        System.out.printf("%d jam %d menit", hours, minutes);
    }
}