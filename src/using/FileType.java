package using;

public enum FileType {
    MEDICINEFILE("Medicine_List"),
    STAFFFILE("Staff_List"),
    PATIENTFILE("Patient_List");

    private final String fileName;

    private FileType(String fileName) { this.fileName = fileName; }
    public String getFileName() { return fileName; }

}
