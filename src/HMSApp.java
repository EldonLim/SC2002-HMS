import database.DataBase;
import view.*;
import using.FileType;


public class HMSApp {
    public static void main(String[] args) {

        new DataBase();
        DataBase.readPatientCSVFile(FileType.PATIENTFILE);
        DataBase.readStaffCSVFile(FileType.STAFFFILE);
        DataBase.readMedicineCSVFile(FileType.MEDICINEFILE);
        HMSAppView hmsAppView = new HMSAppView();
    }


}