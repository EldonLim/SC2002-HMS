package model;

import using.Gender;
import using.Role;

/**
 * Represents an administrator in the system, inheriting properties from the Staff class.
 * An administrator has attributes such as name, ID, password, role, gender, and age.
 *
 * @author Lean Yi Fan
 * @version 1.1
 * @since 2024-10-27
 */
public class Adminstrator extends Staff {

    /**
     * Constructs a new Administrator with the specified details.
     *
     * @param name     the name of the administrator
     * @param id       the unique ID of the administrator
     * @param password the password of the administrator
     * @param role     the role of the administrator (e.g., Admin)
     * @param gender   the gender of the administrator
     * @param age      the age of the administrator
     */
    public Adminstrator(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }
}
