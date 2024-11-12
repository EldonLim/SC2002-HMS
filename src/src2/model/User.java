package src2.model;

import src2.using.Gender;
import src2.using.Role;

/**
 * Represents a generic user in the system. This class contains common attributes for all user types, such as name, id, password, role, and gender.
 * It serves as a base class for specific user roles (e.g., Doctor, Patient, Staff).
 * 
 * @author Chin Linn Sheng
 * @version 3.4
 * @since 2024-10-26
 */
public abstract class User {

    /** The name of the user. */
    private String name;

    /** The unique ID of the user. */
    private String id;

    /** The password for the user account. */
    private String password;

    /** The role of the user (e.g., doctor, patient, staff). */
    private Role role;

    /** The gender of the user. */
    private Gender gender;

    /**
     * Default constructor for the User class. Initializes a user without setting any attributes.
     */
    public User() {
    }

    /**
     * Constructs a User object with the specified name, id, password, role, and gender.
     *
     * @param name    the name of the user
     * @param id      the unique ID of the user
     * @param password the password for the user account
     * @param role    the role of the user (e.g., doctor, patient, staff)
     * @param gender  the gender of the user
     */
    public User(String name, String id, String password, Role role, Gender gender) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.role = role;
        this.gender = gender;
    }

    /**
     * Gets the unique ID of the user.
     *
     * @return the ID of the user
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the unique ID of the user.
     *
     * @param id the new ID for the user
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password the new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the gender of the user.
     *
     * @return the gender of the user
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender the new gender of the user
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the new role of the user
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name for the user
     */
    public void setName(String name) {
        this.name = name;
    }

}
