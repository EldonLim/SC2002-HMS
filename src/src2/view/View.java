package src2.view;

/**
 * Represents a view in the Hospital Management System (HMS) application.
 * Defines common methods for displaying menus and handling interactions within the view.
 * 
 * @author Chew En Zee
 * @version 3.1
 * @since 2024-10-27
 */
public interface View {

    /**
     * Prints the main menu for the view.
     * Implementations of this method should display options specific to the view.
     */
    void printViewMenu();

    /**
     * Handles user interactions and input for the view.
     * Implementations of this method should handle navigation and actions based on user choices.
     */
    void handleView();

    /**
     * Displays the title of the view.
     * Implementations of this method should display a title specific to the view.
     */
    void viewTitle();
}
