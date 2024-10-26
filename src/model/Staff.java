package model;
import using.*;

public class Staff extends User {

    private int age;

    public Staff(String id, String password, Gender gender, Role role, int age) {
        super(id, password, role, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
