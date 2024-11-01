package view;

import java.util.ArrayList;

import controller.AdministratorManager;
import controller.InventoryManager;
import database.DataBase;
import helper.Helper;
import model.Medicine;
import java.util.Map;
import model.User;
import using.Gender;
import using.Role;

public class AdminstratorView implements View{

    public AdminstratorView() {}

    @Override
    public void printViewMenu() {
        this.viewTitle();
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    public void handleView() {
        int choice;

        do {
            this.printViewMenu();
            choice = Helper.readInt();
            System.out.println();

        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                System.out.println("List of hospital staffs: ");
                listStaffs();
                addOrRemoveStaffs();
                System.out.println("Updated list of hospital staffs: ");
                listStaffs();
                break;
            case 2:
                
                break;
            case 3:
                listMedicine();
                replenishMedicine();
                System.out.println("Updated medicine list: ");
                listMedicine();
                break;
            case 4:
                viewReplenishmentRequest();
                break;
            case 5:

                break;
        
            default:
                break;
        }
    }

    @Override
    public void viewTitle() { System.out.println("Adminstrator Menu"); }

    public void listStaffs() {
        // filter out patient
        ArrayList<User> staffs = new ArrayList<>();
        for (Map.Entry<String, User> user: DataBase.Users.entrySet()){
            if (!(user.getValue().getRole() == Role.PATIENT)) {
                staffs.add(user.getValue());
            }
        }
        
        for (User staff: staffs){
            System.out.println("Staff Name: " + staff.getName() + ", Staff id: " + staff.getID());
        }
    }

    public void addOrRemoveStaffs() {
        System.out.println("Enter 1 to add staff, 2 to remove staff: ");
        int res = Helper.readInt();
        if (res == 1){
            System.out.println("Enter staff name: ");
            String name = Helper.readString();
            System.out.println("Enter staff id: ");
            String id = Helper.readString();
            System.out.println("Enter staff role (1 for Administrator, 2 for Pharmacist, 3 for Doctor): ");
            int roleInt = Helper.readInt();
            Role role;
            if (roleInt == 1) role = Role.ADMINISTRATOR;
            if (roleInt == 2) role = Role.PHARMACIST;
            else role = Role.DOCTOR;

            System.out.println("Enter staff gender (M/F): ");
            Character genderChar = Helper.readChar();
            Gender gender;
            if (genderChar.equals("M")) gender = Gender.MALE;
            else gender = Gender.FEMALE;

            System.out.println("Enter staff age: ");
            int age = Helper.readInt();

            AdministratorManager.addUser(name, id, "password", role, gender, age);
            System.out.println("Staff added!");
        } else {
            System.out.println("Enter staff id: ");
            String staffId = Helper.readString();
            AdministratorManager.removeUser(staffId);
            System.out.println("Staff has been removed!");
        }
    }
    
    public void listMedicine(){
        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
        medicineList = InventoryManager.getMedicationList();
        for (int i=0; i<medicineList.size(); i++) {
            System.out.println("Medicine: " + medicineList.get(i).getMedicineName());
            System.out.println("Quantity: " + medicineList.get(i).getStock());
        }
    }

    public void replenishMedicine() {
        String response;
        do {
            System.out.println("Enter the name of medicine that you would like to replenish (Enter NA if not applicable): ");
            response = Helper.readString();
            int quantity;
            if (!response.equals("NA")){
                System.out.println("Enter order quantity: ");
                quantity = Helper.readInt();
                InventoryManager.updateStock(response, quantity);
                System.out.println("Medicine replenished!");
            }
            
        } while (!response.equals("NA"));
    }

    public void viewReplenishmentRequest() {
        String response;
        Medicine[] replenishmentList = InventoryManager.getRequestedAddStockList();
        for (int i=0; i<replenishmentList.length; i++) {
            if (replenishmentList[i] == null) break;
            System.out.println("Medicine: " + replenishmentList[i].getMedicineName() + " Order Quantity: " + replenishmentList[i].getOrderQuantity());
        }
        for (int i=0; i<replenishmentList.length; i++){
            System.out.println("Do you approve to replenish " + replenishmentList[i].getMedicineName() + "?");
            response = Helper.readString();
            if (response == "y"){
                InventoryManager.updateStock(replenishmentList[i].getMedicineName(), replenishmentList[i].getOrderQuantity());
                InventoryManager.approvedReplenishment(replenishmentList[i].getMedicineName());
            }
        }
    }
}
