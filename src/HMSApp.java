import database.DataBase;
import view.*;
import model.*;

import java.util.HashMap;

public class HMSApp {
    public static void main(String[] args) {
        printHomeView();

        new DataBase();
        HMSAppView hmsAppView = new HMSAppView();
        hmsAppView.loginView();
    }

    public static void printHomeView() {
        System.out.println("========================================");
        System.out.println("||                                    ||");
        System.out.println("||  HOSPITAL MANAGEMENT SYSTEM (HMS)  ||");
        System.out.println("||                                    ||");
        System.out.println("========================================");
    }
}