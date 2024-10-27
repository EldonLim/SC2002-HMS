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
}
