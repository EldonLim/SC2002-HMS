package model;
import using.*;

public class Pharmacist extends Staff{

    public Pharmacist(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }
}
