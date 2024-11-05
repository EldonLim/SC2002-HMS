package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Helper {

    private static final Scanner sc = new Scanner(System.in);

    public static String readString() {
        return sc.nextLine();
    }

    public static void pauseApplication() {
        System.out.println("\nPress <Enter> to continue......");
//        try { System.in.read(); }
//        catch (Exception e) {}
        sc.nextLine();
//        clearInputBuffer(); // clear buffer
    }

    public static int readInt() {
        int userInput = sc.nextInt();
        clearInputBuffer(); // clear buffer
        return userInput;
    }

    public static char readChar() {
        return sc.nextLine().toLowerCase().charAt(0);
    }

    public static void clearInputBuffer() {
        sc.nextLine();
    }

    public static List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') inQuotes = !inQuotes; // Toggle quotes flag
            else if (c == ',' && !inQuotes) {
                // Comma outside quotes ends the field
                result.add(currentField.toString().trim());
                currentField.setLength(0); // Reset the field buffer
            } else currentField.append(c);
        }
        // Add the last field
        result.add(currentField.toString().trim());
        return result;
    }

    public static List<String> parseList(String field) {
        if (field.isEmpty() || field.equals("\"\"")) return new ArrayList<>(); // Return empty list
        // Remove surrounding quotes and split by commas
        return Arrays.asList(field.replace("\"", "").split(","));
    }

}
