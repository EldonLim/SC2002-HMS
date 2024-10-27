package model;
import using.*;

public class Staff extends User {

    private int age;

    public Staff() {}

    public Staff(String name, String id, String password, Gender gender, Role role, int age) {
        super(name, id, password, role, gender);
        this.age = age;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

}
