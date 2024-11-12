package src;

import database.DataBase;
import view.HMSAppView;

/**
 * Entry point for the Hospital Management System (HMS) application.
 * Initializes the database and the main application view.
 *
 * @author Chin Linn Sheng
 * @version 5.5
 * @since 2024-10-26
 */
public class HMSApp {

    /**
     * Default constructor for the HMSApp class.
     */
    public HMSApp() {
    }

    /**
     * Main method that launches the HMS application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize the database
        new DataBase();

        // Initialize and display the main application view
        HMSAppView hmsAppView = new HMSAppView();
    }
}