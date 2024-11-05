package database;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import model.Medicine;
import helper.*;
import using.*;

public class MedicineDAO {

    private static HashMap<String, Medicine> medicines;

    public MedicineDAO() { medicines = new HashMap<>();}

    public static void readMedicineData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.MEDICINEFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                createMedicine(inputData);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMedicine(List<String> inputData) {
        String medicineName = inputData.get(0);
        Medicine medicine = new Medicine(
                medicineName, Integer.parseInt(inputData.get(1)), Integer.parseInt(inputData.get(2)),
                inputData.get(3).equals("1"), inputData.get(4).equals("1")
        );
        medicines.put(medicineName, medicine);
    }

    public static void writeMedicineData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.MEDICINEFILE.getFilePath()))) {
            bw.write("Medicine Name,Initial Stock,Low Stock Level Alert,Low Stock Alert,Request Add Stock\n");
            for (Medicine medicine : medicines.values()) {
                bw.write(formatMedicineData(medicine));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatMedicineData(Medicine medicine) {
        return String.format("%s,%d,%d,%d,%d\n",
                medicine.getMedicineName(), medicine.getStock(),
                medicine.getLowStockThreshold(), medicine.getLowStockAlert() ? 1 : 0,
                medicine.getRequestAddStock() ? 1 : 0);
    }

    public static HashMap<String, Medicine> getMedicines() { return medicines; }
}