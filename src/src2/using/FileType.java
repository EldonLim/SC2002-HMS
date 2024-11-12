package src2.using;

/**
 * Enum representing different types of files used in the application.
 * Each file type has a corresponding file name, directory path, and file extension.
 * 
 * @author Chin Linn Sheng
 * @version 2.2
 * @since 2024-10-27
 */
public enum FileType {
    /**
     * File type representing the list of medicines.
     */
    MEDICINEFILE("Medicine_List"),
    
    /**
     * File type representing the list of staff members.
     */
    STAFFFILE("Staff_List"),
    
    /**
     * File type representing the list of patients.
     */
    PATIENTFILE("Patient_List"),
    
    /**
     * File type representing the schedule.
     */
    SCHEDULEFILE("Schedule"),
    
    /**
     * File type representing the list of appointments.
     */
    APPOINTMENTFILE("Appointment");

    /**
     * The base file name associated with the file type.
     */
    private final String fileName;

    /**
     * The directory path where files are stored.
     */
    private final String directoryPath = "./src/database/data/";

    /**
     * The file extension used for all files.
     */
    private final String fileExtension = ".csv";

    /**
     * Constructor for FileType enum.
     *
     * @param fileName The base file name for the file type.
     */
    FileType(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Retrieves the complete file path for the file type.
     *
     * @return The full file path, including directory path, file name, and file extension.
     */
    public String getFilePath() {
        return directoryPath + fileName + fileExtension;
    }
}
