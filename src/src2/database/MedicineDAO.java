package src2.database;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import src2.helper.*;
import src2.model.Medicine;
import src2.using.*;

/**
 * Data Access Object (DAO) for managing medicine data.
 * Provides methods for reading and writing medicine information to and from files, and
 * storing medicines in a hash map for access throughout the application.
 *
 * @author Eldon Lim Kai Jie
 * @author Goh Jun Keat
 * @version 2.1
 * @since 2024-11-06
 */
public class MedicineDAO {

    /**
     * A map of medicine names to Medicine objects, used to store and retrieve medicine data.
     */
    private static HashMap<String, Medicine> medicines;

    /**
     * Constructs a MedicineDAO instance and initializes the medicines map.
     */
    public MedicineDAO() { medicines = new HashMap<>(); }

    /**
     * Reads medicine data from a CSV file and loads it into the medicines map.
     */
    public static void readMedicineData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.MEDICINEFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                createMedicine(inputData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new Medicine object from parsed CSV input data and adds it to the medicines map.
     *
     * @param inputData A list of strings containing the medicine data fields.
     */
    private static void createMedicine(List<String> inputData) {
        String medicineName = inputData.get(0);
        Medicine medicine = new Medicine(
                medicineName, Integer.parseInt(inputData.get(1)), Integer.parseInt(inputData.get(2)),
                inputData.get(3).equals("1"), inputData.get(4).equals("1")
        );
        medicines.put(medicineName, medicine);
    }

    /**
     * Writes the current medicine data from the medicines map to a CSV file.
     */
    public static void writeMedicineData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.MEDICINEFILE.getFilePath()))) {
            bw.write("Medicine Name,Initial Stock,Low Stock Level Alert,Low Stock Alert,Request Add Stock\n");
            for (Medicine medicine : medicines.values()) {
                bw.write(formatMedicineData(medicine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats a Medicine object as a CSV line for file writing.
     *
     * @param medicine The Medicine object to format.
     * @return A formatted string representing the medicine data in CSV format.
     */
    private static String formatMedicineData(Medicine medicine) {
        return String.format("%s,%d,%d,%d,%d\n",
                medicine.getMedicineName(), medicine.getStock(),
                medicine.getLowStockThreshold(), medicine.getLowStockAlert() ? 1 : 0,
                medicine.getRequestAddStock() ? 1 : 0);
    }

    /**
     * Returns the medicines map containing all loaded medicines.
     *
     * @return A HashMap of medicine names to Medicine objects.
     */
    public static HashMap<String, Medicine> getMedicines() { return medicines; }
}
