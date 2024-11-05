package model;

import using.Gender;
import using.Role;

public abstract class User {

    private String name;
    private String id;
    private String password;
    private Role role;
    private Gender gender;

    public User() {
    }

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

    public void setID(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
