package database;
import helper.Encryption;
import helper.Helper;
import using.*;
import model.*;

import java.io.*;
import java.lang.reflect.GenericDeclaration;
import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleConsumer;

public class DataBase {

    private static final String folderPath = "./src/database/data/";
    private static final String fileExtension = ".csv";

    private static HashMap<String, User> Users = new HashMap<String, User>();
//    private static HashMap<String, MedicalRecord> MedicalRecords = new HashMap<>();
    private static HashMap<String, Medicine> Medicines = new HashMap<>();

    private static String currUserID;

    private static int numberOfPatient = 0;
    private static int numberofDoctor = 0;
    private static int numberofAdminstrator = 0;
    private static int numberOfPharmacist = 0;

    public DataBase() {
        if (!readPatientCSVFile(FileType.PATIENTFILE))
            System.err.println("Fail to read" + FileType.PATIENTFILE.getFileName());
        if (!readStaffCSVFile(FileType.STAFFFILE))
            System.err.println("Fail to read" + FileType.STAFFFILE.getFileName());
        if (!readMedicineFile(FileType.MEDICINEFILE))
            System.err.println("Fail to read" + FileType.MEDICINEFILE.getFileName());
    }

    public static boolean readMedicineFile(FileType fileType) {
        String filePath = folderPath + fileType.getFileName() + fileExtension;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] headers = line.split(",");

            boolean initialState = headers.length == 3;

            while ((line = br.readLine()) != null) {
                String[] inputData = line.split(",");

                String medicineName = inputData[0];
                Medicine medicine = null;

                if (initialState)
                    medicine = new Medicine(medicineName, Integer.parseInt(inputData[1]), Integer.parseInt(inputData[2]));
                else
                    medicine = new Medicine(medicineName, Integer.parseInt(inputData[1]), Integer.parseInt(inputData[2]), inputData[3].equals("True"), inputData[4].equals("True"));

                Medicines.put(medicineName, medicine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean readStaffCSVFile(FileType fileType) {
        String filePath = folderPath + fileType.getFileName() + fileExtension;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] headers = line.split(",");

            boolean initialState = headers.length == 5;

            while ((line = br.readLine()) != null) {
                String[] inputData = line.split(",");

                String staffID = inputData[0];
                Role role = Role.fromString(inputData[2]);
                User user = null;

                switch (role) {
                    case ADMINISTRATOR:
                        user = new Adminstrator(inputData[1], staffID, initialState? Encryption.encode("password") : inputData[5], role, Gender.fromString(inputData[3]), Integer.parseInt(inputData[4]));
                        numberofAdminstrator++;
                        break;

                    case DOCTOR:
                        user = new Doctor(inputData[1], staffID, initialState? Encryption.encode("password") : inputData[5], role, Gender.fromString(inputData[3]), Integer.parseInt(inputData[4]));
                        numberofDoctor++;
                        break;

                    case PHARMACIST:
                        user = new Pharmacist(inputData[1], staffID, initialState? Encryption.encode("password") : inputData[5], role, Gender.fromString(inputData[3]), Integer.parseInt(inputData[4]));
                        numberOfPharmacist++;
                        break;
                }

                Users.put(staffID, user);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean readPatientCSVFile(FileType fileType) {
        String filePath = folderPath + fileType.getFileName() + fileExtension;
//        String filePath = folderPath + "Patient_List1" + fileExtension;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the headers

            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);

                String patientID = inputData.getFirst();
                User user = new Patient(inputData.get(1), patientID, inputData.get(7).isEmpty()? Encryption.encode("password") : inputData.get(7), Role.PATIENT, Gender.fromString(inputData.get(3)), BloodType.fromString(inputData.get(4)), inputData.get(6), inputData.get(5), inputData.get(2));

                numberOfPatient++;
                Users.put(patientID, user);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public static void writePatientCSVFile() {
//        // String filePath = folderPath + fileType.getFileName() + fileExtension;
//        String filePath = folderPath + "Patient_List1" + fileExtension;
//
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//            // Write headers
//            bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Phone No,Password,Treatments,Diagnosis\n");
//
//            for (User user : Users.values()) {
//                if (!(user instanceof Patient))
//                    continue;
//
//                Patient patient = (Patient) user;
//
//                // Convert lists to comma-separated strings
//                String treatments = String.join(",", patient.getMedicalRecord().getTreatments());
//                String diagnosis = String.join(",", patient.getMedicalRecord().getDiagnosis());
//
//                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",\"%s\"\n",
//                        patient.getID(),
//                        patient.getName(),
//                        patient.getDateOfBirth(),
//                        patient.getGender(),
//                        patient.getBloodType(),
//                        patient.getEmailAddress(),
//                        patient.getPhoneNo(),
//                        patient.getPassword(),
//                        treatments,
//                        diagnosis
//                ));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static int getNumberOfPatient() { return numberOfPatient; }
    public static int getNumberofDoctor() { return numberofDoctor; }
    public static int getNumberofAdminstrator() { return numberofAdminstrator; }
    public static int getNumberOfPharmacist() { return numberOfPharmacist; }
    public static String getCurrUserID() { return currUserID; }
    public static void setCurrUserID(String currUserID_) { currUserID = currUserID_; }

//    public static HashMap<String, MedicalRecord> getMedicalRecords() { return MedicalRecords; }
    public static HashMap<String, User> getUsers() { return Users; }
    public static HashMap<String, Medicine> getMedicines() { return Medicines; }

    public static void increaseDoctorCount() { numberofDoctor++; }
    public static void increasePharmacistCount() { numberOfPharmacist++; }
    public static void increaseAdminstratorCount() { numberofAdminstrator++; }
    public static void increasePatientCount() { numberOfPatient++; }
    public static void decreaseDoctorCount() { numberofDoctor--; }
    public static void decreasePharmacistCount() { numberOfPatient--; }
    public static void decreaseAdminstratorCount() { numberofAdminstrator--; }
    public static void decreasePatientCount() { numberofDoctor--; }
}