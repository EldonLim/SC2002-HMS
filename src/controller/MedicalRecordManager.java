package controller;

import database.DataBase;
import model.MedicalRecord;

public class MedicalRecordManager {

    public MedicalRecordManager() {}

    public static MedicalRecord getMedicalRecord(String patientID) { return DataBase.MedicalRecords.get(patientID);}

}
