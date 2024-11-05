package database;

import model.Medicine;
import model.User;
import using.Role;

import java.util.HashMap;

public class DataBase {

    private static UserDAO userDAO;
    private static MedicineDAO medicineDAO;
    private static ScheduleDAO scheduleDAO;
    private static AppointmentDAO appointmentDAO;

    public DataBase() {
        userDAO = new UserDAO();
        medicineDAO = new MedicineDAO();
        scheduleDAO = new ScheduleDAO(userDAO);
        appointmentDAO = new AppointmentDAO();

        initializeData();
    }

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

    public static void writeData() {
        UserDAO.writePatientData();
        MedicineDAO.writeMedicineData();
        UserDAO.writeStaffData();
        scheduleDAO.writeScheduleData();
        AppointmentDAO.writeAppointmentData();
    }

    public static int getNumberOfPatients() {
        return UserDAO.getNumberOfPatients();
    }

    public static int getNumberOfDoctors() {
        return UserDAO.getNumberOfDoctors();
    }

    public static int getNumberOfAdminstrators() {
        return UserDAO.getNumberOfAdminstrators();
    }

    public static int getNumberOfPharmacists() {
        return UserDAO.getNumberOfPharmacists();
    }

    public static String getCurrentUserID() {
        return UserDAO.getCurrentUserID();
    }

    public static void setCurrentUserID(String currentUserID) {
        userDAO.setCurrentUserID(currentUserID);
    }

    public static HashMap<String, User> getUsers() {
        return UserDAO.getUsers();
    }

    public static HashMap<String, Medicine> getMedicines() {
        return MedicineDAO.getMedicines();
    }

    public static void increaseUserCount(Role role) {
        userDAO.increaseUserCount(role);
    }

    public static void decreaseUserCount(Role role) {
        userDAO.decreaseUserCount(role);
    }
}