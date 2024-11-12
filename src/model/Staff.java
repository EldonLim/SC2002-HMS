package model;

import using.Gender;
import using.Role;

/**
 * Represents a staff member, which is a subclass of the User class, with additional attributes such as age.
 * 
 * @author Chin Linn Sheng
 * @version 1.4
 * @since 2024-10-27
 */
public class Staff extends User {

    /** The age of the staff member. */
    private int age;

    /**
     * Default constructor for the Staff class. Initializes a staff member without setting any specific attributes.
     */
    public Staff() {
    }

    /**
     * Constructs a Staff object with the specified name, id, password, gender, role, and age.
     *
     * @param name    the name of the staff member
     * @param id      the id of the staff member
     * @param password the password of the staff member
     * @param gender  the gender of the staff member
     * @param role    the role of the staff member (e.g., doctor, pharmacist)
     * @param age     the age of the staff member
     */
    public Staff(String name, String id, String password, Gender gender, Role role, int age) {
        super(name, id, password, role, gender);
        this.age = age;
    }

    /**
     * Gets the age of the staff member.
     *
     * @return the age of the staff member
     */
    public int getAge() {
        return age;
    }
}
