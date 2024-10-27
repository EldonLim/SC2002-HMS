package database;
import using.*;
import model.*;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class DataBase {

    private static final String folderName = "data/";
    private static final String fileExtension = ".csv";
    public static HashMap<String, User> Users = new HashMap<String, User>();

    public DataBase() {
        if (!readCSVFile(FileType.PATIENTFILE))
            System.err.println("Fail to read" + FileType.PATIENTFILE.getFileName());
    }

    public boolean readCSVFile(FileType fileType) {
       String filePath = "./src/database/" + folderName + fileType.getFileName() + fileExtension;

       try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
           String line = br.readLine();
           String[] headers = line.split(",");
           boolean initialState = headers.length == 6;

           while ((line = br.readLine()) != null) {
               String[] inputData = line.split(",");

               String patientID = inputData[0];
               Patient patient = new Patient(inputData[1], patientID, initialState? "password" : inputData[6], Role.PATIENT, Gender.fromString(inputData[3]), BloodType.fromString(inputData[4]), initialState? "" : inputData[7], inputData[5]);

               Users.put(patientID, patient);
           }

       }
       catch (IOException e) {
           e.printStackTrace();
           return false;
       }
       return true;
    }
}