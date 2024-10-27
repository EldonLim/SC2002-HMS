package model;
import using.*;

public abstract class User {

    private String name;
    private String id;
    private String password;
    private Role role;
    private Gender gender;

    public User() {}

    public User(String name, String id, String password, Role role, Gender gender) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.role = role;
        this.gender = gender;
    }

    public String getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender.getLabel();
    }

    public String getRole_String() {
        return role.getLabel();
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
