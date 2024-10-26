package model;

enum Role{
    ADMINISTRATOR,
    PHARMACIST,
    DOCTOR,
    PATIENT
}

public class User {
    private String id;
    private String password;
    private String gender;
    private Role role;

    User(){}

    public String getId(){
        return id;
    }
}
