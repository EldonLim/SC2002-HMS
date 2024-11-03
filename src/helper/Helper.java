package helper;

import java.util.*;

public class Helper {

    private static final Scanner sc = new Scanner(System.in);

    public static String readString() {
        return sc.nextLine();
    }

    public static void pauseApplication() {
        System.out.println("\nPress <Enter> to continue......");
        try { System.in.read(); }
        catch (Exception e) {}
        clearInputBuffer(); // clear buffer
    }

    public static int readInt() {
        int userInput = sc.nextInt();
        clearInputBuffer(); // clear buffer
        return userInput;
    }

    public static char readChar() { return sc.nextLine().toLowerCase().charAt(0); }

    public static void clearInputBuffer() { sc.nextLine(); }
    
}
