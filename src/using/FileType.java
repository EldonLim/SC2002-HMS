package using;

public enum FileType {
    MEDICINEFILE("Medicine_List"),
    STAFFFILE("Staff_List"),
    PATIENTFILE("Patient_List"),
    SCHEDULEFILE("Schedule"),
    APPOINTMENTFILE("Appointment");

    private final String fileName;
    private final String directoryPath = "./src/database/data/";
    private final String fileExtension = ".csv";

    FileType(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return directoryPath + fileName + fileExtension;
    }
}
