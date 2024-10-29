package model;
import using.*;

public class Doctor extends Staff{


    public Doctor(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }
}
