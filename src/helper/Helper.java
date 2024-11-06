package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The Helper class provides utility methods for reading user input, 
 * parsing CSV-formatted strings, and other helper functions.
 * 
 * @author Chin Linn Sheng
 * @author Goh Jun Keat
 * @version 7.5
 * @since 2024-10-27
 */
public class Helper {

    /*
     * A Scanner instance used to read user input from the console.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Reads a line of text input from the user.
     * @return The user's input as a String.
     */
    public static String readString() {
        return sc.nextLine();
    }

    /**
     * Pauses the application and waits for the user to press Enter to continue.
     */
    public static void pauseApplication() {
        System.out.println("\nPress <Enter> to continue......");
        sc.nextLine();
    }

    /**
     * Reads an integer input from the user. 
     * If there are any leftover characters in the input buffer, they are cleared.
     * @return The integer entered by the user.
     */
    public static int readInt() {
        int userInput = sc.nextInt();
        clearInputBuffer();
        return userInput;
    }

    /**
     * Reads a single character input from the user and converts it to lowercase.
     * @return The first character of the user's input, in lowercase.
     */
    public static char readChar() {
        return sc.nextLine().toLowerCase().charAt(0);
    }

    /**
     * Clears any remaining input in the input buffer.
     */
    public static void clearInputBuffer() {
        sc.nextLine();
    }

    /**
     * Parses a line of CSV data, handling quoted fields with commas.
     * @param line A line of CSV-formatted text.
     * @return A list of fields as strings, separated by commas in the original input.
     */
    public static List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') inQuotes = !inQuotes; // Toggle quotes flag
            else if (c == ',' && !inQuotes) {
                result.add(currentField.toString().trim());
                currentField.setLength(0); // Reset the field buffer
            } else currentField.append(c);
        }
        result.add(currentField.toString().trim());
        return result;
    }

    /**
     * Parses a quoted, comma-separated string field into a list of strings.
     * @param field A string field containing items separated by commas, possibly quoted.
     * @return A list of strings parsed from the input field.
     */
    public static List<String> parseList(String field) {
        if (field.isEmpty() || field.equals("\"\"")) 
            return new ArrayList<>(); // Return empty list

        return Arrays.asList(field.replace("\"", "").split(","));
    }
}
