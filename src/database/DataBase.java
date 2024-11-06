package database;

import model.Medicine;
import model.User;
import using.Role;

import java.util.HashMap;

/**
 * Represents a database manager that handles the interaction with various DAO (Data Access Object) components,
 * such as user, medicine, schedule, and appointment data.
 * 
 * @author Chin Linn Sheng
 * @version 9.12
 * @since 2024-10-26
 */
public class DataBase {

    /**
     * The Data Access Object (DAO) responsible for managing user data.
     */
    private static UserDAO userDAO;

    /**
     * The Data Access Object (DAO) responsible for managing medicine data.
     */
    private static MedicineDAO medicineDAO;

    /**
     * The Data Access Object (DAO) responsible for managing schedule data,
     * which depends on the userDAO for user-specific data.
     */
    private static ScheduleDAO scheduleDAO;

    /**
     * The Data Access Object (DAO) responsible for managing appointment data.
     */
    private static AppointmentDAO appointmentDAO;

    /**
     * Constructs the DataBase object and initializes the necessary DAOs.
     * Also initializes the data if it's the first time the system is being booted.
     */
    public DataBase() {
        userDAO = new UserDAO();
        medicineDAO = new MedicineDAO();
        scheduleDAO = new ScheduleDAO(userDAO);
        appointmentDAO = new AppointmentDAO();

        initializeData();
    }

    /**
     * Initializes the data by reading the data files and loading the necessary information.
     * If it's the first time the application is running, it will set up initial data.
     */
    private static void initializeData() {
        boolean firstBoot = !(scheduleDAO.getScheduleFile().exists());

        userDAO.readPatientData();
        UserDAO.readStaffData();
        MedicineDAO.readMedicineData();

        if (!firstBoot) {
            scheduleDAO.readScheduleData();
            AppointmentDAO.readAppointmentData();
        }
    }

    /**
     * Writes the current data of users, medicines, schedules, and appointments back to their respective data files.
     */
    public static void writeData() {
        UserDAO.writePatientData();
        MedicineDAO.writeMedicineData();
        UserDAO.writeStaffData();
        scheduleDAO.writeScheduleData();
        AppointmentDAO.writeAppointmentData();
    }

    /**
     * Gets the total number of patients stored in the user database.
     * 
     * @return the number of patients
     */
    public static int getNumberOfPatients() {
        return UserDAO.getNumberOfPatients();
    }

    /**
     * Gets the total number of doctors stored in the user database.
     * 
     * @return the number of doctors
     */
    public static int getNumberOfDoctors() {
        return UserDAO.getNumberOfDoctors();
    }

    /**
     * Gets the total number of administrators stored in the user database.
     * 
     * @return the number of administrators
     */
    public static int getNumberOfAdminstrators() {
        return UserDAO.getNumberOfAdminstrators();
    }

    /**
     * Gets the total number of pharmacists stored in the user database.
     * 
     * @return the number of pharmacists
     */
    public static int getNumberOfPharmacists() {
        return UserDAO.getNumberOfPharmacists();
    }

    /**
     * Gets the ID of the currently logged-in user.
     * 
     * @return the current user ID
     */
    public static String getCurrentUserID() {
        return UserDAO.getCurrentUserID();
    }

    /**
     * Sets the ID of the current user.
     * 
     * @param currentUserID the ID of the user to set as the current user
     */
    public static void setCurrentUserID(String currentUserID) {
        userDAO.setCurrentUserID(currentUserID);
    }

    /**
     * Retrieves all users stored in the user database.
     * 
     * @return a HashMap containing user IDs as keys and User objects as values
     */
    public static HashMap<String, User> getUsers() {
        return UserDAO.getUsers();
    }

    /**
     * Retrieves all medicines stored in the medicine database.
     * 
     * @return a HashMap containing medicine IDs as keys and Medicine objects as values
     */
    public static HashMap<String, Medicine> getMedicines() {
        return MedicineDAO.getMedicines();
    }

    /**
     * Increases the count of users of a specific role in the user database.
     * 
     * @param role the role whose user count should be increased
     */
    public static void increaseUserCount(Role role) {
        userDAO.increaseUserCount(role);
    }

    /**
     * Decreases the count of users of a specific role in the user database.
     * 
     * @param role the role whose user count should be decreased
     */
    public static void decreaseUserCount(Role role) {
        userDAO.decreaseUserCount(role);
    }
}
