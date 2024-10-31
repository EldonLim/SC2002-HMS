package helper;

import java.util.*;

public class Helper {

    private static final Scanner sc = new Scanner(System.in);

    public static String readString() {
        return sc.nextLine();
    }

    public static void pauseApplication() {
        System.out.println("Press <Enter> to continue......");
        try { System.in.read(); }
        catch (Exception e) {}
    }

    public static int readInt() {
        int userInput = sc.nextInt();
        sc.nextLine(); // clear buffer
        return userInput;
    }

    public static int readChar() {
        char userInput = sc.nextLine().toLowerCase().charAt(0);
        return userInput;
    }

    public static void clearInputBuffer() { sc.nextLine(); }
}
