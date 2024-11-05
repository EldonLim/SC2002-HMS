package model;

import using.Gender;
import using.Role;

public class Adminstrator extends Staff {

    public Adminstrator(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }
}
