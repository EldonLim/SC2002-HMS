package database;
import helper.Encryption;
import helper.Helper;
import using.*;
import model.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

    private static final String folderPath = "./src/database/data/";
    private static final String fileExtension = ".csv";

    private static HashMap<String, User> Users = new HashMap<String, User>();
    private static HashMap<String, Medicine> Medicines = new HashMap<>();

    private static String currUserID;

    private static int numberOfPatient = 0;
    private static int numberofDoctor = 0;
    private static int numberofAdminstrator = 0;
    private static int numberOfPharmacist = 0;

    public DataBase() {
        // Checking whether is it the first time run the program or not
//        boolean firstBoot = !(new File(folderPath + FileType.SCHEDULEFILE.getFileName() + fileExtension).exists());
        boolean firstBoot = !(new File(folderPath + "ScheduleTesting" + fileExtension).exists()); // TESTING

        if (!readPatientCSVFile(FileType.PATIENTFILE))
            System.err.println("Fail to read" + FileType.PATIENTFILE.getFileName());
        if (!readStaffCSVFile(FileType.STAFFFILE))
            System.err.println("Fail to read" + FileType.STAFFFILE.getFileName());
        if (!readMedicineFile(FileType.MEDICINEFILE))
            System.err.println("Fail to read" + FileType.MEDICINEFILE.getFileName());

        if (!firstBoot) {
            if (!readScheduleCSVFile(FileType.SCHEDULEFILE))
                System.err.println("Fail to read" + FileType.SCHEDULEFILE.getFileName());
            if (!readAppointmentCSVFile(FileType.APPOINTMENTFILE))
                System.err.println("Fail to read" + FileType.APPOINTMENTFILE.getFileName());
        }
    }

    public static void writeData() {
        if (!writePatientCSVFile())
            System.err.println("Fail To Write Patient Data");
        if (!writeMedicineCSVFile())
            System.err.println("Fail To Write Medicine Data");
        if (!writeStaffCSVFile())
            System.err.println("Fail To Write Staff Data");
        if (!writeScheduleCSVFile())
            System.err.println("Fail to Write Schedule Data");
        if (!writeAppointmentCSVFile())
            System.err.println("Fail to Write Appointment Data");
    }

    public static boolean readMedicineFile(FileType fileType) {
//        String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "MedicineTesting" + fileExtension;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);

                String medicineName = inputData.getFirst();
                Medicine medicine = new Medicine(medicineName, Integer.parseInt(inputData.get(1)), Integer.parseInt(inputData.get(2)), inputData.get(3).equals("1"), inputData.get(4).equals("1"));
                Medicines.put(medicineName, medicine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean readStaffCSVFile(FileType fileType) {
//        String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "StaffTesting" + fileExtension; // Testing Write
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] headers = line.split(",");

            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);

                String staffID = inputData.get(0);
                Role role = Role.fromString(inputData.get(2));
                User user = null;

                switch (role) {
                    case ADMINISTRATOR:
                        user = new Adminstrator(inputData.get(1), staffID, inputData.get(5).isEmpty()? Encryption.encode("password") : inputData.get(5), role, Gender.fromString(inputData.get(3)), Integer.parseInt(inputData.get(4)));
                        numberofAdminstrator++;
                        break;

                    case DOCTOR:
                        user = new Doctor(inputData.get(1), staffID, inputData.get(5).isEmpty()? Encryption.encode("password") : inputData.get(5), role, Gender.fromString(inputData.get(3)), Integer.parseInt(inputData.get(4)));
                        numberofDoctor++;
                        break;

                    case PHARMACIST:
                        user = new Pharmacist(inputData.get(1), staffID, inputData.get(5).isEmpty()? Encryption.encode("password") : inputData.get(5), role, Gender.fromString(inputData.get(3)), Integer.parseInt(inputData.get(4)));
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
//        String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "PatientTesting" + fileExtension; // Testing csv file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the headers

            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);

                String patientID = inputData.getFirst();
                User user = new Patient(inputData.get(1), patientID, inputData.get(7).isEmpty()? Encryption.encode("password") : inputData.get(7), Role.PATIENT, Gender.fromString(inputData.get(3)),
                        BloodType.fromString(inputData.get(4)), inputData.get(6), inputData.get(5), inputData.get(2)
                , Helper.parseList(inputData.get(8)), Helper.parseList(inputData.get(9)));

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

    public static boolean readScheduleCSVFile(FileType fileType) {
//        String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "ScheduleTesting" + fileExtension; // TESTING

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                ((Doctor) Users.get(inputData.get(0))).getSchedule().getWeeklySlots().get(inputData.get(1)).put(Integer.parseInt(inputData.get(2)), Availability.fromString(inputData.get(3)));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean readAppointmentCSVFile(FileType fileType) {
//        String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "AppointmentTesting" + fileExtension; // TESTING

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip headers

            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                AppointmentStatus appointmentStatus = AppointmentStatus.fromString(inputData.get(1));
                Appointment appointment = new Appointment(
                        inputData.get(0),
                        (Doctor) Users.get(inputData.get(2)),
                        appointmentStatus,
                        (Patient) Users.get(inputData.get(0).substring(0, 5)),
                        inputData.get(0).substring(5, 7) + "/" + inputData.get(0).substring(7, 9) + "/" + inputData.get(0).substring(9, 11),
                        Integer.parseInt(inputData.get(0).substring(11))
                );

                if (appointmentStatus == AppointmentStatus.COMPLETED) {
                    AppointmentOutcome appointmentOutcome =  new AppointmentOutcome(
                            appointment.getDate(),
                            Service.fromString(inputData.get(3)),
                            inputData.get(4),
                            inputData.get(0),
                            inputData.get(5),
                            MedicationStatus.fromString(inputData.get(6))
                    );
                    appointment.setAppointmentOutcome(appointmentOutcome);
                    appointment.getPatient().getMedicalRecord().addAppointmentOutcomes(appointmentOutcome, appointment.getAppointmentID());
                    appointment.getDoctor().addPatient(appointment.getPatient());
                }

                appointment.getPatient().addAppointment(appointment);
                if (appointmentStatus == AppointmentStatus.PENDING || appointmentStatus == AppointmentStatus.CONFIRM ) {
                    appointment.getDoctor().addAppointment(appointment);
                    if (appointmentStatus == AppointmentStatus.CONFIRM)
                        appointment.getDoctor().addPatient(appointment.getPatient());
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean writeAppointmentCSVFile() {
//        String filePath = folderPath + FileType.APPOINTMENTFILE.getFileName() + fileExtension;
        String filePath = folderPath + "AppointmentTesting" + fileExtension; // Testing
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Appointment ID,Appointment Status,Doctor ID,Service,Consultation Notes,Medicine,MedicationStatus\n");
            for (User user : Users.values()) {
                if (!(user instanceof Patient)) continue;
                for (Appointment appointment : ((Patient) user).getAppointments()) {
                    AppointmentOutcome outcome = appointment.getAppointmentOutcome();
                    bw.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                            appointment.getAppointmentID(),
                            appointment.getAppointmentStatus().getLabel(),
                            appointment.getDoctor().getID(),
                            outcome != null ? outcome.getService().getLabel() : "",
                            outcome != null ? outcome.getConsultationNotes() : "",
                            outcome != null ? outcome.getMedicine() : "",
                            outcome != null ? outcome.getMedicationStatus().getLabel() : ""
                    ));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeScheduleCSVFile() {
//        String filePath = folderPath + FileType.SCHEDULEFILE.getFileName() + fileExtension;
        String filePath = folderPath + "ScheduleTesting" + fileExtension; // TESTING

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Doctor ID,Date,Time Slot,Availability\n");

            for (User user : Users.values()) {
                if (!(user instanceof Doctor))
                    continue;

                HashMap<String, HashMap<Integer, Availability>> doctorSchedule = ((Doctor) user).getSchedule().getWeeklySlots();

                for (Map.Entry<String, HashMap<Integer, Availability>> outerHash : doctorSchedule.entrySet())
                    for (Map.Entry<Integer, Availability> innerHash : outerHash.getValue().entrySet()) {
                       if (innerHash.getValue() != Availability.AVAILABLE)
                           bw.write(String.format("%s,%s,%s,%s\n",
                                   user.getID(),
                                   outerHash.getKey(),
                                   innerHash.getKey(),
                                   innerHash.getValue().getLabel()
                           ));
                    }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writePatientCSVFile() {
        // String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "PatientTesting" + fileExtension; // Testing CSV file

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write headers
            bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Phone No,Password,Treatments,Diagnosis\n");

            for (User user : Users.values()) {
                if (!(user instanceof Patient)) continue;

                Patient patient = (Patient) user;

                // Convert lists to comma-separated strings
                String treatments = String.join(",", patient.getMedicalRecord().getTreatments());
                String diagnosis = String.join(",", patient.getMedicalRecord().getDiagnosis());

                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",\"%s\"\n",
                        patient.getID(),
                        patient.getName(),
                        patient.getDateOfBirth(),
                        patient.getGender().getLabel(),
                        patient.getBloodType().getLabel(),
                        patient.getEmailAddress(),
                        patient.getPhoneNo(),
                        patient.getPassword(),
                        treatments,
                        diagnosis
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeMedicineCSVFile() {
        String filePath = folderPath + "MedicineTesting" + fileExtension;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write headers
            bw.write("Medicine Name,Initial Stock,Low Stock Level Alert,Low Stock Alert,Request Add Stock\n");

            for (Medicine medicine : Medicines.values()) {

                bw.write(String.format("%s,%d,%d,%d,%d\n",
                        medicine.getMedicineName(),
                        medicine.getStock(),
                        medicine.getLowStockThreshold(),
                        medicine.getLowStockAlert()? 1 : 0,
                        medicine.getRequestAddStock()? 1 : 0
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeStaffCSVFile() {
//        String filePath = folderPath + fileType.getFileName() + fileExtension;
        String filePath = folderPath + "StaffTesting" + fileExtension; // Testing Write

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write headers
            bw.write("Staff ID,Name,Role,Gender,Age,Password\n");

            for (User user : Users.values()) {
                if (user instanceof Patient) continue;

                bw.write(String.format("%s,%s,%s,%s,%d,%s\n",
                        user.getID(),
                        user.getName(),
                        user.getRole().getLabel(),
                        user.getGender().getLabel(),
                        ((Staff) user).getAge(),
                        user.getPassword()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
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